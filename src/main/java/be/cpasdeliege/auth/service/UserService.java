package be.cpasdeliege.auth.service;

import be.cpasdeliege.auth.model.User;
import be.cpasdeliege.auth.repository.UserLdapRepository;
import be.cpasdeliege.auth.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserLdapRepository userLdapRepository;

	public User findByUsername(String u) {
        return userLdapRepository.findByUsername(u);
    }

    public List<User> findAllUsers() {
        return userLdapRepository.findAll();
    }

    public User saveUser(User user) {
        return userLdapRepository.save(user);
    }
	
	public boolean authenticate(String username, String password) {
		return userRepository.authenticate(username, password);
	}
	
}
