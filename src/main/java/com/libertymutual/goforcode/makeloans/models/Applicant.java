package com.libertymutual.goforcode.makeloans.models;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;


@Entity
public class Applicant {
	
	@Id	
	private int id;  
	
	@NotNull
	@Column(length=50, nullable=true)
	private String firstname; 
	
	@Column(length=50, nullable=true)
	private String lastname; 
	
	@Column(length=50, nullable=true)
	private String address; 
	
	@Column(length=50, nullable=false)
	@Email
	private String email;    		        
		
	@Column(length=11, nullable=false)
	private String ssn;	 
	
	@Column(nullable = false)             // ? numeric
	private Long requestedAmount;   
	
	@Column(nullable = false)             
	private Double requestedTerm;   
	
	// Interest rate, implied by Applicant self-selected credit standing
	// Is used in Loan Calculator cals only
	@Column(nullable = false)             
	private Double possibleRate;   
	
	
	
	public Applicant() {}
	
	// RandomNumberGenerator generator,
	public Applicant(String firstname, String lastname, String address, String email, String ssn, int n, 
			         Long requestedAmount, double requestedTerm, double possibleRate) {

		this.id 			 = setIdRandomly(n);         
		this.firstname 		 = firstname;
		this.lastname 		 = lastname;
		this.address 		 = address;
		this.email 			 = email;
		this.ssn 			 = ssn;
		this.requestedAmount = requestedAmount;
		this.requestedTerm   = requestedTerm;
		this.possibleRate    = possibleRate;
	}

	
		@OneToOne
		// (mappedBy = "creditRecord")  // redundant on both sides because Applicant & CreditRecord share the same PK values?
	    CreditRecord creditRecord;
	
		
	
	/* Validate SSN --------------
		^\\d{3}		3 digits
		[- ]?: 		1 optional “-“
		\\d{2}: 	2 digits
		[- ]?: 		1 optional “-“
		\\d{4}: 	4 digits              */
	public boolean isSSNValid(String ssn) {

	    boolean isValid = false;
	    
	    String expression 		= "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
	    CharSequence inputStr 	= ssn;  							// An 'abstract' String?
	    Pattern pattern 		= Pattern.compile(expression);
	    Matcher matcher 		= pattern.matcher(inputStr);
	    
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	

	/* Validate Email -------------- 
	 * */
	public static boolean isValidEmailAddress(String email) {
		
	    boolean isValid = false;
	    
	    String expression 		= "^.+@.+\\..+$";
	    CharSequence inputStr 	= email;  							

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
//	public static boolean isValidEmailAddress(String email) {
//		   boolean result = true;
//		   try {
//		      InternetAddress emailAddr = new InternetAddress(email);
//		      emailAddr.validate();
//		   } catch (AddressException ex) {
//		      result = false;
//		   }
//		   return result;
//		}	
	
	
	
	
    /* 	
    Applicant is assigned ID at random
	ID range: [1, N], where N = # of Credit Records
	--> Applicant is randomly matched to a credit record (seed)
	Distinct Applicants can get the same ID (and credit profile) 	   */ 
	
	// The value of 'N' is passed from where? 
	// RandomNumberGenerator generator, 
	public int setIdRandomly(int n){
	  	return new Random().nextInt(n) + 1; 
	 }
	

//	// The value of 'N' is passed from where? 
//	public int setIdRandomly(RandomNumberGenerator generator, int n){
//		
//		long randomId = 1 + generator.nextRandomNumber();
//		
//	  	return randomId; 
//	 }
	
	public void setId(int id) {
		this.id = id;
	}		
	public int getId() {
		return id;
	}
			
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	
	public Long getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(Long requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	
	public CreditRecord getCreditRecord() {
		return creditRecord;
	}
	public void setCreditRecord(CreditRecord creditRecord) {
		this.creditRecord = creditRecord;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Double getRequestedTerm() {
		return requestedTerm;
	}
	public void setRequestedTerm(Double requestedTerm) {
		this.requestedTerm = requestedTerm;
	}

	public Double getPossibleRate() {
		return possibleRate;
	}

	public void setPossibleRate(Double possibleRate) {
		this.possibleRate = possibleRate;
	}
	
}	
	    
