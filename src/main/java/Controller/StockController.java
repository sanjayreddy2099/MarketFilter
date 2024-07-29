package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Model.Stock;
import Service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/near-all-time-low")
    public List<Stock> getStocksNearAllTimeLow() {
        System.out.println("hello");
        double thresholdPercentage = 2.0; 
        return stockService.findStocksNearAllTimeLow(thresholdPercentage);
    }
}
