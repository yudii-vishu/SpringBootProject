package com.doctorapi.rest.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.doctorapi.rest.Enum.Gender;
import com.doctorapi.rest.Enum.Status;
import com.doctorapi.rest.dto.DoctorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Entity(name="doctor")
@Table(name = "doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@NotNull
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(name = "modified_by")
	private String modifiedBy  ;
	
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn = LocalDateTime.now();
	
	@NotNull
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "password")
	private String password;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Column(name = "date")
	private LocalDate date;

	@NotNull
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
	private List<Appointment> appointments;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	
	@PreUpdate
	public void setLastUpdate() {
		this.modifiedOn = LocalDateTime.now();
	}
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public Doctor(DoctorDTO doctorDTO) throws Exception {
		
		User u = new User();
		this.name = doctorDTO.getName();
		this.gender = Gender.getEnum(doctorDTO.getGender());  //It will check string value is present otherwise throw Exception
		this.email = doctorDTO.getEmail();
		this.password = doctorDTO.getPassword();
		this.date = doctorDTO.getDate();
		this.status = Status.getEnum(doctorDTO.getStatus());
		
		u.setId(doctorDTO.getUserId());
		Role r = new Role();
		r.setId(doctorDTO.getRoleId());
		u.setRole(r);
		this.user=u;
		
	}

	public Doctor() {
		
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", gender=" + gender + ", isActive=" + isActive + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
				+ ", email=" + email + ", password=" + password + ", date=" + date + ", status=" + status
				+ ", appointments=" + appointments + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(appointments, createdBy, createdOn, date, email, gender, id, isActive, modifiedBy,
				modifiedOn, name, password, status, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(appointments, other.appointments) && Objects.equals(createdBy, other.createdBy)
				&& Objects.equals(createdOn, other.createdOn) && Objects.equals(date, other.date)
				&& Objects.equals(email, other.email) && gender == other.gender && Objects.equals(id, other.id)
				&& isActive == other.isActive && Objects.equals(modifiedBy, other.modifiedBy)
				&& Objects.equals(modifiedOn, other.modifiedOn) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && status == other.status
				&& Objects.equals(user, other.user);
	}
	
	
	
}
