package com.doctorapi.rest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.dto.UserDTO;
import com.doctorapi.rest.models.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

	List<User> findByIsActiveTrue();

	User findByEmail(String email);

	List<User> findByIsActiveTrueAndRole_NameIgnoreCase(String userRole);

}
