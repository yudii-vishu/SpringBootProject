package com.doctorapi.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapi.rest.Enum.Status;
import com.doctorapi.rest.dto.DoctorDTO;
import com.doctorapi.rest.services.DoctorServiceImpl;

@RestController
public class DoctorController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DoctorServiceImpl doctorServiceImpl;
	
	
	// Sample API for test purpose-only
	@GetMapping("/create")
	public String Create() {
		return "Choose AnyOne Role";
	}
	
	/**
	 * Return list of doctors from database doctor table
	 * 
	 * @return doctorDTO
	 */
	@GetMapping("/doctor")
	public List<DoctorDTO> getDoctors(){
		
		return doctorServiceImpl.getDoctors();
	}
	
	/**
	 * @param doctorId
	 * 
	 * This will return individual doctor details
	 * @return doctorDTO
	 * 
	 * @throws Exception 
	 */
	@GetMapping("/doctor/{doctorId}")
	public DoctorDTO getDoctorById(@PathVariable Long doctorId) throws Exception {
		
		return doctorServiceImpl.getDoctorById(doctorId);
	}
	
	/**
	 * This will return the list of available doctors
	 * 
	 * @return doctorDTO
	 */
	@GetMapping("/doctor/status/{status}")
	public List<DoctorDTO> getAvailability(@PathVariable Status status) {
		
		return doctorServiceImpl.getAvailability(status);
	}
	
	/**
	 * @param doctorDTO
	 * 
	 * This controller save the new doctor 
	 * @return doctorDTO
	 * 
	 * @throws Exception 
	 */
	@PostMapping("/doctor")
	public String saveAndUpdate(@Valid @RequestBody DoctorDTO doctorDTO) throws Exception {
		
		logger.info("To save doctor");
		String msg = doctorServiceImpl.saveAndUpdate(doctorDTO);
		
		return msg;
		}
	
		
	/**
	 * @param doctorId
	 * 
	 * This controller will delete the particular doctor from the database table
	 * 
	 * @return deleted doctorDTO
	 * 
	 * @throws Exception 
	 */
	@DeleteMapping("/doctor/delete/{doctorId}")
	public void deleteDoctor(@PathVariable Long doctorId) throws Exception {
		
		doctorServiceImpl.deleteDoctor(doctorId);
	}
	
	
	
}