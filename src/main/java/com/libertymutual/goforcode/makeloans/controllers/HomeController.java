package com.libertymutual.goforcode.makeloans.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.libertymutual.goforcode.makeloans.models.Applicant;
import com.libertymutual.goforcode.makeloans.repositories.CreditRecordRepository;
import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;


@Controller
@RequestMapping("/")
public class HomeController {
	
	private CreditRecordRepository creditRecordRepository;
	RandomNumberGenerator generator;
	private Applicant applicant; 
	// private PasswordEncoder encoder;
	
	
	// add: PasswordEncoder encoder as parameter
	public HomeController(CreditRecordRepository creditRecordRepository) {
		this.creditRecordRepository = creditRecordRepository;
	}

	@GetMapping("")
	public String getInput(Model model) {
		model.addAttribute("first_name", "");
		model.addAttribute("last_name",  "");
		model.addAttribute("address",  "");
		model.addAttribute("email",  "");
		model.addAttribute("ssn",  "");
		model.addAttribute("requested_amount", "1000");
		
		model.addAttribute("noUser", true);
		model.addAttribute("User",   false);
		model.addAttribute("errorSSN", false);
		
		return "home/input";
	}

	
	@PostMapping("/input")
	public String displayInput(Model model, String first_name, String last_name, String address, String email, 
			                   String ssn, Long requested_amount) {
		
		// +generator
		Applicant applicant = new Applicant(first_name, last_name, address, email, ssn, 10, requested_amount);
		
		model.addAttribute("noUser", false);
		model.addAttribute("User",   true);
		model.addAttribute("errorSSN", false);
				
		model.addAttribute("first_name", 	applicant.getFirstname());
		model.addAttribute("last_name",  	applicant.getLastname());
		model.addAttribute("address",  		applicant.getAddress());
		model.addAttribute("email",  		applicant.getEmail());
				
		model.addAttribute("ssn",  			applicant.getSnn());
		if (!applicant.isSSNValid(ssn)) {
			model.addAttribute("errorSSN", "Please enter a valid Social Security Number (xxx-xx-xxxx).");
			model.addAttribute("noUser", true);
			model.addAttribute("User",   false);
		}
			
		model.addAttribute("requested_amount", Long.toString(applicant.getRequestedAmount()));
			
		model.addAttribute("monthly_payment", 100);   // ?????
		model.addAttribute("rate", 17);               // ?????
		
		return "/home/input";
	}
			
}
















