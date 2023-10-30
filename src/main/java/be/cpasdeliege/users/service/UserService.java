package be.cpasdeliege.users.service;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;

import org.springframework.stereotype.Service;

import be.cpasdeliege.users.model.User;
import be.cpasdeliege.users.repository.UserLdapRepository;
import be.cpasdeliege.users.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserLdapRepository userLdapRepository;

	public Optional<User> findById(Name dn) {
        return userLdapRepository.findById(dn);
    }
	
	public User findByUsername(String u) {
        return userLdapRepository.findByUsername(u);
    }

	public List<User> findUsersByUsername(String u) {
        return userLdapRepository.findUsersByUsername(u);
    }

	public List<User> findUsersByFullnameContaining(String u) {
        //return userLdapRepository.findUsersByFullnameContaining(u);
		return userRepository.findUsersByFullname(u);
    }

    public List<User> findAllUsers() {
        return userLdapRepository.findAll();
    }

    public void updateEmployeeId(Name dn, String employeeId) {
        userRepository.updateEmployeeId(dn, employeeId);
    }
	
	public boolean authenticate(String username, String password) {
		return userRepository.authenticate(username, password);
	}
	
}
