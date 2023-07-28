package be.cpasdeliege.authentication.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.authentication.model.User;

@Repository
public interface UserLdapRepository extends LdapRepository<User> {
	User findByUsername(String username);
}
