package test;

import org.junit.Assert;
import org.junit.Test;

import cumReturn.Stock;

public class StockTest {
	
	@Test
	public void shouldReturnTicker(){
		//Given
		String ticker = "TSLA";
		
		//When
		Stock stock = new Stock(ticker, "20151101", "20161101");
		
		//Then
		Assert.assertEquals(stock.getTicker(), ticker);
	}

	@Test
	public void shouldReturnFrom(){
		//Given
		String from = "20151101";
				
		//When
		Stock stock = new Stock("TSLA", from, "20161101");
		
		//Then
		Assert.assertEquals(stock.getFrom(), from);	
	}
	
	@Test
	public void shouldReturnTo(){
		//Given
		String to = "20161101";
				
		//When
		Stock stock = new Stock("TSLA", "20151101", to);
		
		//Then
		Assert.assertEquals(stock.getTo(), to);	
	}
	
}
