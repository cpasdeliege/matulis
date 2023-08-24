package be.cpasdeliege.authentication.config;

import be.cpasdeliege.authentication.model.GroupAuthority;
import be.cpasdeliege.authentication.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
	
	@Value("#{'${application.security.allowed-groups}'.split(';')}")
    private List<String> allowedGroups;

	private final UserService userService;

	@Bean 
	public UserDetailsService userDetailsService() {
		return username -> userService.findByUsername(username); //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
		LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
		factory.setUserSearchFilter("(&(objectCategory=person)(objectClass=user)(sAMAccountName={0}))");
		//factory.setUserSearchBase("ou=cpas");
		return factory.createAuthenticationManager();
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public List<GroupAuthority> groupAuthorities() {
        return allowedGroups.stream()
            .map(GroupAuthority::new)
            .collect(Collectors.toList());
    }
}
