package be.cpasdeliege.authentication.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.cpasdeliege.authentication.service.JwtService;
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
					System.out.println("COOKIE : "+ cookie.getValue());
				}
			}
		}

		/*
		else{
			System.out.println("RETURN NO COOKIES");
			return;
		}
		*/

		if(jwt == null) {
			System.out.println("RETURN NO JWT");
			// Filtre suivant si pas de jwt
			filterChain.doFilter(request, response);
			return;
		}
		
		System.out.println("AFTER COOKIE : "+jwt);

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
	}
}
