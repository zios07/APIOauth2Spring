package ma.demoapp.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.demoapp.entities.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	public User getUser(long id) {
		return userRepository.findOne(id);
	}
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
}
