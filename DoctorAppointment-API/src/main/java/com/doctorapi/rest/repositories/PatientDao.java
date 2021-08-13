package com.doctorapi.rest.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.models.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long>{

	public List<Patient> findByOrderByIdAsc();

	public Patient findByEmail(String email);

	public Patient findByUserId(Long userId);

	public  List<Patient> findByAppointments(Long doctorId);

	@Query("SELECT p FROM patient p WHERE DATE(p.createdOn)=?1")
	public List<Patient> findAllWithCreatedOn(Date date1);

	@Query("SELECT p FROM patient p WHERE DATE(p.modifiedOn)=?1")
	public List<Patient> findAllWithModifiedOn(Date date1);

}
