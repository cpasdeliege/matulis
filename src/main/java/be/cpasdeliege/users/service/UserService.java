package be.cpasdeliege.users.service;

import java.util.List;

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

	public User findByUsername(String u) {
        return userLdapRepository.findByUsername(u);
    }

	public List<User> findUsersByUsername(String u) {
        return userLdapRepository.findUsersByUsername(u);
    }

	public List<User> findUsersByFullnameContaining(String u) {
        return userLdapRepository.findUsersByFullnameContaining(u);
    }

    public List<User> findAllUsers() {
        return userLdapRepository.findAll();
    }

    public User save(User user) {
        return userLdapRepository.save(user);
    }
	
	public boolean authenticate(String username, String password) {
		return userRepository.authenticate(username, password);
	}
	
}
