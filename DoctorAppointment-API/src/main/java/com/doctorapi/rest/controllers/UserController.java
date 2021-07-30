package com.doctorapi.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapi.rest.dto.UserDTO;
import com.doctorapi.rest.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * This controller provide the list of users.
	 * 
	 * @return UserDTO list
	 * 
	 * @throws Exception
	 */
	@GetMapping("/user")
	public List<UserDTO> getAllUsers() throws Exception {
		
		return userService.getAllUsers();
	}
	
	
	/**
	 * @param id
	 * 
	 * This controller will provide the particular user detail.
	 * 
	 * @return UserDTO
	 * 
	 * @throws Exception
	 */
	@GetMapping("/users/{id}")
	public UserDTO getUser(@PathVariable Long id) throws Exception {
		
		return userService.getUser(id);
	}
	
	
	
	/**
	 * @param id
	 * 
	 * This controller change the status of user.
	 * 
	 * @throws Exception 
	 */
	@PutMapping("/user/{id}")
	public void changeStatus(@PathVariable Long id) throws Exception {
		userService.changeStatus(id);
	}
	
	/**
	 * @param userRole
	 * 
	 * @return This controller will provide the list of user w.r.t their role.
	 * 
	 * @throws Exception
	 */
	@GetMapping("/user/{userRole}")
	public List<UserDTO> getUserByRole(@PathVariable String userRole) throws Exception {
		
		return userService.getUserByRole(userRole);
	}
	

}
