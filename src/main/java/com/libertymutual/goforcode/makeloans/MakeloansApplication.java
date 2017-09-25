package com.libertymutual.goforcode.makeloans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

import com.libertymutual.goforcode.makeloans.models.Applicant;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MakeloansApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakeloansApplication.class, args);
		
//		Applicant applicant = new Applicant("alex", "alex", "111 ", "554-55-555", 10);
//		
//		
//		System.out.println("My radom ID is: " 		+ applicant.getRandomId());
//		System.out.println("My auto-generated ID: " + applicant.getId());	
//		
//		System.out.println("My SSN is: " 			+ applicant.getSnn());
//		System.out.println("My SSN is valid: " 	    + applicant.isSSNValid(applicant.getSnn()));
		
	}
}
