package com.doctorapi.rest.Enum;

public enum Action {
	
	BOOK("book"), CANCEL("cancel");
	
	private String action;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	private Action(String action) {
		this.action = action;
	}
	
	public static Action getEnum(String value) throws Exception {
		for (Action a : values()) {
			if (a.getAction().equalsIgnoreCase(value)) {
				return a;
			}
		}
		throw new Exception("Please enter valid action type ('book': For your appointment booking).");
	}
}
