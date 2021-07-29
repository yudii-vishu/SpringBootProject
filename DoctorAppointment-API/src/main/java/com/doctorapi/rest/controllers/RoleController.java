package com.doctorapi.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapi.rest.dto.RoleDTO;
import com.doctorapi.rest.services.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
	/**
	 * @param roleDTO
	 * 
	 * This controller save the role.
	 * 
	 * @return roleDTO
	 * 
	 * @throws Exception
	 */
	@PostMapping("/role")
	public RoleDTO saveRole(@RequestBody RoleDTO roleDTO) throws Exception {
		
		return roleService.saveRole(roleDTO);
	
	}

}
