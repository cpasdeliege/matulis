package be.cpasdeliege.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.users.model.AuthenticationRequest;
import be.cpasdeliege.users.model.AuthenticationResponse;
import be.cpasdeliege.users.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
		@RequestBody AuthenticationRequest authenticationRequest,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest,request,response);
		return ResponseEntity.status(authenticationResponse.getStatus()).body(authenticationResponse);
	}

	@GetMapping("/check-token")
	public ResponseEntity<AuthenticationResponse> checkToken(
		@CookieValue(name = "token", required = false) String token
	) {
		AuthenticationResponse authenticationResponse = authenticationService.isTokenValid(token);
		return ResponseEntity.status(authenticationResponse.getStatus()).body(authenticationResponse);
	}

	@DeleteMapping("/logout")
	public boolean logout(
		@CookieValue(name = "token", required = false) String token,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		return authenticationService.logout(token,request,response);
	}
}
