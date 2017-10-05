package com.libertymutual.goforcode.makeloans.services;

import java.util.ArrayList;
import java.util.Random;
import org.springframework.stereotype.Service;


@Service
public class RandomNumberGenerator {
	
	public long nextRandomNumber() {
		return Math.round(Math.random() * 1000000000);
	}
	
	
	// Simulate n random outcomes with Normal distribution & constrained tails & seed (= mean)
	public ArrayList<Integer> simulateRandomValues(double mean, int sampleSize) {
		Random random = new Random((long)mean);
		int temp;
		ArrayList<Integer> simulatedData = new ArrayList<Integer>();
		final double CV = 0.1;   // coefficient of variation --> data is not noisy 
		
		for (int i = 0; simulatedData.size() <= sampleSize; i++) {
			
			 double val = random.nextGaussian() * mean * CV  +  mean ;
			 temp = (int) Math.round(val);
			 
			 if (temp >= 300 && temp <= 850) {
				 simulatedData.add(temp); 
				 }
		}
		return simulatedData;
	}
	
	
    public int findMean(ArrayList<Integer> nums){
        double sum = 0;
        for (int i = 0; i < nums.size(); i++){
            sum += nums.get(i);}
        
        return (int) (sum / nums.size());
    } 
    
    
    public double findStd(ArrayList<Integer> nums){
        double mean = findMean(nums);
        double temp = 0; 
        
        for (double x : nums){
        	temp += (x - mean)*(x - mean);}
        
        return Math.sqrt(temp) / (nums.size() - 1);
    }   
}
