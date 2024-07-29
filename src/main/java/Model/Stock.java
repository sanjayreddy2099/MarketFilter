package Model;

public class Stock {
	
	
	private String symbol;
	private double high;
	private double  low;
	private double currentprice;
	
	
	public Stock(String symbol, double high , double low, double currentPrice) {
		this.symbol = symbol;
		this.high = high;
		this.low = low;
		this.currentprice = currentPrice;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public double getLow() {
		return low;
	}
	public double getCurrentPrice() {
		return currentprice;
	}
	
	public String toString() {
		return "Stock{" +
	           "symbol='" + symbol +'\''+
	           ",high=" + high +
	           ",low=" + low +
	           ", currentPrice=" + currentprice +
	           '}';
	}

}
