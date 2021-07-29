package com.doctorapi.rest.dto;

import java.time.LocalDate;

import com.doctorapi.rest.models.Appointment;


public class AppointmentDTO {
	
	private Long id;
	private String reason;
	private LocalDate appointmnetDate;
	private String action;
	private Long patientId;
	private String patientFirstName;
	private String patientLastName;
	private Long doctorId;
	private String doctorName;
	
	
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	public AppointmentDTO(Appointment appointment) {
		super();
		this.id = appointment.getId();
		this.reason = appointment.getReason();
		this.appointmnetDate = appointment.getAppointmnetDate();
		this.action = appointment.getAction().getAction();
		this.patientId = appointment.getPatient().getId();
		this.doctorId = appointment.getDoctor().getId();
		this.patientFirstName = appointment.getPatient().getFirstName();
		this.patientLastName = appointment.getPatient().getLastName();
		this.doctorName = appointment.getDoctor().getName();
	}
	
			
	public AppointmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((appointmnetDate == null) ? 0 : appointmnetDate.hashCode());
		result = prime * result + ((doctorId == null) ? 0 : doctorId.hashCode());
		result = prime * result + ((doctorName == null) ? 0 : doctorName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patientFirstName == null) ? 0 : patientFirstName.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((patientLastName == null) ? 0 : patientLastName.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppointmentDTO other = (AppointmentDTO) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (appointmnetDate == null) {
			if (other.appointmnetDate != null)
				return false;
		} else if (!appointmnetDate.equals(other.appointmnetDate))
			return false;
		if (doctorId == null) {
			if (other.doctorId != null)
				return false;
		} else if (!doctorId.equals(other.doctorId))
			return false;
		if (doctorName == null) {
			if (other.doctorName != null)
				return false;
		} else if (!doctorName.equals(other.doctorName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patientFirstName == null) {
			if (other.patientFirstName != null)
				return false;
		} else if (!patientFirstName.equals(other.patientFirstName))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (patientLastName == null) {
			if (other.patientLastName != null)
				return false;
		} else if (!patientLastName.equals(other.patientLastName))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AppointmentDTO [id=" + id + ", reason=" + reason + ", appointmnetDate=" + appointmnetDate + ", action="
				+ action + ", patientId=" + patientId + ", patientFirstName=" + patientFirstName + ", patientLastName="
				+ patientLastName + ", doctorId=" + doctorId + ", doctorName=" + doctorName + "]";
	}
	
}
