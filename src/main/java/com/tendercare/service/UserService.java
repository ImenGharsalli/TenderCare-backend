package com.tendercare.service;

import org.springframework.stereotype.Controller;

import com.tendercare.dao.UserRepository;
import com.tendercare.model.User;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@Controller
public class UserService {

	UserRepository userRepository;

	/**
	 * 
	 * @param userRepository
	 */
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<User> readUsers() {
		return userRepository.findAll();
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User readUser(Long userId) {
		return userRepository.findOne(userId);
	}

	/**
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId) {
		userRepository.delete(userId);
	}

	/**
	 * 
	 * @param user
	 * @param userId
	 */
	public void updateUser(User user, long userId) {

	}
}
