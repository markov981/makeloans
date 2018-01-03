package com.libertymutual.goforcode.makeloans.services;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.makeloans.models.CreditRecord;

//Define time Series forecasting methods

@Service
public class TimeSeries {

	// 1.A. Moving Average forecast
		// The method adds h-step MA forecast to the historic Time Series
		// Historic part = ArrayList<Integer> 
		// Window size (average over):	     3, 6 months, etc.
		// SubsetSize (forecast length):  	 3-month, 12-month
	public int[] createMAForecast(ArrayList<Integer> series, int subsetSize, int forecastLength) {

		// Load the 'historic' data part (is provided as input - based on simulation)
		int[] seriesMA = new int[series.size() + forecastLength];
		for (int i = 0; i < series.size(); i++) {
			 seriesMA[i] = series.get(i);
		}

		// Add the last n ( = forecastLength) averaged points to the series
		for (int i = series.size() - subsetSize; i < series.size() + forecastLength - subsetSize; i++) {
			 seriesMA[i + subsetSize] = getSingleMAPoint(seriesMA, subsetSize, i);
		}	
		return seriesMA;
	}

	
	// 1.B. Weighted Moving Average forecast
		// The method adds h-step weighted MA forecast to the historic Time Series
		// Historic part = ArrayList<Integer> 
		// Window size (subsetSize):	     average over 3, 6 months, etc.
		// Forecast length (steps ahead):  	 3-month, 6-month
	    // Weights add up to 1
	public int[] createWeightedMAForecast(ArrayList<Integer> series, int subsetSize, int forecastLength, double [] weights) {

		// Load the 'historic' data part (based on simulation)
		int[] seriesMA = new int[series.size() + forecastLength];
		for (int i = 0; i < series.size(); i++) {
			 seriesMA[i] = series.get(i);
		}
		
		// Write the last n ( = forecastLength) as weighted/averaged points to historic series
		// The 'weights' array = user-select weight   <>  The 'weightsGen' array = user-select weight or 0
		double [] weightsGen = new double[seriesMA.length];
		System.out.println("Long Array of weights length - 1: " + weightsGen.length);
		weightsGen[seriesMA.length - forecastLength - 1] = weights[0]; 
		weightsGen[seriesMA.length - forecastLength - 2] = weights[1]; 
		weightsGen[seriesMA.length - forecastLength - 3] = weights[2]; 
		weightsGen[seriesMA.length - forecastLength - 4] = weights[3]; 
		System.out.println("Long Array of weights length - 2: " + weightsGen.length);
	
		for (int i = 0; i < weightsGen.length; i++) {
			 if (weightsGen[i] <= 0.000000001d) weightsGen[i] = 0.0d;
		}
			System.out.println("Long Array of weights: " + Arrays.toString(weightsGen));
			System.out.println("Historic Series: "       + Arrays.toString(seriesMA));	
			
		// Define subsetSize = number of historic points with non-zero weights 
		int subsetSizeA  = getNumberOfWeights(weights);
		int weightsIndex = seriesMA.length - forecastLength - 4;
		
		System.out.println("Historic Series: " + seriesMA.length + " Long Array of weights: " + weightsGen.length + 
				           " Subset size: "     + subsetSize    + " Non-zero: "    + subsetSizeA);		
		
		for (int i = series.size() - subsetSize; i < series.size() + forecastLength - subsetSize; i++) {
			 seriesMA[i + subsetSize] = getSingleWeightedMAPoint(seriesMA, subsetSizeA, i, weightsGen, weightsIndex);
		}
		System.out.println(Arrays.toString(series.toArray()));	
	
				
		return seriesMA;
	}
	
	
	// Get a single averaged data point for weighted MA TimeSeries
	private int getSingleWeightedMAPoint(int[] seriesMA, int numberOfWeights, int startingPoint, double [] weightsGen, int weightsIndex) {

		//System.out.println(Arrays.toString(seriesMA));	
		//System.out.println(Arrays.toString(weightsGen));
		System.out.println("Weight Index: " + weightsIndex); 
		
		int temp = 0;
		int j = 0;
		for (int i = startingPoint - 1; i < startingPoint + numberOfWeights - 1; i++) {	
			
			System.out.println("Index: " + i + "  Weight Series: " + weightsGen[weightsIndex + j]  + " Series MA: " + seriesMA[i]);	
			
			temp += weightsGen[weightsIndex + j] * seriesMA[i];
			j++;
			System.out.println("New Point: " + temp);
		}
		return temp;
	}
	
	
	// Get a single averaged data point for non-weighted MA TimeSeries
	private int getSingleMAPoint(int[] seriesMA, int subsetSize, int startingPoint) {
		
		int temp = 0;

		for (int i = startingPoint; i < startingPoint + subsetSize; i++) {
			temp += seriesMA[i];
		}
		return temp / subsetSize;
	}
	
	// Define subsetSize = number of historic points with user-selected non-zero weights 
	private int getNumberOfWeights(double [] weights) {
		
		int n = 0;
		for(int i = 0; i < weights.length; i++)
			if (weights[i] > 0.0d) n++;
		return n;
	};
}
