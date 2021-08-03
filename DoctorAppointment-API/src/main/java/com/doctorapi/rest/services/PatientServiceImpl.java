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
import com.doctorapi.rest.dto.PatientDTO;
import com.doctorapi.rest.models.Patient;
import com.doctorapi.rest.models.Role;
import com.doctorapi.rest.models.User;
import com.doctorapi.rest.repositories.PatientDao;
import com.doctorapi.rest.repositories.RoleDao;
import com.doctorapi.rest.repositories.UserDao;
import com.doctorapi.restutil.CommonUtils;

import javassist.NotFoundException;


@Service
public class PatientServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	
	
	/**
	 * @param patient
	 * 
	 * This method will validate Patient object
	 * 
	 * @throws Exception
	 */
	private void validatePatient(Patient patient) throws Exception {
		logger.info("To validate patient object to save.");
		
		if(StringUtils.isBlank(patient.getFirstName()) || patient.getFirstName().length()>=10) {
			logger.error("Please enter your firstName and length must be less than 10");
			throw new Exception("Please enter your firstName and length must be less than 10");
		}
		if(StringUtils.isBlank(patient.getLastName()) || patient.getLastName().length()>=10) {
			logger.error("Please enter your lastName and length must be less than 10");
			throw new Exception("Please enter your lastName and length must be less than 10");
		}
		if(StringUtils.isBlank(patient.getEmail()) || !CommonUtils.validateEmail(patient.getEmail()) || 
				patient.getEmail().length() > 50) {
			logger.error("Please enter valid email address.");
			throw new Exception("Please enter valid email address.");
		}
		if(StringUtils.isBlank(patient.getPassword()) || !CommonUtils.validatePassword(patient.getPassword()) ||
				patient.getPassword().length() > 60) {
			logger.info("Please enter valid password.");
			throw new Exception("Please enter valid password.");
		}
		if(StringUtils.isBlank(patient.getAddress())) {
			logger.error("Please enter your address.");
			throw new Exception ("Please enter your address.");
		}
		if(StringUtils.isBlank(patient.getGender().getGender())) {
			logger.error("Please enter your gender.");
			throw new Exception ("Please enter your gender.");
		}
		if(patient.getAge()<0 || patient.getAge()==0) {
			logger.info("Age must be greater than 0.It should not be null.");
			throw new Exception ("Age must be greater than 0.It should not be null.");
		}
	}
	
	/**
	 * @param patientDTO
	 * 
	 * This method will validate PatientDTO object
	 * 
	 * @throws Exception
	 */
	private void validatePatientDTO(PatientDTO patientDTO) throws Exception {
		
		logger.info("To validate patientDTO object");

		if(StringUtils.isBlank(patientDTO.getAddress())) {
			logger.error("Address not be empty and null.");
			throw new Exception("Address not be empty and null.");
		}
		if(patientDTO.getAge()<0 || patientDTO.getAge()==0) {
			logger.info("Age must be greater than 0.");
			throw new Exception ("Age must be greater than 0.");
		}
		if(StringUtils.isBlank(patientDTO.getFirstName())) {
			logger.error("firstName should not be Null or Empty.");
			throw new Exception("firstName should not be Null or Empty.");
		}
		if(StringUtils.isBlank(patientDTO.getLastName())) {
			logger.error("lastName should not be Null or Empty.");
			throw new Exception("lastName should not be Null or Empty.");
		}
		if(StringUtils.isBlank(patientDTO.getEmail()) || !CommonUtils.validateEmail(patientDTO.getEmail()) || 
				patientDTO.getEmail().length() > 50) {
			logger.error("Please enter valid email.");
			throw new Exception("Please enter valid email.");
		}
		if(StringUtils.isBlank(patientDTO.getPassword()) || !CommonUtils.validatePassword(patientDTO.getPassword()) ||
				patientDTO.getPassword().length() > 60) {
			logger.info("Please enter valid password.");
			throw new Exception("Please enter valid password.");
		}
		if(Gender.getEnum(patientDTO.getGender())==null) {
			logger.info("Enter the valid gender type."); 
			throw new Exception ("Enter the valid gender type.");
		}
//		if(patientDTO.getRoleId()==null || patientDTO.getRoleId()==0 || patientDTO.getRoleId()==2) {
//			logger.info("roleId Invalid.Please enter roleId 1 for patient.");
//			throw new Exception ("roleId Invalid.Please enter roleId 1 for patient.");
//		}
	}
	
	
	
	/**
	 *This method will return the list of patient
	 */
	public List<PatientDTO> getPatients() {
		
		return patientDao.findByOrderByIdAsc().stream().map(PatientDTO::new).collect(Collectors.toList());
		
	}
	
	
	/**
	 * @param patientId
	 * This method will return the individual patient detail regarding patientId
	 * 
	 * @throws Exception
	 */
	public PatientDTO getPatientById(Long patientId) throws Exception{
		
		Patient patient = validateAndGetPatient(patientId);
//		if(patient.getId()==null) {
//			throw new Exception("Patient Id is not present.");
//		}
		return new PatientDTO(patient);
	}
	
	

	/**
	 * @param patientDTO
	 * 
	 * This method will save new patient as well as add as user and update detail into the table 
	 * 
	 * @throws Exception
	 */
	public String saveAndUpdate(PatientDTO patientDTO) throws Exception {
		
		logger.info("To save patient and update detail.");
		
		PatientDTO patDTO = null;
		String message = null;
		
		validatePatientDTO(patientDTO);
		Patient patient;
		
		if(patientDTO.getId()!=null) {
			
			patient = validateAndUpdate(patientDTO);
			
			patDTO = new PatientDTO(patientDao.save(patient));
			if(patDTO!=null) {
				message = "Updated Successfully";
			}
			
		}else {
			if(patientDao.findByEmail(patientDTO.getEmail())!=null) {
				logger.info("Email already exist. Please enter valid email.");
				 throw new Exception ("Email already exist. Please enter valid email.");
				}
			
			
			//Adding new patient detail
			User u = new User();
			
			u.setEmail(patientDTO.getEmail());
			u.setPassword(patientDTO.getPassword());
			u.setActive(patientDTO.isActive());
			
			Role r = new Role();
			r.setId(1L);
			u.setRole(r);
			
			if(r.getId()==2) {
				
				logger.info("RoleName does not match to the patient.");
				 message= "RoleName does not match to the patient.";
				 
			}else if(r.getId()==1) {
				
				Long id = userDao.save(u).getId();
				
				patient = new Patient(patientDTO);
				patient.setActive(true);
				patient.getUser().setId(id);
				
				patDTO = new PatientDTO(patientDao.save(patient));
				if(patDTO!=null) {
					message = "Saved Successfully";
				}
			}else {
					message="Invalid role";
				}
				
			}
			return message;
		}

	
	
	/**
	 * @param patientDTO
	 * 
	 * This method will validate updatePatientDTO object
	 * 
	 * @throws Exception
	 */
	private void validateUpdatePatientDTO(PatientDTO patientDTO) throws Exception {
		
		if(patientDTO.getId()==null || patientDTO.getId().equals(" ")) {
			throw new Exception("PatientId should not be null or empty.");
		}
		if(StringUtils.isBlank(patientDTO.getPassword())) {
			throw new Exception("Password must not be null or empty.");
		}
		if(StringUtils.isBlank(patientDTO.getEmail())) {
			throw new Exception("Email must not be null or empty.");
		}
		if(StringUtils.isBlank(patientDTO.getFirstName())) {
			throw new Exception ("First Name is compulsory.");
		}
		if(StringUtils.isBlank(patientDTO.getLastName())) {
			throw new Exception ("Last Name is compulsory.");
		}
		if(StringUtils.isBlank(patientDTO.getGender())) {
			throw new Exception ("Gender not be null or empty.");
		}
		
	}
	
	
	/**
	 * @param patientId
	 * 
	 * This method will validate the patient regarding patientId
	 * 
	 * @return patient object
	 * 
	 * @throws Exception
	 */
	public Patient validateAndGetPatient(Long patientId) throws Exception {
		
		logger.info("To validate patient regarding patientId.");
		Optional<Patient> patient = patientDao.findById(patientId);
		if(!patient.isPresent()) {
			throw new Exception("Patient not found.");
		}
		return patient.get();
	}
	
	
	/**
	 * @param patientDTO
	 * 
	 * @return This will validate patientDTO object to update. 
	 * 
	 * @throws Exception
	 */
	public Patient validateAndUpdate(PatientDTO patientDTO) throws Exception {
		
		logger.info("To validate patientDTO to update.");
		Patient patient= validateAndGetPatient(patientDTO.getId());
		
		if(patientDTO.getFirstName() !=null) {
			patient.setFirstName(patientDTO.getFirstName());
		}
		if(patientDTO.getLastName() !=null) {
			patient.setLastName(patientDTO.getLastName());
		}
		if(patientDTO.getEmail() != null) {
			patient.setEmail(patientDTO.getEmail());
		}
//		
		if(patientDao.findByUserId(patientDTO.getUserId())==null) {
			logger.info("Pleasem enter valid userId.");
			throw new Exception ("Please enter valid userId.");
		}
		if(patientDTO.getRoleId()==null || patientDTO.getRoleId()==0 || patientDTO.getRoleId()==2) {
			logger.info("Please enter roleId 1 for patient.");
			throw new Exception ("Please enter roleId 1 for patient.");
		}
		if(patientDTO.getEmail()!=null) {
			patient.setEmail(patientDTO.getEmail());
		}
		if(patientDTO.getAddress()!=null) {
			patient.setAddress(patientDTO.getAddress());
		}
		if(patientDTO.getPassword()!=null) {
			patient.setPassword(patientDTO.getPassword());
		}
		if(patientDTO.getAge()!=0) {
			patient.setAge(patientDTO.getAge());
		}
		patient.setGender(Gender.getEnum(patientDTO.getGender()));
		logger.info("Returning patient after validating patientDTO object to update.");
		return patient;
	} 
	
	
	
}	
	
