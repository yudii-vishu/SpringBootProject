package com.doctorapi.rest.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropConfig {
	
	@Value("${spring.mail.port}")
	private String smtpPort;

	@Value("${spring.mail.host}")
	private String smtpHost;

	@Value("${spring.mail.username}")
	private String smtpUserName;

	@Value("${spring.mail.password}")
	private String smtpPassword;

	public PropConfig() {
		super();
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	
	

}
