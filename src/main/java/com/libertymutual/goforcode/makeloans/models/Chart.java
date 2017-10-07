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
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import com.libertymutual.goforcode.makeloans.services.RandomNumberGenerator;
import com.libertymutual.goforcode.makeloans.services.TimeSeries;



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

		  String series = "Product Line: Credit Cards";
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

 
   // Start with 'historic' TSeries, create 'forecasted TS' (same historic part + forecasted 'end')
   // the original series gets nulls for the 'forecasted' part 
   public DefaultCategoryDataset createMAForecastingData(double mean, int sampleSize, int subsetSize, int forecastLength) {

		  String series = "Product Line: Credit Cards";
		  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		  TimeSeries             ts      = new TimeSeries(); 
		  
		  // Historic & ForecastedTS - overlap in the 'historic' part
		  ArrayList<Integer> historicSeries = simulation.simulateRandomValues(mean, sampleSize);
		  int[] maSeries = ts.createMAForecast(historicSeries, subsetSize, forecastLength);	  
		  
		  // Create a single time series with a structural shift (and a gap in the period when shift happens)  
		  for (int i = 0; i < historicSeries.size(); i++) {			  
			  dataset.addValue(historicSeries.get(i), series, new Day(i + 1, 10, 2017));		  
		  }
		  for (int i = 0; i < maSeries.length; i++) {
			  dataset.addValue(maSeries[i],  series, new Day(i + 1, 10, 2017));
		  }
		  		  
		  // Create a gap (missing value) in the time series
		 // dataset.setValue(null, series, new Day(1 + sampleSize, 10, 2017));
		  
		  return dataset;
	}
   
   
   
   
   
   public void formatLineChart(JFreeChart lineChart, double mean, double shift, int sampleSize) {
			   
		// The t-test 
		ArrayList<Integer> prior = simulation.simulateRandomValues(mean, sampleSize);
		mean *= shift;
	    ArrayList<Integer> post  = simulation.simulateRandomValues(mean, sampleSize); 
	    
	    int meanPrior = simulation.findMean(prior);   
	    int meanPost  = simulation.findMean(post);    
		   
	    double tTest = ( meanPrior - meanPost ) / 
	    	   Math.sqrt(   Math.pow(simulation.findStd(prior), 2) / prior.size() + Math.pow(simulation.findStd(post), 2) / post.size() );
	    tTest = Math.round(tTest * 100) / 100;
	    
		   
	    // SubTitle - 1    
		TextTitle sourceTtest = new TextTitle("T-value to compare mean FICO of " + meanPrior + " to " + meanPost + " = " + tTest);   
		sourceTtest.setFont(new Font("SansSerif", Font.PLAIN, 20));
		sourceTtest.setToolTipText("Dear manager, this is a t-test. \n It has been around since 1908 and will not bite you.");
	    lineChart.addSubtitle(sourceTtest);
	    
	    // SubTitle - 2  
	    String result = "different"; if (Math.abs(tTest) < 2) {result = "the same";}
		TextTitle sourceTstat = new TextTitle("The 2 groups of applicants have FICOs that are statistically " + result + " at 5% significance level");   
		sourceTstat.setPaint(Color.blue);
		if (Math.abs(tTest) < 2) {sourceTstat.setPaint(Color.red);}
		sourceTstat.setFont(new Font("SansSerif", Font.PLAIN, 16));   
	    lineChart.addSubtitle(sourceTstat);
	        
	    formatLineChartBase(lineChart, sampleSize);
    }
   
   
   
   public void formatLineChartBase(JFreeChart lineChart, int sampleSize, int subsetSize, int forecastLength) {
	    	   
	    // Legend
	    LegendTitle legend = lineChart.getLegend();
	    legend.setPosition(RectangleEdge.BOTTOM);
	    legend.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	    legend.setItemFont(new Font("SansSerif", Font.PLAIN, 14));
	    
	    //Set plot specifications 
	    final CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
	    plot.setBackgroundPaint(new Color(0xffffe0));
	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.lightGray);
	    plot.setRangeGridlinePaint(Color.lightGray);

	    // add vertical line
	    Chart.addMarker(plot, sampleSize, subsetSize, forecastLength);
	    
	    // Domain axis - label position (slanted)
	    final CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
	    domainAxis.setLabelFont(new Font("Verdana", Font.PLAIN, 22));
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
   
   
   private static void addMarker(CategoryPlot plot, int sampleSize, int subsetSize, int forecastLength) {
	   
       final Day day = new Day(sampleSize, 10, 2017);
       final CategoryMarker forecastStart = new CategoryMarker(day);
       
       forecastStart.setPaint(Color.orange);
       forecastStart.setLabel("Forecasted Data: " + forecastLength + " points are added.");
       forecastStart.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
       forecastStart.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
       plot.addDomainMarker(forecastStart);
   }
   
   
   private String setTypeOfForecast(String method, String info) {
	   
	   String descr = "Forecasting method used: " + method + info;
	   return descr;
   }
   
}


