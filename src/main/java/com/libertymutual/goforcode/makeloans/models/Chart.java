package com.libertymutual.goforcode.makeloans.models;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;



public class Chart extends ApplicationFrame {   // JFrame

	
  private static final long serialVersionUID = 1L;
  RandomNumberGenerator simulation = new RandomNumberGenerator();	  
  
  
  public Chart(String title) {   
	super("JavaBank Analytics");
	      
	DefaultCategoryDataset dataset = createDataset(550, 1.1, 14);		
        	
    JFreeChart lineChart = ChartFactory.createLineChart(	
        "Loan Originations by Product Group", 			
        "Jan - Oct, 2017", 							
        "FICO (daily averages)", 			
        dataset,
        PlotOrientation.VERTICAL,
        true,                        // Show legend
        true, 						 // Show tool-tips
        false						 // Generate URLs
        );
  }

  
  
   // Generate dataset: time series with a structural shift
   public DefaultCategoryDataset createDataset(double mean, double shift, int sampleSize) {

		  String series = "Credit Cards";
		  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		  
		  // Before & After time series - no overlap
		  ArrayList<Integer> simulatedFICO_prior = simulation.simulateRandomValues(mean, sampleSize);
		  mean *= shift;
		  ArrayList<Integer> simulatedFICO_post  = simulation.simulateRandomValues(mean, sampleSize);

		  // Create a single time series with a structural shift (and a gap in the period when shift happens)  
		  for (int i = 0; i < simulatedFICO_prior.size(); i++) {			  
			  dataset.addValue(simulatedFICO_prior.get(i), series, new Day(i + 1, 10, 2017));		  
		  }
		  for (int i = 0; i < simulatedFICO_post.size(); i++) {
			  dataset.addValue(simulatedFICO_post.get(i),  series, new Day(i + 1 + sampleSize, 10, 2017));
		  }
		  // Create a gap (missing value) in the time series
		 dataset.setValue(null, series, new Day(1 + sampleSize, 10, 2017));
		  
		  return dataset;
	}

   
   
   
   public void formatLineChart(JFreeChart lineChart) {
	
    // Add two subTitles    
	TextTitle sourceUp = new TextTitle("The 2-sample t-test to determine if 2 population means are equal: .");   
	sourceUp.setFont(new Font("SansSerif", Font.PLAIN, 22));   
    lineChart.addSubtitle(sourceUp);
    
    TextTitle source = new TextTitle("XXXX");
    	source.setFont(new Font("SansSerif", Font.PLAIN, 24));
    	source.setPosition(RectangleEdge.BOTTOM);
    	source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    	lineChart.addSubtitle(source);
    
    	
    //Set plot specifications 
    final CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
    plot.setBackgroundPaint(new Color(0xffffe0));
    plot.setDomainGridlinesVisible(true);
    plot.setDomainGridlinePaint(Color.lightGray);
    plot.setRangeGridlinePaint(Color.lightGray);
    
    
    // Domain axis - label position (slanted)
    final CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
    domainAxis.setCategoryLabelPositions(
    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
    );   
    // Range axis - label 
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setLabelFont(new Font("Verdana", Font.BOLD, 22));
    rangeAxis.setLabelPaint(Color.DARK_GRAY);
    rangeAxis.setRange(300, 850); 
    
	  
    final ChartPanel chartPanel = new ChartPanel(lineChart);
    chartPanel.setPreferredSize( new java.awt.Dimension( 1200 , 800 ) );
    chartPanel.setDomainZoomable(true);
    chartPanel.setRangeZoomable(true);
    chartPanel.setFillZoomRectangle(true);
    chartPanel.setDisplayToolTips(true);
    
    // setContentPane(chartPanel);
    }
}


