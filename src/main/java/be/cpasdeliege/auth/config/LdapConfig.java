package be.cpasdeliege.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;

@Configuration
@EnableLdapRepositories(basePackages = "be.cpasdeliege.auth.repository")
public class LdapConfig {
	@Bean
	public LdapTemplate ldapTemplate(ContextSource contextSource) {
		LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
		ldapTemplate.setIgnorePartialResultException(false);
		return ldapTemplate;
	}
}
