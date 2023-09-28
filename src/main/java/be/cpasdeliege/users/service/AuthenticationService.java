package be.cpasdeliege.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		if(user != null) {
			try {
				authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);

				if (user.hasAnyAuthority(groupAuthorities)){
					var jwtToken = jwtService.generateToken(user);
					Cookie cookie = new Cookie(cookieName, jwtToken);
					cookie.setDomain(cookieDomain);
					cookie.setPath(cookiePath);
					cookie.setHttpOnly(true);
					//cookie.setSecure(true); // Pour n'autoriser que sur des connexions https, mais nous sommes toujours en http pour le moment
					cookie.setMaxAge(jwtExpiration / 1000); // conversion millisecondes en secondes
					response.addCookie(cookie);

					logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.SUCCESS);
					authenticationResponse.setUser(user);

				} else {
					logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.FAILED, "unauthorized");
					authenticationResponse.setErrorMessage("Vous n'avez pas les accès nécessaires.");
					authenticationResponse.setStatus(HttpStatus.UNAUTHORIZED);	
				}

			} catch (org.springframework.security.core.AuthenticationException e) {
				logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(), LogStatus.FAILED, "incorrect password");
				authenticationResponse.setErrorMessage("Mot de passe incorrect.");
				authenticationResponse.setStatus(HttpStatus.FORBIDDEN);	
			}
			
		} else {
			logService.create(authenticationRequest.getUsername(), logAction, request.getRemoteAddr(),LogStatus.FAILED, "username not found");
			authenticationResponse.setErrorMessage("Identifiant incorrect.");
			authenticationResponse.setStatus(HttpStatus.FORBIDDEN);
		}

		return authenticationResponse;
	}
	
	public AuthenticationResponse isTokenValid(String jwtToken) throws AuthenticationException {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		if(jwtToken != null) {
			String username = jwtService.extractUsername(jwtToken);
			User user = userService.findByUsername(username);
			
			if(user != null){
				if (user.hasAnyAuthority(groupAuthorities)){
					if(jwtService.isTokenValid(jwtToken, user)){
						authenticationResponse.setUser(user);
					} else {
						authenticationResponse.setStatus(HttpStatus.FORBIDDEN);
						authenticationResponse.setErrorMessage("Token invalide.");
					}
				} else {
					authenticationResponse.setStatus(HttpStatus.FORBIDDEN);
					authenticationResponse.setErrorMessage("Vous n'avez pas les accès nécessaires.");
				}
			} else { 
				authenticationResponse.setStatus(HttpStatus.FORBIDDEN);
				authenticationResponse.setErrorMessage("Identifiant incorrect.");
			}
		} else {
			authenticationResponse.setStatus(HttpStatus.FORBIDDEN);
			authenticationResponse.setErrorMessage("Pas de token fourni.");
		}

		return authenticationResponse;
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
