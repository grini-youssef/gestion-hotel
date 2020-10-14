package com.graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

import com.client.NouveauClient;

public class JavaJFreeChartLineChartExample extends ApplicationFrame {

	static Calendar calendar = new GregorianCalendar();
	static int year = calendar.get(Calendar.YEAR);
	
	static String janvier="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-01-01' AND date_debut <= '"+year+"-01-31' ;";
	static String fevrier="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-02-01' AND date_debut <= '"+year+"-02-28' ;";
	static String mars="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-03-01' AND date_debut <= '"+year+"-03-31' ;";
	static String avril="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-04-01' AND date_debut <= '"+year+"-04-30' ;";
	static String mai="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-05-01' AND date_debut <= '"+year+"-05-31' ;";
	static String juin="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-06-01' AND date_debut <= '"+year+"-06-30' ;";
	static String juillet="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-07-01' AND date_debut <= '"+year+"-07-31' ;";
	static String aout="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-08-01' AND date_debut <= '"+year+"-08-31' ;";
	static String septembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-09-01' AND date_debut <= '"+year+"-09-30' ;";
	static String octobre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-10-01' AND date_debut <= '"+year+"-10-31' ;";
	static String novembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-11-01' AND date_debut <= '"+year+"-11-30' ;";
	static String decembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-12-01' AND date_debut <= '"+year+"-12-31' ;";
	
	public JavaJFreeChartLineChartExample(String title) throws SQLException {
		super(title);
		CategoryDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1100, 640));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(chartPanel);
	}

	private static CategoryDataset createDataset() throws SQLException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(resultat(janvier), "Classes", "Janvier");
		dataset.addValue(resultat(fevrier), "Classes", "fevrier");
		dataset.addValue(resultat(mars), "Classes", "Mars");
		dataset.addValue(resultat(avril), "Classes", "Avril");
		dataset.addValue(resultat(mai), "Classes", "Mai");
		dataset.addValue(resultat(juin), "Classes", "Juin");
		dataset.addValue(resultat(juillet), "Classes", "Juillet");
		dataset.addValue(resultat(aout), "Classes", "Aout");
		dataset.addValue(resultat(septembre), "Classes", "Septembre");
		dataset.addValue(resultat(octobre), "Classes", "Octobre");
		dataset.addValue(resultat(novembre), "Classes", "Novembre");
		dataset.addValue(resultat(decembre), "Classes", "Decembre");
		return dataset;
	}

	private static JFreeChart createChart(CategoryDataset dataset) {
		// create the chart...
		JFreeChart chart = ChartFactory.createLineChart("Progression des reservation "+year, // chart
																						// //
																						// title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips
				false // urls
		);
		chart.addSubtitle(new TextTitle(""));
		TextTitle source = new TextTitle("");
		source.setFont(new Font("SansSerif", Font.PLAIN, 10));
		source.setPosition(RectangleEdge.BOTTOM);
		source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		chart.addSubtitle(source);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);
		// customise the range axis...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// customise the renderer...
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setFillPaint(Color.white);
		return chart;
	}

	public static JPanel createDemoPanel() throws SQLException {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
	
	public static int resultat(String sql) throws SQLException {
    	int n=0;
    	Connection cnx = NouveauClient.connecterDB();
		Statement stm = cnx.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while (rs.next()) {
		n+=1;
		}
		stm.close();
		cnx.close();
    	return n;
    }

	public static void main(String[] args) throws SQLException {
		JavaJFreeChartLineChartExample barChart = new JavaJFreeChartLineChartExample("Line Chart Demo");
		barChart.pack();
		RefineryUtilities.centerFrameOnScreen(barChart);
		barChart.setVisible(true);
	}
}