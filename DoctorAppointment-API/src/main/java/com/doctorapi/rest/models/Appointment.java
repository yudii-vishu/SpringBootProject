package com.doctorapi.rest.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.doctorapi.rest.Enum.Action;
import com.doctorapi.rest.dto.AppointmentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "reason")
	private String reason;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Column(name = "appointment_date")
	private LocalDate appointmnetDate;
	
	@NotNull
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Action action;
	
	@NotNull
	@OneToOne
	private Patient patient;

	@NotNull
	@ManyToOne
	private Doctor doctor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDate getAppointmnetDate() {
		return appointmnetDate;
	}

	public void setAppointmnetDate(LocalDate appointmnetDate) {
		this.appointmnetDate = appointmnetDate;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Appointment(AppointmentDTO appointmentDTO) throws Exception {
		super();

		this.reason = appointmentDTO.getReason();
		this.appointmnetDate = appointmentDTO.getAppointmnetDate();
		
		Patient p=new Patient();
		p.setId(appointmentDTO.getPatientId());
		
		Doctor d=new Doctor();
		d.setId(appointmentDTO.getDoctorId());
		
		this.patient=p;
		this.doctor=d;
		
		this.action = Action.getEnum(appointmentDTO.getAction());
		System.out.println(appointmentDTO.getAction());
	}

	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", reason=" + reason + ", appointmnetDate=" + appointmnetDate + ", action="
				+ action + ", patient=" + patient + ", doctor=" + doctor + "]";
	}


}
