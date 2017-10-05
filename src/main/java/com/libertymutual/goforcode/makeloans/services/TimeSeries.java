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

	// Moving Average: define window length: 3, 6 months, etc.
	// The method re-states the whole time series as a MA, adding a m-step forecast
	// subsetSize = width of the rolling window, e.g. 3-month, 6-month, etc. moving
	// average
	public int[] createMAForecast(ArrayList<Integer> series, int subsetSize, int forecastLength) {

		// The 'historic' data part is the same for both TSeries.
		int[] seriesMA = new int[series.size() + forecastLength];
		for (int i = 0; i < series.size(); i++) {
			seriesMA[i] = series.get(i);
		}

		// Fill out the last n ( = forecastLength) points in the TSeries with averaged
		// data
		for (int i = series.size() - subsetSize; i < series.size() + forecastLength - subsetSize; i++) {
			seriesMA[i + subsetSize] = getSingleMAPoint(seriesMA, subsetSize, i);
		}

		// The base (historic) series will have nulls for these points
		// for(int i = series.size() - subsetSize; i < series.size() + forecastLength -
		// subsetSize; i++) {
		// series.add(i, null);
		// }

		return seriesMA;
	}

	
	
	// Get a single average value (point) for a specified ts subset = average value
	// for MA(3) over 3 months
	private int getSingleMAPoint(int[] seriesMA, int subsetSize, int startingPoint) {

		int temp = 0;

		for (int i = startingPoint; i < startingPoint + subsetSize; i++) {
			temp += seriesMA[i];
		}
		return temp / subsetSize;
	}

}
