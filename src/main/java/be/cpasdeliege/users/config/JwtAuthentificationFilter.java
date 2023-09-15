package be.cpasdeliege.users.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.cpasdeliege.users.service.AuthenticationService;
import be.cpasdeliege.users.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
	

	@Value("${application.security.jwt.cookie-name}")
	private String cookieName;
	
	private final AuthenticationService authenticationService;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
		@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain
	) throws ServletException, IOException {
		final String username;
		String jwt = null;
		// On parcourt les cookies, pour récupérer le jwt
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					jwt = cookie.getValue();
				}
			}
		}

		if(jwt == null) {
			// Filtre suivant si pas de jwt
			filterChain.doFilter(request, response);
			return;
		}

		try {
			username = jwtService.extractUsername(jwt);
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// On récupère le user via LDAP
				if(jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwt, username, userDetails.getAuthorities());
					authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
					);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				filterChain.doFilter(request, response);
			}
		} catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			// Si jwt pas valide, on supprime le cookie et on passe au fitlre suivant
			authenticationService.removeCookie(response);
			filterChain.doFilter(request, response);
			return;
		}
	}
}
