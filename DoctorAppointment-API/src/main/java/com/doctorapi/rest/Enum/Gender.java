package com.doctorapi.rest.Enum;

public enum Gender {
	
	MALE("Male"), FEMALE("Female"), TRANSGENDER("Transgender");
	
	private String gender;

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

	private Gender(String gender) {
		this.gender = gender;
	}
	
	
	public static Gender getEnum(String value) throws Exception {
		for (Gender ge : values()) {
			if (ge.getGender().equalsIgnoreCase(value)) {
				return ge;
			}
		}
		throw new Exception("Invalid Gender type. Please enter valid gender.");
	}
}
