package com.doctorapi.rest.dto;

import java.util.Date;
import java.util.Objects;

import com.doctorapi.rest.models.Patient;
import com.doctorapi.rest.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PatientDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String address;
	private String email;
	private String password;
	private Long userId;
	private boolean isActive;
	@JsonIgnore
	private Long roleId;
	private Date createdOn;
	private Date modifiedOn;
	
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	
	public PatientDTO(Patient patient) {
		super();
		this.id = patient.getId();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.age = patient.getAge();
		this.gender = patient.getGender().getGender();
		this.address = patient.getAddress();
		this.email = patient.getEmail();
		this.password = patient.getPassword();
		this.userId = patient.getUser().getId();
		this.isActive = patient.getUser().isActive();
		this.roleId = patient.getUser().getRole().getId();
		
		this.createdOn = patient.getCreatedOn();
		this.modifiedOn = patient.getModifiedOn();
	}
	
	public PatientDTO() {

	}
	@Override
	public int hashCode() {
		return Objects.hash(address, age, createdOn, email, firstName, gender, id, isActive, lastName, modifiedOn,
				password, roleId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientDTO other = (PatientDTO) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(createdOn, other.createdOn)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id) && isActive == other.isActive
				&& Objects.equals(lastName, other.lastName) && Objects.equals(modifiedOn, other.modifiedOn)
				&& Objects.equals(password, other.password) && Objects.equals(roleId, other.roleId)
				&& Objects.equals(userId, other.userId);
	}
	
	@Override
	public String toString() {
		return "PatientDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", gender=" + gender + ", address=" + address + ", email=" + email + ", password=" + password
				+ ", userId=" + userId + ", isActive=" + isActive + ", roleId=" + roleId + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + "]";
	}
	
}
