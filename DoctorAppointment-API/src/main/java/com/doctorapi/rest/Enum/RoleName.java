package com.doctorapi.rest.Enum;

public enum RoleName {

	PATIENT("Patient"), DOCTOR("Doctor");
	
	private String roleName;

	public String getRoleNameString() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private RoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public static boolean getEnum(String value) throws Exception {
		for (RoleName r : values()) {
			if (r.getRoleNameString().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
}
