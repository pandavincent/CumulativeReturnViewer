package cumReturn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {	
	
	public static void main(String [] args) throws ParseException {
		
		// Future Improvement: Prompt user to input file name
		
		// Create an ArrayList of stocks
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		
		//Read in and Parse the config.ini file
		try {
		    BufferedReader in = new BufferedReader(new FileReader("config.ini"));
		    String str = null;
		    String ticker = null;
		    String from = null;
		    String to = null;
		    		    
		    // Use Regular Expression to read data from file
		    while ((str = in.readLine()) != null) {
		    	if (str.startsWith("Ticker")){
		    		
		    		str = str.substring(7);
		    		// Extract any capital letter combination with length 1 to 5
		    		ticker = regexChecker("[A-Z]{1,5}", str);
		    		
		    	} 
		    	else if (str.startsWith("From")){
		    		
		    		str = str.substring(7);
		    		// Extract any single digit combination with length 4 to 8
		    		from = regexChecker("[0-9]{4,8}", str);
		    		
		    	} 	
		    	else if (str.startsWith("To")){
		    		
		    		str = str.substring(7);
		    		// Extract any single digit combination with length 4 to 8
		    		to = regexChecker("[0-9]{4,8}", str);
		    		
		    		// Adding the stock to stockList
		    		Stock stock = new Stock(ticker, from, to);
		    		stockList.add(stock);
		    		
		    	}
		    }

		    in.close();
		    
		} catch (IOException e) {
			System.out.println("Reading file exception");
		}
		
		// Future Improvement: Program keeps running until user explicitly quit. (Use while loop)
		
		// Print out available stock for users to choose
		for (int i = 0; i < stockList.size(); i++) {
			System.out.println(i + "'s stock is " + stockList.get(i).getTicker());
		}
		
		// Take user input
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter index of the stock you want to see graph: ");
		int index = scan.nextInt();
		
		// Checking for input validity
		if (index < 0 || index > stockList.size()-1){
			System.out.println("Invalid input. Please re-try");
			return;
		}
		
		// Prepare data according to user input 
		String stockName = stockList.get(index).getTicker();
		String startTime = stockList.get(index).getFrom();
	    String endTime = stockList.get(index).getTo();
	    
	    // Create the specific stock's model
		Model model = new Model(stockName, startTime, endTime);
		
		ArrayList<Double> closeList = model.getCloseList();		
	
		ArrayList<Double> cumReturnList = new ArrayList<Double>();
		
		double cumReturnValue = 0;

		for (int i = 0; i < closeList.size(); i++) {
			System.out.println(closeList.get(i));
		}
		
		// Calculate cumulative return and put them into cumReturnList
		for (int i = 0; i < closeList.size(); i++) {		
			if (i+1 != closeList.size()) {
				cumReturnValue = (closeList.get(i) - closeList.get(0))/closeList.get(0);
				cumReturnList.add(cumReturnValue);
			}
		}			
	
		// Create a new file stockName.ini to stock cumulative return information
		Path path = Paths.get(stockName + ".ini");		
		if (Files.notExists(path)){
			File file = new File(stockName + ".ini");	 
		} 
			
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(stockName + ".ini"));
			// Write stock name
			fileWriter.write(stockName+"\n");
			fileWriter.flush();

			// Write stock returns
			for (int i = 0; i < cumReturnList.size(); i++) {
				fileWriter.write(cumReturnList.get(i)+"\n");
				fileWriter.flush();
			}	
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("File writing exception");
		}
		
		// Visualize the return information on graph
		View stockView = new View();
		stockView.showChart(stockName + ".ini");			
	} 	
	
	// Regular Expression checker
	public static String regexChecker(String theRegex, String strToCheck) {
		Pattern checkRegex = Pattern.compile(theRegex);
		
		Matcher regexMatcher = checkRegex.matcher(strToCheck); 
		
		while(regexMatcher.find()) {
			if(regexMatcher.group().length() != 0) {
				return (regexMatcher.group().trim());
			}
		}
		return null;		
	}
}
