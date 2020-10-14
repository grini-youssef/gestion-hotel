package com.graphique;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.client.NouveauClient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class LineChartSample extends Application {
	
	Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);
	
	String janvier="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-01-01' AND date_debut <= '"+year+"-01-31' ;";
	String fevrier="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-02-01' AND date_debut <= '"+year+"-02-31' ;";
	String mars="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-03-01' AND date_debut <= '"+year+"-03-31' ;";
	String avril="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-04-01' AND date_debut <= '"+year+"-04-31' ;";
	String mai="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-05-01' AND date_debut <= '"+year+"-05-31' ;";
	String juin="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-06-01' AND date_debut <= '"+year+"-06-31' ;";
	String juillet="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-07-01' AND date_debut <= '"+year+"-07-31' ;";
	String aout="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-08-01' AND date_debut <= '"+year+"-08-31' ;";
	String septembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-09-01' AND date_debut <= '"+year+"-09-31' ;";
	String octobre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-10-01' AND date_debut <= '"+year+"-10-31' ;";
	String novembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-11-01' AND date_debut <= '"+year+"-11-31' ;";
	String decembre="SELECT prix_paye FROM reservation WHERE 	date_debut >= '"+year+"-12-01' AND date_debut <= '"+year+"-12-31' ;";
	
    
    @Override public void start(Stage stage) throws SQLException {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");       
        
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Progression des reservation "+year);
                                
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        
        series.getData().add(new XYChart.Data("Jan", resultat(janvier)));
        series.getData().add(new XYChart.Data("Feb", resultat(fevrier)));
        series.getData().add(new XYChart.Data("Mar", resultat(mars)));
        series.getData().add(new XYChart.Data("Apr", resultat(avril)));
        series.getData().add(new XYChart.Data("May", resultat(mai)));
        series.getData().add(new XYChart.Data("Jun", resultat(juin)));
        series.getData().add(new XYChart.Data("Jul", resultat(juillet)));
        series.getData().add(new XYChart.Data("Aug", resultat(aout)));
        series.getData().add(new XYChart.Data("Sep", resultat(septembre)));
        series.getData().add(new XYChart.Data("Oct", resultat(octobre)));
        series.getData().add(new XYChart.Data("Nov", resultat(novembre)));
        series.getData().add(new XYChart.Data("Dec", resultat(decembre)));
        
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
    
    public int resultat(String sql) throws SQLException {
    	int n=0;
    	Connection cnx = NouveauClient.connecterDB();
		Statement stm = cnx.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while (rs.next()) {
		n+=1;
		}	
    	return n;
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}