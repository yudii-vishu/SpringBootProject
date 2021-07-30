package com.doctorapi.rest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapi.rest.Enum.Gender;
import com.doctorapi.rest.Enum.Status;
import com.doctorapi.rest.dto.DoctorDTO;
import com.doctorapi.rest.models.Doctor;
import com.doctorapi.rest.models.Role;
import com.doctorapi.rest.models.User;
import com.doctorapi.rest.repositories.DoctorDao;
import com.doctorapi.rest.repositories.RoleDao;
import com.doctorapi.rest.repositories.UserDao;
import com.doctorapi.restutil.CommonUtils;

import javassist.NotFoundException;

@Service
public class DoctorServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired 
	private RoleDao roleDao;
	

	/**
	 * @param doctor
	 * 
	 * This method will validate doctor object
	 * 
	 * @throws Exception
	 */
	private void validateDoctor(Doctor doctor) throws Exception {
		
		logger.info("To validate doctor object to save.");
		if(StringUtils.isBlank(doctor.getName()) || doctor.getName().length() > 20) {
			logger.error("Please enter doctorName.");
			throw new Exception("Please enter doctorName.");
		}
		if(StringUtils.isBlank(doctor.getEmail()) || !CommonUtils.validateEmail(doctor.getEmail()) || 
				doctor.getEmail().length() > 50) {
			logger.error("Please enter valid email address.");
			throw new Exception("Please enter email address.");
		}
		if(StringUtils.isBlank(doctor.getPassword()) || !CommonUtils.validatePassword(doctor.getPassword()) ||
				doctor.getPassword().length() > 60) {
			logger.error("Please enter valid password.");
			throw new Exception("Please enter the password.");
		}
		if(StringUtils.isBlank(doctor.getStatus().getStatus())) {
			logger.error("Please enter your status (available / Not available).");
			throw new Exception ("Please enter your status (available / Not available).");
		}
		if(StringUtils.isBlank(doctor.getGender().getGender())) {
			logger.error("Please enter your gender.");
			throw new Exception ("Please enter your gender.");
		}
		if(doctor.getDate()==null || doctor.getDate().equals("")) {
			logger.error("Please enter the date (yyyy-MM-dd)");
			throw new Exception ("Please enter the date (yyyy-MM-dd)"); 
		}
	}
	
	
	/**
	 * @param doctorDTO
	 * 
	 * This method will validate doctorDTO object
	 * 
	 * @throws Exception
	 */
	private void validateDoctorDTO(DoctorDTO doctorDTO) throws Exception {
		
		logger.info("To validate doctorDTO objec.");

		if(StringUtils.isBlank(doctorDTO.getName())) {
			logger.error("doctorName must not be null or Empty.");
			throw new Exception("doctorName must not be null or Empty.");
		}
		if(StringUtils.isBlank(doctorDTO.getStatus())) {
			logger.error("doctor status must be available or Not Available and should not be null/empty");
			throw new Exception("doctor status must be available or Not Available and should not be null/empty");
		}
		if(StringUtils.isBlank(doctorDTO.getEmail()) || !CommonUtils.validateEmail(doctorDTO.getEmail()) || 
				doctorDTO.getEmail().length() > 50) {
			logger.error("Please enter valid email.");
			throw new Exception("Please enter valid email.");
		}
		if(StringUtils.isBlank(doctorDTO.getPassword()) || !CommonUtils.validatePassword(doctorDTO.getPassword()) ||
				doctorDTO.getPassword().length() > 60) {
			logger.info("Please enter valid password.It contains atleast 1UpperCase letter, 1special symbol and numbers.");
			throw new Exception("Please enter valid password.It contains atleast 1UpperCase letter, 1special symbol and numbers");
		}
		if(Gender.getEnum(doctorDTO.getGender())==null) {
			logger.info("Enter the valid gender type.");
			throw new Exception ("Enter the valid gender type.");
		}
		if(doctorDTO.getDate()==null || doctorDTO.getDate().equals("")) {
			logger.error("Please enter the date (yyyy-MM-dd)");
			throw new Exception ("Please enter the date (yyyy-MM-dd)"); 
		}
		if(doctorDTO.getRoleId()==null || doctorDTO.getRoleId()==0 || doctorDTO.getRoleId()==1) {
			logger.info("roleId Invalid.Please enter roleId 2 for doctor.");
			throw new Exception ("roleId Invalid.Please enter roleId 2 for doctor.");
		}
	}
	
	

	/**
	 * This method will return List of doctor
	 * 
	 * @return doctorDTO
	 */
	public List<DoctorDTO> getDoctors() {
		
		return doctorDao.findByOrderByIdAsc().stream().map(DoctorDTO::new).collect(Collectors.toList());

  }


	/**
 	 * @param doctorId
 	 * 
	 * This method will return doctor detail regarding doctorId
	 * 
	 * @throws Exception
	 */
	public DoctorDTO getDoctorById(Long doctorId) throws Exception{
		
		Doctor doctor = validateAndGetDoctor(doctorId);
		
		if(doctor.getId()== null) {
			throw new Exception ("Doctor Id is not present.");
		}
		return new DoctorDTO(doctor);
	}

	
	
	/**
	 * @param status
	 * 
	 * This method gives the list of Available doctors
	 * 
	 * @return DoctorDTO
	 */
	public List<DoctorDTO> getAvailability(Status status) {
		
		
		return doctorDao.findByOrderByStatus(status).stream().map(DoctorDTO::new).collect(Collectors.toList());
		 
	}
		
	
	
	/**
	 * @param doctorDTO
	 * 
	 * This method will save new doctor and update doctor detail to the table
	 * 
	 * @throws Exception
	 */
	public String saveAndUpdate(DoctorDTO doctorDTO) throws Exception {
		logger.info("To save and update doctor");
		DoctorDTO docDTO = null;
		String message = null;
		
		 validateDoctorDTO(doctorDTO);
		 Doctor doctor;
		 
		 if(doctorDTO.getId()!=null) {
			 
			 doctor = validateAndUpdate(doctorDTO);
			 
			 docDTO=new DoctorDTO(doctorDao.save(doctor));
			 if(docDTO!=null) {
				 message= "Updated successfully.";
				 }
		 }else {
			 if(doctorDao.findByEmail(doctorDTO.getEmail())!=null) {
				 logger.info("Email already exist. Please enter valid email.");
				 throw new Exception ("Email already exist. Please enter valid email.");
			 }
			 
			
			 // Adding new doctor detail
			 User u = new User();
			 u.setEmail(doctorDTO.getEmail());
			 u.setPassword(doctorDTO.getPassword());
			 u.setActive(doctorDTO.isActive());
			 Role r = new Role();
			 r.setId(doctorDTO.getRoleId());
			 u.setRole(r);
			 
			 if(r.getId()== 1) {
				 logger.info("RoleName does not match to the doctor.");
				 message= "RoleName does not match to the doctor.";
				 
			 }else if(r.getId()==2) {
				 
				Long id =  userDao.save(u).getId();
				 
				 doctor = new Doctor(doctorDTO);
				 doctor.setActive(true);
				 doctor.getUser().setId(id);
				 
				 docDTO=new DoctorDTO(doctorDao.save(doctor));
				 if(docDTO!=null) {
					 message= "Saved successfully.";
				 }
			 }else {
				 message="Invalid role";
			 }
			 
		 }
		return message;
		
	}
	
	

	/**
	 * @param doctorId
	 * 
	 * This method will delete the doctor detail regarding doctorId
	 * 
	 * @throws Exception 
	 */
	public void deleteDoctor(Long doctorId) throws Exception  {
		
		Optional<Doctor> doctorOpt = doctorDao.findById(doctorId);
		if(doctorOpt.isPresent()) {
			doctorDao.deleteById(doctorId);
		}else {
			throw new Exception("Doctor is not present.");
		}
	}
	
	
	/**
	 * @param doctorId
	 * 
	 * This method validate the doctor regarding doctorId.
	 * 
	 * @return doctor object.
	 * 
	 * @throws Exception
	 */
	public Doctor validateAndGetDoctor(Long doctorId)  throws Exception {
		
		logger.info("To validate doctor regarding doctorId.");
		Optional<Doctor> doctor = doctorDao.findById(doctorId);
		if(!doctor.isPresent()) {
			throw new Exception ("Doctor not found.");
		}
		return doctor.get();
	}
	
	
	/**
	 * @param doctorDTO
	 * 
	 * @return this method will validate doctorDTO object to update
	 * 
	 * @throws Exception
	 */
	public Doctor validateAndUpdate(DoctorDTO doctorDTO) throws Exception {
		
		logger.info("To validate doctorDTO to update.");
		Doctor doctor= validateAndGetDoctor(doctorDTO.getId());
		
		if(doctorDTO.getName()!=null) {
			doctor.setName(doctorDTO.getName());
		}
		if(doctor.getEmail().equals(doctorDTO.getEmail())) {
			logger.info("Email already exist.");
			throw new Exception("Email already exist.");
		}
		if(doctorDao.findByUserId(doctorDTO.getUserId())==null) {
			 logger.info("Please enter your valid userId.");
			 throw new Exception ("Please enter your valid userId.");
		 }
		if(doctorDTO.getRoleId()==null || doctorDTO.getRoleId()==0 || doctorDTO.getRoleId()==1) {
			logger.info("Please enter roleId 2 for doctor.");
			throw new Exception ("Please enter roleId 2 for doctor.");
		}
		if(doctorDTO.getEmail()!=null) {
			doctor.setEmail(doctorDTO.getEmail());
		}
		if(doctorDTO.getDate()!=null) {
			doctor.setDate(doctorDTO.getDate());
		}
		if(doctorDTO.getPassword()!=null) {
			doctor.setPassword(doctorDTO.getPassword());
		}
		doctor.setStatus(Status.getEnum(doctorDTO.getStatus()));
		doctor.setGender(Gender.getEnum(doctorDTO.getGender()));
		logger.info("Returning doctor after validating doctorDTO object to update.");
		return doctor;
	}
	
	
	
}
	
	
