package oaktrees.db.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oaktrees.data.User;
import oaktrees.db.interfaces.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findById(long id) {
		return userRepository.findById(id);
	}
	
	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public boolean exist(String login) {
		return userRepository.exist(login) == 0 ? false : true;
	}
	
}
