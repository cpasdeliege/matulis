package be.cpasdeliege.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@Configuration
@EnableLdapRepositories(basePackages = "be.cpasdeliege.authentication.repository")
public class LdapConfig {
	
}
