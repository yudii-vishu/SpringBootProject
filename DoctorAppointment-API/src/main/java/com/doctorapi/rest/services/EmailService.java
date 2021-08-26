package com.doctorapi.rest.services;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.doctorapi.rest.Config.PropConfig;
import com.doctorapi.rest.dto.AppointmentDTO;
import com.doctorapi.rest.dto.DoctorDTO;
import com.doctorapi.rest.dto.PatientDTO;
import com.doctorapi.rest.models.Doctor;
import com.doctorapi.rest.models.Patient;

@Service
public class EmailService {
	
	private JavaMailSender javaMailSender;
	private SpringTemplateEngine springTemplateEngine;
	private PropConfig propConfig;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender, PropConfig propConfig, SpringTemplateEngine springTemplateEngine) {
		super();
		this.javaMailSender = javaMailSender;
		this.propConfig = propConfig;
		this.springTemplateEngine = springTemplateEngine;
	}
	
	
	/**
	 * @param to, from, body, subject
	 * 
	 * @throws Exception
	 * 
	 * This method will send the mail to the specified address
	 */
	public void sendEmail(String to, String from, String body, String subject) throws Exception {
		
		logger.info("Sending email....");
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			
			helper.setTo(to);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setText(body, true);
			javaMailSender.send(mimeMessage);
			
		}catch (Exception e) {
			logger.info("Email not sent to email address.."+ to, e);
			}
		logger.info("Email Successfully Sent....");
	}
	
	/**
	 * This method will send notification email to patient when he is created his account.
	 */
	public void sendPatientNotificationMail(PatientDTO patient) throws Exception {
		
		logger.info("to send notification that patient is created successfully.");
		validateSmtp();
		
		String sendTo = patient.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Your account in BookMyDoctor is created successfully";
		Context context = new Context();
		context.setVariable("patName", patient.getFirstName());
		context.setVariable("patient", patient);
		String html = springTemplateEngine.process("patient-created-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Notification sent successfully to the patient....");
	}
	
	
	
	/**
	 * This method will send updation notification Email to patient.
	 */
	public void sendPatientUpdationMail(PatientDTO patient) throws Exception {
		
		logger.info("To send notification that patient's data updated successfully.");
		validateSmtp();
		
		String sendTo = patient.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Your account in BookMyDoctor is updated successfully.";
		Context context = new Context();
		context.setVariable("patName", patient.getFirstName());
		context.setVariable("patient", patient);
		String html = springTemplateEngine.process("patient-updated-notification", context);
				
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Notification sent successfully to the patient....");
	}
	
	
	
	/**
	 * @param patient
	 * @param doctorName
	 * @throws Exception
	 * 
	 * This method will send notification email to doctor when he is created his account.
	 * 
	 */
	public void sendDoctorNotificationMail(DoctorDTO doctor) throws Exception {
		
		logger.info("to send notification that doctor is created successfully.");
		validateSmtp();
		
		String sendTo = doctor.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Your Doctor account in BookMyDoctor is created successfully";
		Context context = new Context();
		context.setVariable("docName", doctor.getName());
		context.setVariable("doctor", doctor);
		String html =springTemplateEngine.process("doctor-created-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Notification sent successfully to the doctor....");
	}
	
	
	/**
	 * @param doctor
	 * @param doctorName
	 * @throws Exception
	 * 
	 * This method will send updation notification email to doctor.
	 */
	public void sendDoctorUpdationMail(DoctorDTO doctor) throws Exception {
		
		logger.info("to send notification that doctor's data updated successfully.");
		validateSmtp();
		
		String sendTo = doctor.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Hello! Doctor your account in BookMyDoctor is updated successfully.";
		Context context = new Context();
		context.setVariable("docName", doctor.getName());
		context.setVariable("doctor", doctor);
		String html =springTemplateEngine.process("doctor-updated-notification", context);
//				"Doctor "+doctor.getName() + " your account is updated";
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Notification sent successfully to the doctor....");
	}
	
	
	/**
	 * This method will send appointmentUpdation email to patient.
	 */
	public void sendAppointmentUpdationMail(Patient patient, AppointmentDTO appointment) throws Exception {
		
		logger.info("to send update appointment notification to the patient.");
		validateSmtp();
		
		String sendTo = patient.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Your appointment is updated successfully";
		Context context = new Context();
		context.setVariable("patientName", appointment.getPatientFirstName());
		context.setVariable("patient", patient);
		context.setVariable("appointment", appointment);

		String html = springTemplateEngine.process("appointment-patientUpdate-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Appointment Updation Notification sent successfully to the patient.....");
	}
	
	
	/**
	 *  This method will send patient appointmentUpdation email to their respective doctor.
	 */
	public void sendAppointmentPatUpdateDoctorMail(Doctor doctor, AppointmentDTO appointment) throws Exception {
		
		logger.info("to send update appointment notification to the doctor.");
		validateSmtp();
		
		String sendTo = doctor.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Doctor! Your patient appointment is updated.";
		Context context = new Context();
		context.setVariable("doctorName", appointment.getDoctorName());
		context.setVariable("doctor", doctor);
		context.setVariable("appointment", appointment);
		String html = springTemplateEngine.process("appointment-patUpdate-doc-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Appointment Updation Notification sent successfully to the doctor.....");
	}
	
	
	/**
	 *  This method will send patient created appointment email to the patient. 
	 */
	public void sendPatientAppointmentMail(Patient patient, AppointmentDTO appointment) throws Exception {
		
		logger.info("to send the book appointment notification to patient.");
		validateSmtp();
		
		String sendTo = patient.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Your Appointment is created successfully";
		Context context = new Context();
		context.setVariable("patientName", patient.getFirstName());
		context.setVariable("patient", patient);
		context.setVariable("appointment", appointment);

		String html = springTemplateEngine.process("appointment-patient-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Appointment booking Notification sent successfully to the patient.....");
	}
	
	
	
	/**
	 *  This method will send the patient booked appointment to their respective doctor.
	 */
	public void sendDoctorAppointmentMail(Doctor doctor, AppointmentDTO appointment) throws Exception {
		
		logger.info("to send the booked appointment notification to doctor.");
		validateSmtp();
		
		String sendTo = doctor.getEmail();
		String sendFrom = "vishalofficial4554@gmail.com";
		String subject = "Doctor! Here is your new Appointment.";
		Context context = new Context();
		context.setVariable("doctorName", doctor.getName());
		context.setVariable("doctor", doctor);
		context.setVariable("appointment", appointment);
		String html = springTemplateEngine.process("appointment-doctor-notification", context);
		
		sendEmail(sendTo, sendFrom, html, subject);
		logger.info("Appointment booked notification sent successfully to the doctor.....");
	}
	
	
	/**
	 * @throws Exception
	 * This method validate the SMTP configuration
	 */
	private void validateSmtp() throws Exception {
		
		if (StringUtils.isBlank(propConfig.getSmtpUserName()) || StringUtils.isBlank(propConfig.getSmtpPassword())
				|| StringUtils.isBlank(propConfig.getSmtpHost()) || StringUtils.isBlank(propConfig.getSmtpPort())) 
		{
			logger.error("SMTP credential is missing.");
			throw new Exception("Error while sending mail.");
		}

	}
	

}
