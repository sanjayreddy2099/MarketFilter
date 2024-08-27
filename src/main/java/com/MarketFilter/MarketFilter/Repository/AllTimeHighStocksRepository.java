package com.MarketFilter.MarketFilter.Repository;

import com.MarketFilter.MarketFilter.Model.AllTimeHighStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllTimeHighStocksRepository extends JpaRepository<AllTimeHighStocks, Long> {
    
}
