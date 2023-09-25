package be.cpasdeliege.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import be.cpasdeliege.logs.model.LogStatus;
import be.cpasdeliege.logs.service.LogService;
import be.cpasdeliege.users.exception.AuthenticationException;
import be.cpasdeliege.users.model.AuthenticationRequest;
import be.cpasdeliege.users.model.AuthenticationResponse;
import be.cpasdeliege.users.model.GroupAuthority;
import be.cpasdeliege.users.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Value("${application.security.jwt.expiration}")
	private int jwtExpiration;
	@Value("${application.security.jwt.cookie-name}")
	private String cookieName;
	@Value("${application.security.jwt.cookie-domain}")
	private String cookieDomain;
	@Value("${application.security.jwt.cookie-path}")
	private String cookiePath;

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final LogService logService;
	private final List<GroupAuthority> groupAuthorities;
	
	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		User user = userService.findByUsername(authenticationRequest.getUsername());
		String logAction = "login";

		if(user == null){
			logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(),LogStatus.FAILED, "username not found");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Identifiant incorrect.");
		}

		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch (org.springframework.security.core.AuthenticationException e) {
			logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.FAILED, "incorrect password");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Mot de passe incorrect.", e);
		}

		if (!user.hasAnyAuthority(groupAuthorities)){
			logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.FAILED, "unauthorized");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'avez pas les accès nécessaires.");
		}

		var jwtToken = jwtService.generateToken(user);
		Cookie cookie = new Cookie(cookieName, jwtToken);
		cookie.setDomain(cookieDomain);
		cookie.setPath(cookiePath);
		cookie.setHttpOnly(true);
		//cookie.setSecure(true); // Pour n'autoriser que sur des connexions https, mais nous sommes toujours en http pour le moment
		cookie.setMaxAge(jwtExpiration / 1000); // conversion millisecondes en secondes
		response.addCookie(cookie);

		logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.SUCCESS);

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

	public boolean logout(String token, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		removeCookie(response);
		logService.create(jwtService.extractUsername(token), "logout", request.getRemoteAddr(), LogStatus.SUCCESS);
		return true;
	}

	public void removeCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setDomain(cookieDomain);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(0); // pour supprimer le cookie
		//cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
