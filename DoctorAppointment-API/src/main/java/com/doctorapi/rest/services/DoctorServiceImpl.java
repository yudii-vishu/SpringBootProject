package com.doctorapi.rest.services;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.doctorapi.rest.dto.AppointmentDTO;
import com.doctorapi.rest.dto.DoctorDTO;
import com.doctorapi.rest.dto.PatientDTO;
import com.doctorapi.rest.models.Doctor;
import com.doctorapi.rest.models.Role;
import com.doctorapi.rest.models.User;
import com.doctorapi.rest.repositories.AppointmentDao;
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
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	
	
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
			 logger.info("Updated successfully.");
				 
		 }else {
			 if(doctorDao.findByEmail(doctorDTO.getEmail())!=null) {
				 logger.info("Email already exist. Please enter valid email.");
				 throw new Exception ("Email already exist. Please enter valid email.");
			 }
			
			 // Adding new doctor detail
			 User user = new User();
			 user.setEmail(doctorDTO.getEmail());
			 user.setPassword(doctorDTO.getPassword());
			 user.setActive(doctorDTO.isActive());
			 Role r = new Role();
			 r.setId(2L);
			 user.setRole(r);

			 	 doctor = new Doctor(doctorDTO);
				 doctor.setActive(true);
				 doctor.setUser(user);
				 doctor.setCreatedBy(doctorDTO.getEmail());
				 
				 docDTO=new DoctorDTO(doctorDao.save(doctor));
				 logger.info("Saved successfully.");
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
	 * @param doctorId
	 * 
	 * @return This method provide the list of Patient regarding doctorId.
	 * 
	 */
	public List<PatientDTO> getPatientListByDoctorId(Long doctorId) {
		
		List<PatientDTO> patientList = appointmentDao.getAppointmentByDoctorId(doctorId).stream().map(p -> new PatientDTO(p,true))
				.collect(Collectors.toList());
		
		return patientList;
	}
	
	

	/**
	 * @param createdOn
	 * 
	 * @return This method will provide the list of doctor's w.r.t the createdDate.
	 * 
	 * @throws Exception
	 */
	public List<DoctorDTO> getDoctorBycreatedOn(String createdOn) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(createdOn);
		
		List<DoctorDTO> doctorDTOList = doctorDao.findAllWithCreatedOn(date1).stream().map(DoctorDTO::new).collect(Collectors.toList());
		return doctorDTOList;
	}


	
	/**
	 * @param modifiedOn
	 * 
	 * @return This method will provide the list of doctor's w.r.t the modifiedDate.
	 * @throws Exception 
	 */
	public List<DoctorDTO> getDoctorByModifiedOn(String modifiedOn) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(modifiedOn);
			
			List<DoctorDTO> doctorDTOList = doctorDao.findAllWithModifiedOn(date1).stream().map(DoctorDTO::new).collect(Collectors.toList());
		return doctorDTOList;
	}



	/**
	 * @param doctorId
	 * 
	 * @return This method provide the List of currentDate appointments of doctor by doctorId.
	 * @throws Exception 
	 * 
	 */
	public List<AppointmentDTO> getTodaysAppointment(Long doctorId) throws Exception {

		List<AppointmentDTO> appointmentList = appointmentDao.findAllAppointmentByDoctorId(doctorId).stream().map(AppointmentDTO::new)
				.collect(Collectors.toList());
		
		return appointmentList;
	}

	
	
	
	
}
	
	
