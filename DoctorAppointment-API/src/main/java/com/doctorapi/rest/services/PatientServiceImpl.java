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
import com.doctorapi.rest.dto.PatientDTO;
import com.doctorapi.rest.models.Patient;
import com.doctorapi.rest.models.Role;
import com.doctorapi.rest.models.User;
import com.doctorapi.rest.repositories.PatientDao;
import com.doctorapi.rest.repositories.UserDao;
import com.doctorapi.restutil.CommonUtils;



@Service
public class PatientServiceImpl {
	
	private EmailService emailService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public PatientServiceImpl(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	
	
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

		return new PatientDTO(patient);
	}
	
	

	/**
	 * @param patientDTO
	 * 
	 * This method will save new patient as well as add as user and update detail into the table 
	 * 
	 * @throws Exception
	 */
	public PatientDTO saveAndUpdate(PatientDTO patientDTO) throws Exception {
		
		logger.info("To save patient and update detail.");
		
		PatientDTO patDTO = null;
		validatePatientDTO(patientDTO);
		Patient patient;
		
		if(patientDTO.getId()!=null) {
			
			patient = validateAndUpdate(patientDTO);
			Optional<User> u = userDao.findById(patientDTO.getUserId());
			patient.setModifiedBy(u.get().getEmail());
			
			patDTO = new PatientDTO(patientDao.save(patient));
			logger.info("Updated Successfully");
			try {
//				emailService.sendPatientUpdationMail(patient,patientDTO.getFirstName()+" "+patientDTO.getLastName());
				emailService.sendPatientUpdationMail(patientDTO);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			if(patientDao.findByEmail(patientDTO.getEmail())!=null) {
				logger.info("Email already exist. Please enter valid email.");
				 throw new Exception ("Email already exist. Please enter valid email.");
				}
			
			
			//Adding new patient detail
			User user = new User();
			
			user.setEmail(patientDTO.getEmail());
			user.setPassword(patientDTO.getPassword());
			user.setActive(patientDTO.isActive());
			Role r = new Role();
			r.setId(1L);
			user.setRole(r);

				patient = new Patient(patientDTO);
				patient.setActive(true);
				patient.setUser(user);
				patient.setCreatedBy(patientDTO.getEmail());
				
				try {
					emailService.sendPatientNotificationMail(patientDTO);
				}catch (Exception e) {
					e.printStackTrace();
				}
				patDTO = new PatientDTO(patientDao.save(patient));
				logger.info("Saved Successfully");
			}
			return patDTO;
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
		if(patientDTO.getEmail()!=null) {
			patient.setEmail(patientDTO.getEmail());
		}
		if(patientDTO.getAddress()!=null) {
			patient.setAddress(patientDTO.getAddress());
		}
		if(patientDTO.getPassword()!=null) {
			patient.setPassword(patientDTO.getPassword());
		}
		if(patientDTO.getAge()<0 || patient.getAge()==0) {
			logger.info("Age must be greater then 0.Please enter valid age.");
			throw new Exception("Age must be greater then 0.Please enter valid age.");
		}
		if(patientDTO.getAge()!=0) {
			patient.setAge(patientDTO.getAge());
		}
		patient.setGender(Gender.getEnum(patientDTO.getGender()));
		logger.info("Returning patient after validating patientDTO object to update.");
		return patient;
	}

	
	
	/**
	 * @param createdOn
	 * 
	 * @return This method provide the lis of patient regarding createdDate.
	 * @throws Exception 
	 * 
	 */
	public List<PatientDTO> getPatientByCreatedOn(String createdOn) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(createdOn);
		
		List<PatientDTO> patientDTOList = patientDao.findAllWithCreatedOn(date1).stream().map(PatientDTO::new).collect(Collectors.toList());
		return patientDTOList;
	}

	
	/**
	 * @param modifiedOn
	 * 
	 * @return This method provide the lis of patient regarding modifiedDate.
	 * @throws Exception 
	 * 
	 */
	public List<PatientDTO> getPatientByModifiedOn(String modifiedOn) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(modifiedOn);
		
		List<PatientDTO> patientDTOList = patientDao.findAllWithModifiedOn(date1).stream().map(PatientDTO::new).collect(Collectors.toList());
		return patientDTOList;
	} 
	
	
	
}	
	
