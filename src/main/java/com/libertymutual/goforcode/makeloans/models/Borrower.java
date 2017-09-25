package com.libertymutual.goforcode.makeloans.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Borrower {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int id; 
		
	// Values of "FICO”, “age of credit history”, “utilization”
	// ???????????????
	//private double[] ValueOfVariables;
	
	// Same size as modelVariables array
	private static final double[] modelCoefficients = {1.1, 2.3, -0.7, -1.2, -1.9};
	
	private int score;
	
	public int setCreditScore(CreditRecord creditRecord) {
	
		Double temp = modelCoefficients[0] * creditRecord.getFico() +
					  modelCoefficients[1] * creditRecord.getCreditFileAge() +
					  modelCoefficients[2] * creditRecord.getCreditCardUtilization() +
					  modelCoefficients[3] * creditRecord.getInquiriesLast12Months() +
					  modelCoefficients[4] * creditRecord.getNumberOfLatePayments();
		
		score = temp.intValue();
		
	return score; 
}
	
//	public int setCreditScore() {		
//		for(int i = 0; i < modelCoefficients.length; i++) {			
//			score += ValueOfVariables[i] * modelCoefficients[i];   // intercept?
//		}			
//		return score; 
//	}

		


}
