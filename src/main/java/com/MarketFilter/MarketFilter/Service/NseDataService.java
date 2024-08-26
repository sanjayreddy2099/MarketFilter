package com.MarketFilter.MarketFilter.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class NseDataService {

    private static final String NIFTY_URL = "https://moneycontrol.com/indian-indices/nifty-50-9.html";
    private static final String BANK_NIFTY_URL = "https://moneycontrol.com/indian-indices/bank-nifty-23.html";
    private static final String NIFTY_IT_URL = "https://www.moneycontrol.com/indian-indices/nifty-it-19.html";
    private static final String NIFTY_FMCG_URL = "https://www.moneycontrol.com/indian-indices/nifty-fmcg-39.html";
    private static final String NIFTY_PHARMA_URL = "https://www.moneycontrol.com/indian-indices/nifty-pharma-41.html";
    private static final String NIFTY_AUTO_URL = "https://www.moneycontrol.com/indian-indices/nifty-auto-52.html";

    // Cache to store data
    private Map<String, Map<String, String>> cache = new HashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Constructor
    public NseDataService() {
        fetchAndCacheData(); // Initial data load
        startScheduledUpdates(); // Start periodic updates
    }

    private void startScheduledUpdates() {
        scheduler.scheduleAtFixedRate(this::fetchAndCacheData, 0, 10, TimeUnit.MINUTES);
    }

    // Method to fetch and update cache
    private void fetchAndCacheData() {
        System.out.println("Fetching and updating data...");
        cache.put("NIFTY", fetchData(NIFTY_URL));
        cache.put("BANK_NIFTY", fetchData(BANK_NIFTY_URL));
        cache.put("NIFTY_IT", fetchData(NIFTY_IT_URL));
        cache.put("NIFTY_FMCG", fetchData(NIFTY_FMCG_URL));
        cache.put("NIFTY_PHARMA", fetchData(NIFTY_PHARMA_URL));
        cache.put("NIFTY_AUTO", fetchData(NIFTY_AUTO_URL));
    }

    public Map<String, Map<String, String>> fetchAllIndexData() {
        return new HashMap<>(cache); // Return a copy of the cached data
    }

    private Map<String, String> fetchData(String url) {
        Map<String, String> data = new HashMap<>();
        try {
            Document doc = Jsoup.connect(url).get();

            Element valueElement = doc.selectFirst("#sp_val");
            data.put("value", valueElement != null ? valueElement.text() : "N/A");

            Element lowElement = doc.selectFirst("#sp_yearlylow");
            data.put("yearLow", lowElement != null ? lowElement.text() : "N/A");

            Element highElement = doc.selectFirst("#sp_yearlyhigh");
            data.put("yearlyHigh", highElement != null ? highElement.text() : "N/A");

            Element changeElement = doc.selectFirst("#sp_ch_prch");
            data.put("change", changeElement != null ? changeElement.text() : "N/A");

        } catch (IOException e) {
            data.put("value", "Error fetching data");
            data.put("yearLow", "Error fetching data");
            data.put("yearlyHigh", "Error fetching data");
            data.put("change", "Error fetching data");
        }
        return data;
    }
}
