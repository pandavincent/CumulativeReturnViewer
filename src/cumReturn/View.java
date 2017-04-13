package cumReturn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class View extends Application {

    @Override public void start(Stage stage) {
    	
        stage.setTitle("Vincent Capital");
        String fileName = null;
        String stockName = null;
       
        ArrayList<Double> cumReturnList = new ArrayList<Double>();
        final Parameters params = getParameters();
        
        List<String> rawArguments = params.getRaw();
          
        for (String raw : rawArguments){
        	fileName = raw;
        }
 
        System.out.println("graph showing from file: " + fileName);

        // Parse the file 
        try {
		    BufferedReader in = new BufferedReader(new FileReader(fileName));
		    String str = null;
		    stockName = in.readLine();   
		    // Adding cumulative returns to cumReturnList
		    while ((str = in.readLine()) != null) {
		    	cumReturnList.add(Double.parseDouble(str));
		    }

		    in.close();
		} catch (IOException e) {
			System.out.println("Reading file exception");
		}
   
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");       
        
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
        
        lineChart.setTitle("Company Ticker : " + stockName);
                                
        XYChart.Series series = new XYChart.Series();
        series.setName("Cumulative Return");
        
        // Future Improvement: add month value on graph (write month information to stockName.ini)
        for (int i = 0; i < cumReturnList.size(); i++) {
        	series.getData().add(new XYChart.Data(""+i, cumReturnList.get(i)));
        }
        
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public void showChart(String fileName) {
    	System.out.println("generating graph...");
        launch(fileName);
    }
    
}