package be.cpasdeliege.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@Configuration
@EnableLdapRepositories(basePackages = "be.cpasdeliege.users.repository")
public class LdapConfig {
	
}
