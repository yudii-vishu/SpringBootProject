package com.doctorapi.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapi.rest.dto.PatientDTO;
import com.doctorapi.rest.services.PatientServiceImpl;

@RestController
public class PatientController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PatientServiceImpl patientServiceImpl;
	
	
	/**
	 * Return the list of patients from database patient table
	 * 
	 * @return PatientDTO
	 */
	@GetMapping("/patient")
	public List<PatientDTO> getPatients(){
		
		return patientServiceImpl.getPatients();
	}
	
	/**
	 * @param patientId
	 * 
	 * This will return individual patient detail 
	 * @return patientDTO
	 * 
	 * @throws Exception 
	 */
	@GetMapping("/patient/{patientId}")
	public PatientDTO getPatientById(@PathVariable Long patientId) throws Exception {
		
		return patientServiceImpl.getPatientById(patientId);
		 
	}
	
	/**
	 * @param patientDTO
	 * 
	 * This controller will add patient and update patient detail in the patient table
	 * 
	 * @return patientDTO
	 * 
	 * @throws Exception 
	 */
	@PostMapping("/patient")
	public PatientDTO saveAndUpdate(@Valid @RequestBody PatientDTO patientDTO) throws Exception {
		
		logger.info("To save patient");
		return patientServiceImpl.saveAndUpdate(patientDTO);
	}
	
	
	@GetMapping("/patient/createdOn/{createdOn}")
	public List<PatientDTO> getPatientByCreatedOn(@PathVariable String createdOn) throws Exception {
		
		return patientServiceImpl.getPatientByCreatedOn(createdOn);
	}
	
	
	@GetMapping("/patient/modifiedOn/{modifiedOn}")
	public List<PatientDTO> getPatientByModifiedOn(@PathVariable String modifiedOn) throws Exception {
		
		return patientServiceImpl.getPatientByModifiedOn(modifiedOn);
	}

}
