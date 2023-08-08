package be.cpasdeliege.authentication.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import be.cpasdeliege.authentication.exception.AuthenticationException;
import be.cpasdeliege.authentication.model.AuthenticationRequest;
import be.cpasdeliege.authentication.model.AuthenticationResponse;
import be.cpasdeliege.authentication.model.GroupAuthority;
import be.cpasdeliege.authentication.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final List<GroupAuthority> groupAuthorities;
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		User user = userService.findByUsername(request.getUsername());

		if(user == null){
			throw new AuthenticationException("User not found");
		}

		if (!user.hasAnyAuthority(groupAuthorities)){
			throw new AuthenticationException("User does not have the required authority.");
		}

		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).user(user).build();
	}
	
	public boolean isTokenValid(String jwtToken) throws AuthenticationException {
		String username = jwtService.extractUsername(jwtToken);
		User user = userService.findByUsername(username);
		
		if(user == null){
			throw new AuthenticationException("User not found.");
		}

		if (!user.hasAnyAuthority(groupAuthorities)){
			throw new AuthenticationException("User does not have the required authority.");
		}

		if(!jwtService.isTokenValid(jwtToken, user)){
			throw new AuthenticationException("Invalid token. Generate a new one.");
		}

		return true;
	}
}
