package be.cpasdeliege.matulis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.authentication.model.User;
import be.cpasdeliege.authentication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IndexRestController {

	private final UserService userService;

	@RequestMapping("/resource")
	public Map<String,Object> home() {
	  Map<String,Object> model = new HashMap<String,Object>();
	  model.put("id", UUID.randomUUID().toString());
	  model.put("content", "Hello World");
	  return model;
	}

	@GetMapping("/test")
    public Object test(
		@NotNull HttpServletRequest request
	) throws IOException {
		//@CookieValue(name = "token") String token
	
		boolean authenticated = userService.authenticate("GOMBA","");
		User user = userService.findByUsername("ULIS");
		List<String> authorities = new ArrayList<>();
		/*
		if(user.hasAnyAuthority(authorities)){
			user.setAuthenticated(authenticated);
		}
		*/
		/*
		var token = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
					System.out.println("COOKIE : "+ cookie.getValue());
				}
			}
		}
		*/
		List<User> users = userService.findAllUsers();
		return ResponseEntity.ok(users);
    }

	@GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello World !");
    }
}