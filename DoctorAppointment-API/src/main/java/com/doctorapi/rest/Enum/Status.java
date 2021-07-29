package com.doctorapi.rest.Enum;

public enum Status {

	Available("available"), Notavailable("Not available");

	private String Status;


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	private Status(String status) {
		Status = status;
	}
	
	public static Status getEnum(String value) throws Exception {
		for (Status s : values()) {
			if (s.getStatus().equalsIgnoreCase(value)) {
				return s;
			}
		}
		throw new Exception("Please enter valid status(Available/Not available).");
	}
}
