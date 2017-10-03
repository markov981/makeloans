package com.libertymutual.goforcode.makeloans.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	private Applicant     applicant; 
	// private PasswordEncoder encoder;
	
	
	// add: PasswordEncoder encoder as parameter
	public HomeController(CreditRecordRepository creditRecordRepository) {
		this.creditRecordRepository = creditRecordRepository;
	}

	@GetMapping("")
	public String getInput(Model model) {
		model.addAttribute("firstname", "");
		model.addAttribute("lastname",  "");
		model.addAttribute("address",  "");
		model.addAttribute("email",  "");
		model.addAttribute("ssn",  "");
		model.addAttribute("requestedAmount", "1000");
		
		model.addAttribute("noUser", true);
		model.addAttribute("User",   false);
		model.addAttribute("errorSSN", false);
		model.addAttribute("errorEmail", false);
		
		return "home/input";
	}

	
	@PostMapping("/input")
	public String displayInput(Model model, Applicant applicant, BindingResult result) {
		
		// +generator
//		Applicant applicant = new Applicant(first_name, last_name, address, email, ssn, 10, requested_amount);
		
		model.addAttribute("noUser", false);
		model.addAttribute("User",   true);
		model.addAttribute("errorSSN", false);
		model.addAttribute("errorEmail", false);
				
		model.addAttribute("firstname", applicant.getFirstname());
		model.addAttribute("lastname",  applicant.getLastname());
		model.addAttribute("address",  	applicant.getAddress());
		
		model.addAttribute("email", applicant.getEmail());
					System.out.println("email:  " + applicant.getEmail()); 
					System.out.println("error?:  " + result.hasErrors()); 
		if (result.hasErrors()) {
			model.addAttribute("errorEmail", "Please enter a valid email.");
			model.addAttribute("noUser", true);
			model.addAttribute("User",   false);
		}
		
		
		model.addAttribute("ssn", applicant.getSsn());	
		if (!applicant.isSSNValid(applicant.getSsn())) {
			model.addAttribute("errorSSN", "Please enter a valid Social Security Number (xxx-xx-xxxx).");
			model.addAttribute("noUser", true);
			model.addAttribute("User",   false);
		}
	
		
			
		model.addAttribute("requestedAmount", Long.toString(applicant.getRequestedAmount()));
			
		model.addAttribute("monthly_payment", 100);   // ?????
		model.addAttribute("rate", 17);               // ?????
		
		return "/home/input";
	}
			
}
















