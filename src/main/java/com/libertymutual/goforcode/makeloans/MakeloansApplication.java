package com.libertymutual.goforcode.makeloans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.data.category.DefaultCategoryDataset;
import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.libertymutual.goforcode.makeloans.models.Applicant;
import com.libertymutual.goforcode.makeloans.models.Chart;
import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MakeloansApplication {

	public static void main(String[] args) {
//		 SpringApplication.run(MakeloansApplication.class, args);
		// Substitute line above with this (to take care of the HeadlessException):
		// Headless error - Thrown when code that is dependent on a keyboard, display, or mouse 
		//                  is called in an environment that does not support a keyboard, display, or mouse.
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MakeloansApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
		
		
//		RandomNumberGenerator randomData = new RandomNumberGenerator();		
//		ArrayList<Integer> simData = randomData.simulateRandomValues(500, 80, 350, 850, 10);		
//		System.out.println("Simulated data: " + simData);		
//		System.out.println("Simulated data: " + randomData.findMean(simData));


		
//		Chart example = new Chart("FFF");
//	      example.setAlwaysOnTop(true);
//	      example.pack();
	  //  example.setSize(1900, 600);
	  //  example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//	      example.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
//	      example.setVisible(true);	
//	      
//	      
//	      example.setBackground(Color.BLUE); // ????
		
//		Applicant applicant = new Applicant("alex", "alex", "111 ", "554-55-555", 10);
//			
//		System.out.println("My radom ID is: " 		+ applicant.getRandomId());
//		System.out.println("My auto-generated ID: " + applicant.getId());	
//		
//		System.out.println("My SSN is: " 			+ applicant.getSnn());
//		System.out.println("My SSN is valid: " 	    + applicant.isSSNValid(applicant.getSnn()));
		
	}
	

}
