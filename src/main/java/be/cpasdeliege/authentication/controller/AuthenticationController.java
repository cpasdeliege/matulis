package be.cpasdeliege.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.authentication.exception.AuthenticationException;
import be.cpasdeliege.authentication.model.AuthenticationRequest;
import be.cpasdeliege.authentication.model.AuthenticationResponse;
import be.cpasdeliege.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
		@RequestBody AuthenticationRequest request,
		HttpServletResponse response
	) {
		return ResponseEntity.ok(authenticationService.authenticate(request,response));
	}

	@GetMapping("/check-token")
	public ResponseEntity<AuthenticationResponse> checkToken(
		@CookieValue(name = "token", required = false) String token
	) {
		return ResponseEntity.ok(authenticationService.isTokenValid(token));
	}

	@DeleteMapping("/logout")
	public boolean logout(
		HttpServletResponse response
	) {
		return authenticationService.logout(response);
	}
}
