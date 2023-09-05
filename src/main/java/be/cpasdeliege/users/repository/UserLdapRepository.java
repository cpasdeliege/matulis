package be.cpasdeliege.users.repository;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.users.model.User;

@Repository
public interface UserLdapRepository extends LdapRepository<User> {
	Optional<User> findById(Name id);
	User findByUsername(String username);
	List<User> findUsersByUsername(String username);
	List<User> findUsersByFullnameContaining(String fullname);
}
