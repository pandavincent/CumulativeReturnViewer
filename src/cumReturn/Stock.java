package cumReturn;

public class Stock {

	private String ticker;
	private String from;
	private String to;
	
	public Stock(String ticker, String from, String to){
		this.ticker = ticker;
		this.from = from;
		this.to = to;
	}
	
	public String getTicker(){
		return this.ticker;
	}
	
	public String getFrom(){
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
}
