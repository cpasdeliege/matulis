package be.cpasdeliege.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.logs.model.LogStatus;
import be.cpasdeliege.logs.service.LogService;
import be.cpasdeliege.users.model.User;
import be.cpasdeliege.users.service.JwtService;
import be.cpasdeliege.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final LogService logService;
	private final JwtService jwtService;

	@PutMapping("/update")
	public ResponseEntity<User> update(
		@RequestBody User userData,
		@CookieValue(name = "token") String token,
		HttpServletRequest request
	) {
		User user = userService.findOneByDn(userData.getDn());
		if(user != null){
			String oldEmployeeID = user.getEmployeeId();
			if(oldEmployeeID == null || !oldEmployeeID.equals(userData.getEmployeeId())) { // S'il y a bien un changement
				String newEmployeeID = userData.getEmployeeId() != "" ? userData.getEmployeeId() : null; // LDAP bloque si c'est une chaine vide
				userService.updateEmployeeId(user.getDn(),newEmployeeID); // Pour le moment on ne change que cette prop
				logService.create(jwtService.extractUsername(token), "update", request.getRemoteAddr(), LogStatus.SUCCESS, null, "User", user.getUsername(), "employeeID", oldEmployeeID, userData.getEmployeeId());

				// Màj du User
				user = userService.findOneByDn(userData.getDn());
			}
		}
		return ResponseEntity.ok(user);
	}
}
