package com.doctorapi.rest.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapi.rest.dto.AppointmentDTO;
import com.doctorapi.rest.services.AppointmentServiceImpl;

@RestController
public class AppointmentController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppointmentServiceImpl appointmentServiceImpl;

	/**
	 * Return the list of appointments from the database appointment table
	 * 
	 * @return appointmentDTO
	 */
	@GetMapping("/appointment")
	public List<AppointmentDTO> getAllAppointments() {

		return appointmentServiceImpl.getAllAppointments();
	}

	/**
	 * @param doctorId
	 * 
	 * Return the list of appointments under particular doctor
	 * 
	 * @return appointmentDTO
	 */
	@GetMapping("/appointment/doctor/{doctorId}")
	public List<AppointmentDTO> getAppointmentByDoctorId(@PathVariable Long doctorId) {

		return appointmentServiceImpl.getAppointmentByDoctorId(doctorId);
	}

	/**
	 * @param patientId
	 * 
	 * This will provide the appointment detail of patient
	 * 
	 * @return appointmentDTO
	 */
	@GetMapping("/appointment/patient/{patientId}")
	public AppointmentDTO getAppointmentByPatientId(@PathVariable Long patientId) {

		return appointmentServiceImpl.getAppointmentByPatientId(patientId);
	}

	/**
	 * @param appointmentDTO
	 * 
	 * This controller will save new appointment and update detail in the database
	 *           
	 * @return appointmentDTO
	 * 
	 * @throws Exception
	 */
	@PostMapping("/appointment/booking")
	public AppointmentDTO saveAndUpdate(@Valid @RequestBody AppointmentDTO appointmentDTO) throws Exception {

		logger.info("To save appointment");
		return appointmentServiceImpl.saveAndUpdate(appointmentDTO);

	}

	

	/**
	 * @param appointmentId
	 * 
	 * This controller will delete particular appointment
	 * 
	 * @return deleted appointmentDTO
	 * 
	 * @throws Exception
	 */
	@DeleteMapping("/appointment/delete/{appointmentId}")
	public void deleteAppointment(@PathVariable Long appointmentId) throws Exception {

		appointmentServiceImpl.deleteAppointment(appointmentId);
	}

	/**
	 * @param appointmentDate
	 * 
	 * This controller will gives the list of appointment w.r.t appointmentDate
	 * 
	 * @return AppointmentDTO
	 */
	@GetMapping("/appointment/date/{appointmentDate}")
	public List<AppointmentDTO> getAppointmentByDate(@PathVariable String appointmentDate) {

		
		return appointmentServiceImpl.getAppointmentByDate(appointmentDate);
	}
	
	
	/**
	 * @param appointmentId
	 * 
	 * @return appointment detail regarding appointmentId
	 * 
	 * @throws Exception
	 */
	@GetMapping("/appointment/{appointmentId}")
	public AppointmentDTO getAppointmentById(@PathVariable Long appointmentId) throws Exception {
		
		return appointmentServiceImpl.getAppointmentById(appointmentId);
	}
	

//public List<AppointmentDTO> getAppointmentByDate() {
//		return appointmentServiceImpl.getAppointmentByDate(appointmentDate);
//	}
 	
}
