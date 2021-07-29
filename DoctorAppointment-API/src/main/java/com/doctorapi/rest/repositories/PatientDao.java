package com.doctorapi.rest.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorapi.rest.models.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long>{

	public List<Patient> findByOrderByIdAsc();

	public Patient findByEmail(String email);

	public Patient findByUserId(Long userId);

}
