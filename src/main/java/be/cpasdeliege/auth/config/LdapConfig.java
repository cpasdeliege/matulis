package be.cpasdeliege.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@Configuration
@EnableLdapRepositories(basePackages = "be.cpasdeliege.auth.repository")
public class LdapConfig {
	
}
