package com.libertymutual.goforcode.makeloans.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.libertymutual.goforcode.makeloans.models.Applicant;
import com.libertymutual.goforcode.makeloans.models.CreditRecord;
import com.libertymutual.goforcode.makeloans.repositories.CreditRecordRepository;
import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;


@Configuration
@Profile("development")
public class SeedData {
	
	// PasswordEncoder encoder <- a parameter
	public SeedData(CreditRecordRepository creditRecordRepository, RandomNumberGenerator generator) {
				
		creditRecordRepository.save(new CreditRecord(1, 850, 30, 0.01, 1, 0));
		creditRecordRepository.save(new CreditRecord(2, 800, 27, 0.05, 2, 0));
		creditRecordRepository.save(new CreditRecord(3, 750, 25, 0.1,  3, 1));
		creditRecordRepository.save(new CreditRecord(4, 700, 23, 0.15, 3, 1));
		creditRecordRepository.save(new CreditRecord(5, 650, 20, 0.2,  4, 2));

		// Applicant applicant = new Applicant("alex", "alex", "111 ", "554-55-555", generator, 10);
	}

}


















