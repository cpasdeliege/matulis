package be.cpasdeliege.authentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import be.cpasdeliege.authentication.exception.AuthenticationException;
import be.cpasdeliege.authentication.model.AuthenticationRequest;
import be.cpasdeliege.authentication.model.AuthenticationResponse;
import be.cpasdeliege.authentication.model.GroupAuthority;
import be.cpasdeliege.authentication.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Value("${application.security.jwt.expiration}")
	private int jwtExpiration;
	@Value("${application.security.jwt.cookie-name}")
	private String cookieName;

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final List<GroupAuthority> groupAuthorities;
	
	public AuthenticationResponse authenticate(AuthenticationRequest request,HttpServletResponse response) throws AuthenticationException {

		User user = userService.findByUsername(request.getUsername());

		if(user == null){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Identifiant incorrect.");
		}

		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
			);
		} catch (org.springframework.security.core.AuthenticationException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Mot de passe incorrect.", e);
		}

		if (!user.hasAnyAuthority(groupAuthorities)){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'avez pas les accès nécessaires.");
		}

		var jwtToken = jwtService.generateToken(user);
		Cookie cookie = new Cookie(cookieName, jwtToken);
		cookie.setHttpOnly(true);
		//cookie.setSecure(true);
		cookie.setMaxAge(jwtExpiration / 1000); // conversion millisecondes en secondes
		cookie.setPath("/");
		response.addCookie(cookie);

		return AuthenticationResponse.builder().user(user).build();
	}
	
	public AuthenticationResponse isTokenValid(String jwtToken) throws AuthenticationException {
		if(jwtToken == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pas de token fourni.");
		}

		String username = jwtService.extractUsername(jwtToken);
		User user = userService.findByUsername(username);
		
		if(user == null){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Identifiant incorrect.");
		}

		if (!user.hasAnyAuthority(groupAuthorities)){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas les accès nécessaires.");
		}

		if(!jwtService.isTokenValid(jwtToken, user)){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token invalide.");
		}

		return AuthenticationResponse.builder().user(user).build();
	}

	public boolean logout(HttpServletResponse response) throws AuthenticationException {
		removeCookie(response);
		return true;
	}

	public void removeCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0); // pour supprimer le cookie
		//cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
