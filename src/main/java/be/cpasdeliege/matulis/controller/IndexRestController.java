package be.cpasdeliege.matulis.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.users.model.User;
import be.cpasdeliege.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IndexRestController {

	private final UserService userService;

	@GetMapping("/search-users")
    public Object searchUsers(
		@RequestParam String username,
		@NotNull HttpServletRequest request
	) throws IOException {
		List<User> users = userService.findAllByFullname(username);
		return ResponseEntity.ok(users);
    }

	@GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello World !");
    }
}