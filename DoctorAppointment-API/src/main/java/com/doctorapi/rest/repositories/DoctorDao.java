package com.doctorapi.rest.repositories;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	public List<Doctor> findByModifiedOn(LocalDateTime localDateTime);

	public List<Doctor> getAppointmentById(Long doctorId);

	@Query("SELECT d FROM doctor d WHERE DATE(d.createdOn)=?1")
	public List<Doctor> findAllWithCreatedOn(@Param("createdOn") Date date1);

	
	@Query("SELECT d FROM doctor d WHERE DATE(d.modifiedOn)=?1")
	public List<Doctor> findAllWithModifiedOn(@Param("modifiedOn") Date date1);


	
	
}
