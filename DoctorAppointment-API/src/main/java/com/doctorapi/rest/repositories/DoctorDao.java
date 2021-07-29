package com.doctorapi.rest.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.Enum.Status;
import com.doctorapi.rest.models.Doctor;

@Repository
public interface DoctorDao extends JpaRepository<Doctor, Long> {

	public List<Doctor> findByOrderByIdAsc();
	
	public Doctor findByEmail(String email);
	
	public Doctor findByUserId(Long userId);
	
	
	@Query("SELECT dd from doctor dd where dd.status=?1")
	public List<Doctor> findByOrderByStatus(Status status);


	
}
