package com.MarketFilter.MarketFilter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MarketFilter.MarketFilter.Model.Stock;
import com.MarketFilter.MarketFilter.Service.StockService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/near-all-time-low")
    public List<Stock> getStocksNearAllTimeLow() {
        return stockService.findStocksNearAllTimeLow();
    }
}
