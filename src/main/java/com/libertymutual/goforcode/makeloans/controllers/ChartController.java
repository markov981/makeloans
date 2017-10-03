package com.libertymutual.goforcode.makeloans.controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;

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
	  
	  
	@GetMapping("lines")
	public String drawLineChart(Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		Chart chart = new Chart("FFF");	
		double shift = 1.1;
		model.addAttribute("shift", shift);
		
		JFreeChart lineChart = ChartFactory.createLineChart(	
		        "Loan Originations by Product Group", 			
		        "2017", 							
		        "FICO (daily averages)", 			
		        chart.createDataset(550, shift, 10),
		        PlotOrientation.VERTICAL,
		        true, true, false);
		
	    chart.formatLineChart(lineChart);
	
		
		
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

	
	
	
	@PostMapping("lines")
	public String displayInput(Model model, HttpServletResponse response, HttpServletRequest request, double shift) throws IOException {
		
		model.addAttribute("noUser", false);
		model.addAttribute("shift", shift);
		
		Chart chart = new Chart("FFF");	
		
		JFreeChart lineChart = ChartFactory.createLineChart(	
		        "Loan Originations by Product Group", 			
		        "2017", 							
		        "FICO (daily averages)", 			
		        chart.createDataset(550, shift, 10),
		        PlotOrientation.VERTICAL,
		        true, true, false);
		
	    chart.formatLineChart(lineChart);
	
		System.out.println("NNNNNNNNN");
		
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
}