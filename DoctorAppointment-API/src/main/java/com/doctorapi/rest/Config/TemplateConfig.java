package com.doctorapi.rest.Config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateConfig {
	
	@Bean
	public SpringTemplateEngine springTemplateEngine()
	{
		SpringTemplateEngine templateEngine = new  SpringTemplateEngine();
		templateEngine.addTemplateResolver(emailTemplateResolver());
		return templateEngine;
	}
	
	@Bean
	ClassLoaderTemplateResolver emailTemplateResolver()
	{
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return templateResolver;
	}

}