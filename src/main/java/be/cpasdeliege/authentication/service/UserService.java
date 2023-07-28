package be.cpasdeliege.authentication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.cpasdeliege.authentication.model.User;
import be.cpasdeliege.authentication.repository.UserLdapRepository;
import be.cpasdeliege.authentication.repository.UserRepository;
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
