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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.auth.model.User;
import be.cpasdeliege.auth.service.UserService;


@RestController
@RequestMapping("/api")
public class IndexRestController {

	@Value("${spring.ldap.urls}")
    private String ldapUrls;
	@Value("${spring.ldap.base}")
    private String ldapBase;
	@Value("${spring.ldap.username}")
    private String ldapUsername;

	private final UserService userService;
	
	@Autowired
    public IndexRestController(UserService userService) {
        this.userService = userService;
    }

	/* 
	@Value("${spring.ldap.urls}")
    private String ldapUrls;

	@Value("${spring.profiles.active}")
	private String activeProfile;

    @GetMapping("/")
    public String sayHello() {
		System.out.println("test5");
        return "Active profile: " + activeProfile;
    }
	
	@GetMapping("/ldap")
    public String getLdapProperties() throws IOException {
        return "Urls :" + ldapUrls;
    }
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
        /*
		Map<String, Object> object = new HashMap<>();
		object.put("key1", "value1");
		object.put("key2", "value2");
		*/
		List<User> users = userService.findByUsernameLike("GOMBA");
		//List<User> users = userService.findAllUsers();
		
		//List<User> users = Collections.<User>emptyList();
		//users.add(user);
		System.out.println("USERS COUNT : " + users.size());
		//System.out.println("USER : " + user.getUsername());

		return users;
    }
}