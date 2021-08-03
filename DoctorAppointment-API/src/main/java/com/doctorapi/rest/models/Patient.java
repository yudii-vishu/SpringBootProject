package com.doctorapi.rest.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.doctorapi.rest.Enum.Gender;
import com.doctorapi.rest.dto.PatientDTO;

@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Min(value=1)
	@Column(name = "age")
	private int age;
	
	@NotNull
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn = new Date(System.currentTimeMillis()) ;
	
	@Column(name = "modified_by")
	private String modifiedBy  ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn = new Date();
	
	@NotNull
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@NotNull
	@Column(name = "address")
	private String address;
	
	@NotNull
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "password")
	private String password;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	
	@PreUpdate
	public void setLastUpdate() {
		this.modifiedOn = new Date();
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public Patient(PatientDTO patientDTO) throws Exception {
		
		User u = new User();

		this.firstName = patientDTO.getFirstName();
		this.lastName = patientDTO.getLastName();
		this.age = patientDTO.getAge();
		this.gender = Gender.getEnum(patientDTO.getGender());	//It will check string value is present otherwise throw Exception
		this.address = patientDTO.getAddress();
		this.email = patientDTO.getEmail();
		this.password = patientDTO.getPassword();

		u.setId(patientDTO.getUserId());
		
		Role r = new Role();
		r.setId(patientDTO.getRoleId());
		u.setRole(r);
		this.user=u;
		
		
	}

	public Patient() {
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", isActive=" + isActive + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", modifiedBy="
				+ modifiedBy + ", modifiedOn=" + modifiedOn + ", gender=" + gender + ", address=" + address + ", email="
				+ email + ", password=" + password + ", user=" + user + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(address, age, createdBy, createdOn, email, firstName, gender, id, isActive, lastName,
				modifiedBy, modifiedOn, password, user);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(createdBy, other.createdBy)
				&& Objects.equals(createdOn, other.createdOn) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && gender == other.gender && Objects.equals(id, other.id)
				&& isActive == other.isActive && Objects.equals(lastName, other.lastName)
				&& Objects.equals(modifiedBy, other.modifiedBy) && Objects.equals(modifiedOn, other.modifiedOn)
				&& Objects.equals(password, other.password) && Objects.equals(user, other.user);
	}
	
	

}
