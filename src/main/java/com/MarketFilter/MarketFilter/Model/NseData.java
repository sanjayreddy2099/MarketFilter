package com.MarketFilter.MarketFilter.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nse_stock_data")
public class NseData {
	



	
	    @Id
	    @Column(name = "symbol")
	    private String symbol;

	    @Column(name = "open")
	    private String open;

	    @Column(name = "high")
	    private String high;

	    @Column(name = "low")
	    private String low;

	    @Column(name = "prev_close")
	    private String prevClose;

	    @Column(name = "ltp")
	    private String ltp;

	    @Column(name = "chng")
	    private String chng;

	    @Column(name = "pct_chng")
	    private String pctChng;

	    @Column(name = "volume")
	    private String volume;

	    @Column(name = "value")
	    private String value;

	    @Column(name = "fifty_two_week_high")
	    private String fiftyTwoWeekHigh;

	    @Column(name = "fifty_two_week_low")
	    private String fiftyTwoWeekLow;

	    @Column(name = "thirty_day_pct_chng")
	    private String thirtyDayPctChng;

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public String getOpen() {
			return open;
		}

		public void setOpen(String open) {
			this.open = open;
		}

		public String getHigh() {
			return high;
		}

		public void setHigh(String high) {
			this.high = high;
		}

		public String getLow() {
			return low;
		}

		public void setLow(String low) {
			this.low = low;
		}

		public String getPrevClose() {
			return prevClose;
		}

		public void setPrevClose(String prevClose) {
			this.prevClose = prevClose;
		}

		public String getLtp() {
			return ltp;
		}

		public void setLtp(String ltp) {
			this.ltp = ltp;
		}

		public String getChng() {
			return chng;
		}

		public void setChng(String chng) {
			this.chng = chng;
		}

		public String getPctChng() {
			return pctChng;
		}

		public void setPctChng(String pctChng) {
			this.pctChng = pctChng;
		}

		public String getVolume() {
			return volume;
		}

		public void setVolume(String volume) {
			this.volume = volume;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getFiftyTwoWeekHigh() {
			return fiftyTwoWeekHigh;
		}

		public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
			this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
		}

		public String getFiftyTwoWeekLow() {
			return fiftyTwoWeekLow;
		}

		public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
			this.fiftyTwoWeekLow = fiftyTwoWeekLow;
		}

		public String getThirtyDayPctChng() {
			return thirtyDayPctChng;
		}

		public void setThirtyDayPctChng(String thirtyDayPctChng) {
			this.thirtyDayPctChng = thirtyDayPctChng;
		}
	    
	    
	    

	   
	}



