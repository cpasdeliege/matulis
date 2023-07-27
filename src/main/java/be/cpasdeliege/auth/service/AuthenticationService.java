package be.cpasdeliege.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import be.cpasdeliege.auth.model.AuthenticationRequest;
import be.cpasdeliege.auth.model.AuthenticationResponse;
import be.cpasdeliege.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		var user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()); //orElseThrow()
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
