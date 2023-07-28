package be.cpasdeliege.matulis.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.authentication.model.User;
import be.cpasdeliege.authentication.service.UserService;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IndexRestController {
/*
	@Value("${spring.ldap.urls}")
    private String ldapUrls;
	@Value("${spring.ldap.base}")
    private String ldapBase;
	@Value("${spring.ldap.username}")
    private String ldapUsername;
*/
	private final UserService userService;

	/* 
	@Value("${spring.profiles.active}")
	private String activeProfile;
	*/
	
	@RequestMapping("/resource")
	public Map<String,Object> home() {
	  Map<String,Object> model = new HashMap<String,Object>();
	  model.put("id", UUID.randomUUID().toString());
	  model.put("content", "Hello World");
	  return model;
	}

	@GetMapping("/test")
    public Object getLdapProperties() throws IOException {
		boolean authenticated = userService.authenticate("GOMBA","");
		User user = userService.findByUsername("ULIS");
		List<String> authorities = new ArrayList<>();
		//authorities.add("CN=_GPP_BaD_CPL-APP-04,OU=Particuliers,OU=Groups,OU=Divers,OU=CPAS,DC=CpasLiege,DC=dom");
		//authorities.add("CN=_GPDB_BufferAS400,OU=DB,OU=Groups,OU=Divers,OU=CPAS,DC=CpasLiege,DC=dom");
		if(user.hasAuthorities(authorities)){
			user.setAuthenticated(authenticated);
		}
		return ResponseEntity.ok(user);
    }

	@GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello World !");
    }
}