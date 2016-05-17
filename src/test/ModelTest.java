package test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import cumReturn.Model;

public class ModelTest {
	
	@Test
	public void shouldReturnUrl(){
		//Given MSFT from 2012/03/02 to 2015/03/02	            
		String url = "http://real-chart.finance.yahoo.com/table.csv?s=MSFT&a=2&b=2&c=2012&d=2&e=2&f=2015&g=m&ignore=.csv";
		
		//When
		Model model = new Model("MSFT", "20120302", "20150302");
		
		//Then
		Assert.assertEquals(model.getUrl(), url);
	}
	
	@Test
	public void shouldReturnCloseList(){
		//Given QQQ from 2015/10/01 to 2015/12/09	            		                		
		ArrayList<Double> cList = new ArrayList<Double>(
		          Arrays.asList(113.330002,
		        		  	114.019997,
		        		  	112.889999
	               ));
			
		//When		
		Model model = new Model("QQQ", "20151001", "20151209");
		
		//Then
		Assert.assertEquals(model.getCloseList(), cList);
	}
		
}
