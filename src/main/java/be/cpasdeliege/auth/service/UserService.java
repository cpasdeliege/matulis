package be.cpasdeliege.auth.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import be.cpasdeliege.auth.model.User;
import be.cpasdeliege.auth.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	@Autowired
	private final LdapTemplate ldapTemplate;

	@Autowired
    public UserService(UserRepository userRepository,LdapTemplate ldapTemplate) {
        this.userRepository = userRepository;
		this.ldapTemplate = ldapTemplate;
    }

	/*
	public User findUserByUid(String uid) {
        return userRepository.findById(uid).orElse(null);
    }
	*/

	public List<User> search(String u) {
		List<User> userList = userRepository.findByUsernameLike(u);
		
		if (userList == null) {
			return Collections.emptyList();
		}

		return userList;
	}

	public User findByUsername(String u) {
        return userRepository.findByUsername(u);
    }

	public User findByUsernameAndPassword(String u, String p) {
        return userRepository.findByUsernameAndPassword(u,p);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

	//OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS

    public User saveUser(User user) {
        return userRepository.save(user);
    }
	
	/*
    public void deleteUser(String uid) {
        userRepository.deleteById(uid);
    }
	*/
}
