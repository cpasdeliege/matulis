package be.cpasdeliege.auth.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.cpasdeliege.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
		@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain
	) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		// Check jwt Token
		if(authHeader == null || !authHeader.startsWith("Bearer ")){
			// Go to next filter if no jwt
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7); // After "Bearer "
		username = jwtService.extractUsername(jwt);
	}
}
