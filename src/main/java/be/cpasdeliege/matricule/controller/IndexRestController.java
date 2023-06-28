package be.cpasdeliege.matricule.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexRestController {

	@Value("${spring.ldap.urls}")
    private String ldapUrls;

	@Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping("/")
    public String sayHello() {
        return "Active profile: " + activeProfile;
    }

	@GetMapping("/ldap")
    public String getLdapProperties() throws IOException {
        return "Urls :" + ldapUrls;
    }
}