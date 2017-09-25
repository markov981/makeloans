package com.libertymutual.goforcode.makeloans.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class CreditRecord {
	
	@Id                                               // This will not do!
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int  	id;  
	
	private int  	fico; 
	private int  	creditFileAge;
	private double  creditCardUtilization;
	private int  	inquiriesLast12Months;
	private int  	numberOfLatePayments;
	
	
	public CreditRecord() {}
	
	public CreditRecord(int id, int fico, int creditFileAge, double creditCardUtilization, int inquiriesLast12Months,
			int numberOfLatePayments) {
		this.id = id;
		this.fico = fico;
		this.creditFileAge = creditFileAge;
		this.creditCardUtilization = creditCardUtilization;
		this.inquiriesLast12Months = inquiriesLast12Months;
		this.numberOfLatePayments = numberOfLatePayments;	
	}
	
	private double[] regressors = {getId(), getFico(), getCreditFileAge(), getCreditCardUtilization(), 
			                       getInquiriesLast12Months(), getInquiriesLast12Months()};
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getFico() {
		return fico;
	}
	public void setFico(int fico) {
		this.fico = fico;
	}
	
	public int getCreditFileAge() {
		return creditFileAge;
	}
	public void setCreditFileAge(int creditFileAge) {
		this.creditFileAge = creditFileAge;
	}
	
	public double getCreditCardUtilization() {
		return creditCardUtilization;
	}
	public void setCreditCardUtilization(double creditCardUtilization) {
		this.creditCardUtilization = creditCardUtilization;
	}
	
	public int getInquiriesLast12Months() {
		return inquiriesLast12Months;
	}
	public void setInquiriesLast12Months(int inquiriesLast12Months) {
		this.inquiriesLast12Months = inquiriesLast12Months;
	}
	
	public int getNumberOfLatePayments() {
		return numberOfLatePayments;
	}
	public void setNumberOfLatePayments(int numberOfLatePayments) {
		this.numberOfLatePayments = numberOfLatePayments;
	}
}
