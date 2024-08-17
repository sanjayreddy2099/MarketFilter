package com.MarketFilter.MarketFilter.Repository;

import com.MarketFilter.MarketFilter.Model.Stock;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MarketFilter.MarketFilter.Model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	
	Stock findBySymbol(String symbol);
}
