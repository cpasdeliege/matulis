package be.cpasdeliege.auth.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.auth.model.User;

@Repository
public interface UserLdapRepository extends LdapRepository<User> {
	User findByUsername(String username);
}
