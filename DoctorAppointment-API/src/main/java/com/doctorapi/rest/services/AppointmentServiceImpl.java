package com.doctorapi.rest.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapi.rest.Enum.Action;
import com.doctorapi.rest.dto.AppointmentDTO;
import com.doctorapi.rest.dto.DoctorDTO;
import com.doctorapi.rest.models.Appointment;
import com.doctorapi.rest.models.Doctor;
import com.doctorapi.rest.repositories.AppointmentDao;
import com.doctorapi.rest.repositories.DoctorDao;


@Service
public class AppointmentServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	
	
	/**
	 * @param appointment
	 * 
	 * This method will validate appointment object
	 * 
	 * @throws Exception
	 */
	private void validateAppointment(Appointment appointment) throws Exception {
		
		logger.info("To validate appointment object to save.");
		if(StringUtils.isBlank(appointment.getAction().getAction())) {
			logger.error("Please enter action 'BOOK' ");
			throw new Exception("Please enter action 'BOOK' ");
		}
		if(appointment.getAppointmnetDate()==null || appointment.getAppointmnetDate().equals("")) {
			logger.error("Please enter the formated date (yyyy-MM-dd)");
			throw new Exception("Please enter the formated date (yyyy-MM-dd)");
		}
		if(StringUtils.isBlank(appointment.getReason())) {
			logger.error("Please enter your valid reason for Appointment.");
			throw new Exception ("Please enter your valid reason for Appointment.");
		}
		if(appointment.getPatient().getId() == null || appointment.getPatient().getId()==0) {
			logger.error("Please enter the patient Id.");
			throw new Exception ("Please enter the patient Id.");
		}
		if(appointment.getDoctor().getId() == null || appointment.getDoctor().getId()==0) {
			logger.error("Please enter the doctor Id.");
			throw new Exception ("Please enter the doctor Id.");
		}
	}
	
	
	/**
	 * @param appointmentDTO
	 * 
	 * This method will validate the appointmetnDTO object
	 * 
	 * @throws Exception
	 */
	private void validateAppointmentDTO(AppointmentDTO appointmentDTO) throws Exception {
		
		logger.info("To validate appointmentDTO object.");
		
		if(StringUtils.isBlank(appointmentDTO.getAction())) {
			logger.info("Appointment action should not be null or blank.");
			throw new Exception ("Appointment action should not be null or blank.");
		}
		if(appointmentDTO.getDoctorId()==null || appointmentDTO.getDoctorId()==0) {
			logger.info("doctorId must be greater than 0.It should not be null/empty");
			throw new Exception ("doctorId must be greater than 0.It should not be null/empty");
		}
		if(StringUtils.isBlank(appointmentDTO.getReason())) {
			logger.error("Appointment reason should not be null or blank.");
			throw new Exception("Appointment reason should not be null or blank.");
		}
		if(appointmentDTO.getPatientId()==null || appointmentDTO.getPatientId()==0) {
			logger.error("patientId must be greater than 0.It should not be null/empty");
			throw new Exception ("patientId must be greater than 0.It should not be null/empty");
		}
		if(appointmentDTO.getAppointmnetDate()==null || appointmentDTO.getAppointmnetDate().equals("")) {
			logger.error("Please enter the appointmentDate (yyyy-MM-dd)");
			throw new Exception ("Please enter the appointmentDate (yyyy-MM-dd)"); 
		}
		if(appointmentDTO.getAppointmentSlot()==null || appointmentDTO.getAppointmentSlot().equals("")) {
			logger.error("Please enter valid appointmentSlot (hh:mm:ss)");
			throw new Exception("Please enter valid appointmentSlot (hh:mm:ss)");
		}
		
	}
	
	
	/**
	 * This method will return list of appointment 
	 */
	public List<AppointmentDTO> getAllAppointments() {
		
		return appointmentDao.findByOrderByIdAsc().stream().map(AppointmentDTO::new).collect(Collectors.toList());
		
	}
	
	
	/**
	 * @param appointmentDTO
	 * 
	 * This method will save the new appointment and update appointment details
	 * 
	 * @throws Exception
	 */
	public AppointmentDTO saveAndUpdate(AppointmentDTO appointmentDTO) throws Exception {
		
		logger.info("To save appointment.");
		validateAppointmentDTO(appointmentDTO);
		Appointment appointment;
		
		if(appointmentDTO.getId()!=null) {
			
			appointment = validateAndUpdate(appointmentDTO);
		}else {
//			if(appointmentDao.findByReason(appointmentDTO.getReason())!=null) {
//				logger.info("AppointmentReason is already exist.Please enter valid reason");
//				 throw new Exception ("AppointmentReason is already exist.Please enter valid reason");
//			}
			appointment = new Appointment(appointmentDTO);
		}
		return new AppointmentDTO(appointmentDao.save(appointment));
	}
		
	

	/**
	 * @param doctorId
	 * 
	 * This is method will return the list of appointment regarding doctorId
	 * 
	 * @return appointmentDTOList
	 */
	public List<AppointmentDTO> getAppointmentByDoctorId(Long doctorId) {
		
		return appointmentDao.findAppointmentByDoctorId(doctorId).stream().map(AppointmentDTO::new).collect(Collectors.toList());
		
		
	}
	
		
	/**
	 * @param appointmentId
	 * 
	 * This method will delete the appointment record from the table regarding appointmentId
	 * 
	 * @throws Exception
	 */
	public void deleteAppointment(Long appointmentId) throws Exception{
		
			Optional<Appointment> appointmentOpt = appointmentDao.findById(appointmentId);
			
		if(appointmentOpt.isPresent()) {
			appointmentDao.deleteById(appointmentId);
		}else {
			throw new Exception ("Appointment does not exist.");
		}
	}
	

	/**
	 * @param patientId
	 * 
	 * This method will return the appointment detail regarding patientId
	 * 
	 * @return appointmentDTO
	 */
	public AppointmentDTO getAppointmentByPatientId(Long patientId) {
		
		Appointment appointment = appointmentDao.getAppointmentByPatientId(patientId);
		
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		
		appointmentDTO.setId(appointment.getId());
		appointmentDTO.setAction(appointment.getAction().getAction());
		appointmentDTO.setAppointmnetDate(appointment.getAppointmnetDate());
		appointmentDTO.setAppointmentSlot(appointment.getAppointmentSlot());
		appointmentDTO.setReason(appointment.getReason());
		appointmentDTO.setPatientId(appointment.getPatient().getId());
		appointmentDTO.setPatientFirstName(appointment.getPatient().getFirstName());
		appointmentDTO.setPatientLastName(appointment.getPatient().getLastName());
		appointmentDTO.setDoctorId(appointment.getDoctor().getId());
		appointmentDTO.setDoctorName(appointment.getDoctor().getName());
		
		return appointmentDTO;
		

	}


	/**
	 * @param appointmentDate
	 * 
	 * This method will return appointmentList with respect to the appointmentDate
	 * 
	 * @return AppointmentDTO
	 */
	public List<AppointmentDTO> getAppointmentByDate(String appointmentDate) {
		
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		// convert string into localDate
		LocalDate localdate = LocalDate.parse(appointmentDate, dateTimeFormat);
		
		List<AppointmentDTO> appointmentDTOList = appointmentDao.findByAppointmnetDate(localdate).stream().map(AppointmentDTO::new)
				.collect(Collectors.toList()) ;
		
		return appointmentDTOList;
	}
	
	
	
	/**
	 * @param appointmentDTO
	 * 
	 * This method validate appointmentDTO to update
	 * 
	 * @return appointment
	 * 
	 * @throws Exception
	 */
	public Appointment validateAndUpdate(AppointmentDTO appointmentDTO) throws Exception {
		
		logger.info("To validate AppointmentDTO to update.");
		Appointment appointment = validateAndGetAppointment(appointmentDTO.getId());
		
		if(appointmentDTO.getAppointmnetDate()!=null) {
			appointment.setAppointmnetDate(appointmentDTO.getAppointmnetDate());
		}
		if(appointmentDTO.getAppointmentSlot()!=null) {
			appointment.setAppointmentSlot(appointmentDTO.getAppointmentSlot());
		}
		if(appointment.getReason().equals(appointmentDTO.getReason())) {
			 logger.info("AppointmentReason is already exist.");
			 throw new Exception ("AppointmentReason is already exist.");
		}
		if(appointmentDTO.getReason()!=null) {
			appointment.setReason(appointmentDTO.getReason());
		}
		appointment.setAction(Action.getEnum(appointmentDTO.getAction()));
		logger.info("Returning appointment after validating appointmentDTO object to update.");
		return appointment;
	}

	

	/**
	 * @param appointmentId
	 * 
	 * This method will return appointment w.r.t appointmentId
	 * 
	 * @return appointmentDTO
	 * 
	 * @throws Exception
	 */
	public AppointmentDTO getAppointmentById(Long appointmentId) throws Exception {
		
		Appointment appointment = validateAndGetAppointment(appointmentId);
		
		if(appointment.getId()==null) {
			throw new Exception ("Appointment Id is not present.");
		}
		return new AppointmentDTO(appointment);
	}
	
	
	/**
	 * @param appointmentId
	 * 
	 * This method will validate doctor regarding appointmentId.
	 * 
	 * @return appointment
	 * 
	 * @throws Exception
	 */
	public Appointment validateAndGetAppointment(Long appointmentId) throws Exception {
		
		logger.info("To validate doctor regarding appointmentId.");
		Optional<Appointment> appointment = appointmentDao.findById(appointmentId);
		if(!appointment.isPresent()) {
			throw new Exception ("Appointment not found.");
		}
		return appointment.get();
	}


}
	
	
