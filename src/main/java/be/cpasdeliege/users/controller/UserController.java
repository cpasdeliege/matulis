package be.cpasdeliege.users.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.users.model.User;
import be.cpasdeliege.users.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	@PutMapping("/update")
	public ResponseEntity<User> update(
		@RequestBody User userData
	) {
		User user = null;
		Optional<User> op = userService.findById(userData.getDn());
		if(op.isPresent()){
			user = op.get();
			userService.updateEmployeeId(user.getDn(),userData.getEmployeeId()); // Pour le moment on ne change que cette prop
		}
		return ResponseEntity.ok(user);
	}
}
