package com.MarketFilter.MarketFilter.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock") 
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "high")
    private double high;
    @Column(name = "low")
    private double low;
    @Column(name = "current_price")
    private double currentPrice;


    public Stock() {}

    public Stock(String symbol, double high, double low, double currentPrice) {
        this.symbol = symbol;
        this.high = high;
        this.low = low;
        this.currentPrice = currentPrice;
    }

  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public String toString() {
		return "Stock{" +
	           "symbol='" + symbol +'\''+
	           ",high=" + high +
	           ",low=" + low +
	           ", currentPrice=" + currentPrice +
	           '}';
	}

}
