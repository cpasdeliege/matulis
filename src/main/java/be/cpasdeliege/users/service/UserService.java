package be.cpasdeliege.users.service;

import java.util.List;

import javax.naming.Name;

import org.springframework.stereotype.Service;

import be.cpasdeliege.users.model.User;
import be.cpasdeliege.users.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	public boolean authenticate(String username, String password) {
		return userRepository.authenticate(username, password);
	}

	public User findOneByDn(Name dn) {
        return userRepository.findOneByDn(dn);
    }

	public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

	public List<User> findAllByFullname(String fullname) {
		return userRepository.findAllByFullname(fullname);
    }

    public void updateEmployeeId(Name dn, String employeeId) {
        userRepository.updateEmployeeId(dn, employeeId);
    }
}
