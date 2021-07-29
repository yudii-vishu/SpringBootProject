package com.doctorapi.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapi.rest.Enum.RoleName;
import com.doctorapi.rest.dto.RoleDTO;
import com.doctorapi.rest.models.Role;
import com.doctorapi.rest.repositories.RoleDao;

@Service
public class RoleService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleDao roleDao;

	/**
	 * @param roleDTO
	 * 
	 * This method will add roles.
	 * 
	 * @return roleDTO object
	 * 
	 * @throws Exception
	 */
	public RoleDTO saveRole(RoleDTO roleDTO) throws Exception {

		if(!RoleName.getEnum(roleDTO.getName())) {
			logger.info("Invalid Role.");
			throw new Exception ("Invalid Role.");
		}
		
		if(roleDao.findByNameIgnoreCase(roleDTO.getName()).isPresent()) {
			logger.info("Role already exist.");
			throw new Exception ("Role already exist.");
		}
		Role role = new Role(roleDTO);
		return new RoleDTO(roleDao.save(role));
	}
	
}
