package be.cpasdeliege.auth.repository;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepository {
	
	private final LdapTemplate ldapTemplate;

	public boolean authenticate(String username, String password) {
		return ldapTemplate.authenticate("ou=cpas", "(sn="+username+")", password);
	}
}
