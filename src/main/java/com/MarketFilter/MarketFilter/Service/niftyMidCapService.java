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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class niftyMidCapService {

	private static final String URL = "https://www.moneycontrol.com/stocks/marketstats/indcontrib.php?optex=NSE&opttopic=indcontrib&index=27";
	private static final long CACHE_DURATION = 2 * 60 * 60 * 1000; // 2 hours in milliseconds
	

	private final ConcurrentHashMap<String, List<Map<String, String>>> cache = new ConcurrentHashMap<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public niftyMidCapService() {

		scheduler.scheduleAtFixedRate(this::fetchAndCacheData, 0, CACHE_DURATION, TimeUnit.MILLISECONDS);
	}

	public List<Map<String, String>> getCompanyData() {
		return cache.getOrDefault("companyData", new ArrayList<>());
	}

	private void fetchAndCacheData() {
		try {
			List<Map<String, String>> data = scrapeData();
			cache.put("companyData", data);
		} catch (IOException e) {
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

