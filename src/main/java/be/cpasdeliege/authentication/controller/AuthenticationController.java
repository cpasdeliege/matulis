package be.cpasdeliege.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.authentication.model.AuthenticationRequest;
import be.cpasdeliege.authentication.model.AuthenticationResponse;
import be.cpasdeliege.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
		@RequestBody AuthenticationRequest request
	) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}

	@GetMapping("/check-token")
	public boolean checkToken(
		@RequestParam String token
	) {
		return authenticationService.isTokenValid(token);
	}
}
