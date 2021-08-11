package com.doctorapi.rest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapi.rest.dto.UserDTO;
import com.doctorapi.rest.models.User;
import com.doctorapi.rest.repositories.UserDao;

@Service
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
	
	
	/**
	 * This method will provide the list of Active users.
	 * 
	 * @return UserDTO
	 * 
	 * @throws Exception
	 */
	public List<UserDTO> getAllUsers() throws Exception {

		List<UserDTO> userDTO = userDao.findByIsActiveTrue().stream().map(UserDTO::new).collect(Collectors.toList());
		
		if(userDTO.isEmpty()) {
			throw new Exception ("No active user found.");
		}
		return userDTO;
	}
	
	
	/**
	 * @param id
	 * 
	 * This method will give you the particular user detail w.r.t userId.
	 * 
	 * @return userDTO
	 * 
	 * @throws Exception
	 */
	public UserDTO getUser(Long id) throws Exception {
		
		User user = validateAndGetUser(id);

		return new UserDTO(user);
	}
	
	/**
	 * @param id
	 * 
	 * This method will validate the user regarding userId.
	 *  
	 * @return userDTO
	 * 
	 * @throws Exception
	 */
	public User validateAndGetUser(Long id) throws Exception {
		
		 logger.info("To validate the user regarding userId");
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) {
			throw new Exception ("User not found.");
		}
		logger.info("Returning User after validating user existence.");
		return user.get();
	}
		



	public void changeStatus(Long id) throws Exception {
		
		User user = validateAndGetUser(id);

		if(user.isActive()) {
			logger.info("User is deactivated.");
			user.setActive(false);
		}
		else {
			logger.info("User is activated.");
			user.setActive(true);
		}
		userDao.save(user);
		
	}


	/**
	 * @param userRole
	 * 
	 * @return This method will provide the list of users regarding their roles.
	 * 
	 * @throws Exception
	 */
	public List<UserDTO> getUserByRole(String userRole) throws Exception {

		List<UserDTO> usersList = userDao.findByIsActiveTrueAndRole_NameIgnoreCase(userRole).stream().map(UserDTO::new).collect(Collectors.toList());
		
		if(usersList.isEmpty()) {
			logger.info("No active user is present w.r.t the role.");
			throw new Exception ("No active user is present w.r.t the role.");
		}
		
		return usersList;
	}


}
