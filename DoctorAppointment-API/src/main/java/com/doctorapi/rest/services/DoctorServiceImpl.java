package com.doctorapi.rest.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.doctorapi.rest.repositories.UserDao;
import com.doctorapi.restutil.CommonUtils;

@Service
public class DoctorServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private UserDao userDao;
	
	
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
//		if(doctorDTO.getRoleId()==null || doctorDTO.getRoleId()==0 || doctorDTO.getRoleId()==1) {
//			logger.info("roleId Invalid.Please enter roleId 2 for doctor.");
//			throw new Exception ("roleId Invalid.Please enter roleId 2 for doctor.");
//		}
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
		
//		if(doctor.getId()== null) {
//			throw new Exception ("Doctor Id is not present.");
//		}
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
	public DoctorDTO saveAndUpdate(DoctorDTO doctorDTO) throws Exception {
		logger.info("To save and update doctor");
		DoctorDTO docDTO = null;
		validateDoctorDTO(doctorDTO);
		 Doctor doctor;
		 
		 if(doctorDTO.getId()!=null) {
			 
			 doctor = validateAndUpdate(doctorDTO);
			 Optional<User> u = userDao.findById(doctorDTO.getUserId());
			 doctor.setModifiedBy(u.get().getEmail());
			 
			 docDTO=new DoctorDTO(doctorDao.save(doctor));
			 if(docDTO!=null) {
				 logger.info("Updated successfully.");
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
			 r.setId(2L);
			 u.setRole(r);
				 
				 Long id =  userDao.save(u).getId();
				 
				 doctor = new Doctor(doctorDTO);
				 doctor.setActive(true);
				 doctor.getUser().setId(id);
				 doctor.setCreatedBy(userDao.findById(id).get().getEmail());
				 
				 docDTO=new DoctorDTO(doctorDao.save(doctor));
				 if(docDTO!=null) {
					 logger.info("Saved successfully.");
				 }
		 }
		return docDTO;
		
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
		if(doctorDTO.getEmail() !=null) {
			doctor.setEmail(doctorDTO.getEmail());
		}
		if(doctorDao.findByUserId(doctorDTO.getUserId())==null) {
			 logger.info("Please enter your valid userId.");
			 throw new Exception ("Please enter your valid userId.");
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


	/**
	 * @param createdOn
	 * 
	 * @return This method will provide the list of doctor's w.r.t the createdDate.
	 * 
	 * @throws Exception
	 */
	public List<DoctorDTO> getDoctorByCreatedOn(String createdOn) throws Exception {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		// convert string into Localdatetime format.....
		LocalDateTime localDateTime = LocalDateTime.parse(createdOn, dtf);
		System.out.println("Date :"+localDateTime);
		
		
		List<DoctorDTO> doctorDTOList = doctorDao.findByCreatedOn(localDateTime).stream().map(DoctorDTO::new).collect(Collectors.toList());
		return doctorDTOList;
	}


	/**
	 * @param modifiedOn
	 * 
	 * @return This method will provide the list of doctor's w.r.t the modifiedDate.
	 */
	public List<DoctorDTO> getDoctorByModifiedOn(String modifiedOn) {

		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;
		
		// convert string into Localdatetime format.....
			LocalDateTime localDateTime = LocalDateTime.parse(modifiedOn, dateTimeFormat);
			System.out.println("Date :"+localDateTime);
			
			List<DoctorDTO> doctorDTOList = doctorDao.findByModifiedOn(localDateTime).stream().map(DoctorDTO::new).collect(Collectors.toList());
		return doctorDTOList;
	}
	
	
	
}
	
	
