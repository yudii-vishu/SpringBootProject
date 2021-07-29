package com.doctorapi.restutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *  This method provides validations
 *  
 * @author Vishal
 *
 */
public class CommonUtils {
	
	public  static boolean validateEmail(String email) {
		Pattern emailPattern = Pattern
				.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
		Matcher matcher = emailPattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean validatePassword(String password) {
		Pattern passwordPattern = Pattern
				.compile("^(?!.*\\s)(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[$@$!%*#?&])(?=.*?[0-9]).{8,}$");
		Matcher matcher = passwordPattern.matcher(password);
		return matcher.matches();
	}

}
