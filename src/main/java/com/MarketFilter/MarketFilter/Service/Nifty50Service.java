package com.MarketFilter.MarketFilter.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class Nifty50Service {

    private static final String URL = "https://moneycontrol.com/stocks/marketstats/indcontrib.php?optex=NSE&opttopic=indcontrib&index=9";
    
   
    private final ConcurrentHashMap<String, List<Map<String, String>>> cache = new ConcurrentHashMap<>();

    
    public List<Map<String, String>> getCompanyData() {
       
        return cache.getOrDefault("companyData", new ArrayList<>());
    }

  
    public void fetchAndCacheData() {
        try {
            List<Map<String, String>> data = scrapeData();
            cache.put("companyData", data);
        } catch (IOException e) {
            
            System.err.println("Error fetching and caching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    private List<Map<String, String>> scrapeData() throws IOException {
        
        Document doc = Jsoup.connect(URL).get();
        Elements rows = doc.select("table tbody tr");

        
        return rows.stream().map(row -> {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("companyName", row.select("td.PR a b").text());
            dataMap.put("ltp", row.select("td:nth-child(3)").text());
            dataMap.put("change", row.select("td:nth-child(4)").text());
            dataMap.put("changePercentage", row.select("td:nth-child(5)").text());
            return dataMap;
        }).collect(Collectors.toList());
    }
}
