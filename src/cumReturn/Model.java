package cumReturn;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Model {

	// Url for retriving financial data from Yahoo
	protected String url;
	
	// A list that holds all the closing prices
	protected ArrayList<Double> closeList;
	

	public Model(String ticker, String from, String to){
		closeList = new ArrayList<Double>();
		try{
			// Parse date data
			int from_dd = Integer.parseInt(from.substring(6,8));
			int from_mm = Integer.parseInt(from.substring(4,6)) - 1;
			int from_yyyy = Integer.parseInt(from.substring(0,4));
		
			int to_dd = Integer.parseInt(to.substring(6,8));
			int to_mm = Integer.parseInt(to.substring(4,6)) - 1;
			int to_yyyy = Integer.parseInt(to.substring(0,4));
			
			// Extract Yahoo! Finance URL
			url = "http://real-chart.finance.yahoo.com/table.csv?s="+ticker+
					"&a="+from_mm+
					"&b="+from_dd+
					"&c="+from_yyyy+
					"&d="+to_mm+
					"&e="+to_dd+
					"&f="+to_yyyy+
					"&g=m&ignore=.csv";
		
			// Download data from Yahoo Finance
			System.out.println("initializing connection...");
			URL yahoo = new URL(url);
			URLConnection data = yahoo.openConnection();
			Scanner scan = new Scanner(data.getInputStream());
			
			System.out.println("downloading data...");
			if(scan.hasNext()){
				scan.nextLine();
			}
			
			// Create closing price list
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				addClose(line);
			}
			Collections.reverse(closeList);
			
		}catch(Exception e) {
			System.out.println("Exception in parsing, connecting, and reading Yahoo! URL");
		}
	
	}
	
	// Adding all the closing values to cList
	public void addClose(String str) {
		String[] strElement = str.split(",");
		double close = Double.parseDouble(strElement[4]);
		closeList.add(close);	
	}
	
	public String getUrl(){
		return this.url;
	}
		
	public ArrayList<Double> getCloseList(){
		return this.closeList;
	}

}
