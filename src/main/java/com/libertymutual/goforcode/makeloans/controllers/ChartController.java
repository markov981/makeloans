package com.libertymutual.goforcode.makeloans.controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.goforcode.makeloans.models.Applicant;
import com.libertymutual.goforcode.makeloans.models.Chart;
import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


@Controller
@RequestMapping( {"/reports"} )
public class ChartController {
   
	
	@GetMapping("images")
	public void getFile(String filename, HttpServletResponse response) {
		try {
			ServletUtilities.sendTempFile(filename, response);
		} catch (IOException ioe) {
			response.setStatus(404);
		}
	}
	  
	
	// Front page for Analyst
	@GetMapping("")
	public String getAnalystTools(Model model) {
		
		model.addAttribute("fc_length", 6);         // MA forecast length
		model.addAttribute("window_length", 3);		// MA window length
		model.addAttribute("weight1", 0.6);		    // MA weighted: the most recent historic data
		model.addAttribute("weight2", 0.2);		    // MA weighted: historic data at lag 1 
		model.addAttribute("weight3", 0.15);		// MA weighted: historic data at lag 2
		model.addAttribute("weight4", 0.05);		// MA weighted: historic data at lag 3
		return "home/analyst";
	}
	
	
	@GetMapping("lines")
	public String drawLineChart(Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		Chart chart = new Chart("FFF");	
		double shift = 1.1;
		model.addAttribute("shift", shift);
		
		JFreeChart lineChart = ChartFactory.createLineChart(	
		        "Loan Originations by Product Group", 			
		        "2017", 							
		        "FICO (daily averages)", 			
		        chart.createDataset(550, shift, 14),
		        PlotOrientation.VERTICAL,
		        true, true, false);
		
	    chart.formatLineChart(lineChart, 550, 1.1, 14, 0);
	    String filename = ChartController.setConnection(lineChart, response, request, 900, 600);  // XXXXXXXXX XXXXXXXXXXXXXX
			
//		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
//		HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
//		
//		String file = "";
//		file = ServletUtilities.saveChartAsPNG(lineChart, 900, 600, info, session);
//		response.addHeader("Content-Type", "text/html"); // added
//		ChartUtilities.writeImageMap(out, "imgMap", info, false);
//		out.flush();
//		
//		String filename = request.getContextPath() + "images?filename=" + file;	
	    
	    
		model.addAttribute("line_chart", filename);
		return "reports/linechart"; 				
	}
	
	@PostMapping("lines")
	public String displayInput(Model model, HttpServletResponse response, HttpServletRequest request, double shift) throws IOException {
		
		model.addAttribute("noUser", false);
		model.addAttribute("shift", shift);
		
		Chart chart = new Chart("FFF");	
		
		JFreeChart lineChart = ChartFactory.createLineChart(	
		    "Loan Originations by Product Group", "2017", "FICO (daily averages)", 			
		    chart.createDataset(550, shift, 14),
		    PlotOrientation.VERTICAL, true, true, false);
		
	    chart.formatLineChart(lineChart, 550, shift, 14, 0);
		
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String file = "";
		file = ServletUtilities.saveChartAsPNG(lineChart, 900, 600, info, session);
		response.addHeader("Content-Type", "text/html"); // added
		ChartUtilities.writeImageMap(out, "imgMap", info, false);
		out.flush();
		
		String filename = request.getContextPath() + "images?filename=" + file;		
		model.addAttribute("line_chart", filename);

		
		return "reports/linechart";
	}
	
	


	
	
//
//	@GetMapping("forecastma")
//	public String drawLineChartMA(Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
//		
//		Chart chart = new Chart("FFF");			
//		int forecastPeriod = 3;  // AKAK
//		int forecastLength = 10;  // AKAK
//		int sampleSize = 20;
//		
//		JFreeChart lineChart = ChartFactory.createLineChart(	
//		        "Forecasting: Moving Average", 			
//		        "2017", 							
//		        "FICO (daily averages)", 			
//		     //   chart.createMAForecastingData(550, sampleSize, forecastPeriod, forecastLength),
//		        chart.createMAForecastingData(550, sampleSize, forecastPeriod, forecastLength),
//		        PlotOrientation.VERTICAL,
//		        true, true, false);
//		
//		chart.formatLineChartBase(lineChart, sampleSize, forecastLength);
//			
//		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
//		HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
//		
//		String file = "";
//		file = ServletUtilities.saveChartAsPNG(lineChart, 900, 600, info, session);
//		response.addHeader("Content-Type", "text/html"); // added
//		ChartUtilities.writeImageMap(out, "imgMap", info, false);
//		out.flush();
//		
//		String filename = request.getContextPath() + "images?filename=" + file;		
//		model.addAttribute("forecastma", filename);
//		return "reports/forecastma"; 				
//	}

	

	    // Plot un-weighted Moving Average forecast
		@PostMapping("forecastma")
		public String drawLineChartMA_P
		       (Model model, HttpServletResponse response, HttpServletRequest request, 
		        int fc_length, int window_length) throws IOException {
			
			Chart chart = new Chart("FFF");			
			int sampleSize = 20;
			
			JFreeChart lineChart = ChartFactory.createLineChart(	
			        "Forecasting: Moving Average", "2017", "Credit Card Losses ('000$)", 			
			        chart.createMAForecastingData(550, sampleSize, window_length, fc_length),
			        PlotOrientation.VERTICAL,
			        true, true, false);
			
			chart.formatLineChartBase(lineChart, sampleSize, fc_length);
				
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			
			String file = "";
			file = ServletUtilities.saveChartAsPNG(lineChart, 900, 600, info, session);
			response.addHeader("Content-Type", "text/html"); // added
			ChartUtilities.writeImageMap(out, "imgMap", info, false);
			out.flush();
			
			String filename = request.getContextPath() + "images?filename=" + file;		
			model.addAttribute("forecastma", filename);
			return "reports/forecastma"; 				
		}



			
			// Plot weighted Moving Average forecast
			@PostMapping("forecastma_we")
			public String drawLineChartWeightedMA(Model model, HttpServletResponse response, HttpServletRequest request, 
					                              int fc_length, int window_length,
					                              double weight1, double weight2, double weight3, double weight4) 
					                              throws IOException {				
				Chart chart = new Chart("FFF");			
				int sampleSize = 20;
				double [] weights = {weight1, weight2, weight3, weight4};
				
				JFreeChart lineChart = ChartFactory.createLineChart(	
				        "Forecasting: Weighted Moving Average", "2017", "FICO (daily averages)", 			
				        chart.createMAWhtForecastingData(550, sampleSize, window_length, fc_length, weights),
				        PlotOrientation.VERTICAL, true, true, false);
				
				chart.formatLineChartBase(lineChart, sampleSize, window_length);
					
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				HttpSession session 	= request.getSession();
				PrintWriter out 		= response.getWriter();
				
				String file = "";
				file = ServletUtilities.saveChartAsPNG(lineChart, 900, 600, info, session);
				response.addHeader("Content-Type", "text/html"); // added
				ChartUtilities.writeImageMap(out, "imgMap", info, false);
				out.flush();
				
				String filename = request.getContextPath() + "images?filename=" + file;		
				model.addAttribute("forecastma", filename);
				return "reports/forecastma"; 				
			}

			
			
			private static String setConnection(JFreeChart chart, HttpServletResponse response, 
					                            HttpServletRequest request, int width, int height) throws IOException {
				
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				HttpSession session = request.getSession();
				PrintWriter out 	= response.getWriter();
				
				String file = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
				response.addHeader("Content-Type", "text/html"); 
				ChartUtilities.writeImageMap(out, "imgMap", info, false);
				out.flush();
				
				String filename = request.getContextPath() + "images?filename=" + file;	
				return filename;			
			}			
			
}
