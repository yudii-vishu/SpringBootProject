package com.doctorapi.rest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.models.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>{

	Optional<Role> findByNameIgnoreCase(String name);
}
