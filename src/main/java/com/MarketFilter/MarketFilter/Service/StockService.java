package com.MarketFilter.MarketFilter.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MarketFilter.MarketFilter.Model.Stock;
import com.MarketFilter.MarketFilter.Repository.StockRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private ExccelReader excelReader;

    @Autowired
    private StockRepository stockRepository;

    @Scheduled(cron = "0 0 18 * * ?") 
    @Transactional
    public void fetchAndStoreStockData() {
        List<String> stockSymbols = excelReader.readStockSymbols();
        List<Stock> stocks = fetchStockData(stockSymbols);
        List<Stock> nearAllTimeLowStocks = getStocksNearAllTimeLow(stocks, 2.0); 

        for (Stock stock : nearAllTimeLowStocks) {
           
            Stock existingStock = stockRepository.findBySymbol(stock.getSymbol());
            if (existingStock != null) {
                
                existingStock.setHigh(stock.getHigh());
                existingStock.setLow(stock.getLow());
                existingStock.setCurrentPrice(stock.getCurrentPrice());
                stockRepository.save(existingStock);
            } else {
               
                stockRepository.save(stock);
            }
        }
    }

    public List<Stock> fetchStockData(List<String> stockSymbols) {
        List<Stock> stocks = new ArrayList<>();

        for (String symbol : stockSymbols) {
            try {
                String url = "https://www.screener.in/company/" + symbol + "/consolidated/";
                Document doc = null;
                int attempts = 0;
                boolean success = false;
                Thread.sleep(100);

                while (attempts < 3 && !success) {
                    try {
                        doc = Jsoup.connect(url).get();
                        success = true;
                    } catch (org.jsoup.HttpStatusException e) {
                        if (e.getStatusCode() == 404) {
                            System.out.println("Stock not found: " + symbol);
                            break;
                        } else if (e.getStatusCode() == 429) {
                            System.out.println("Rate limit exceeded. Waiting before retrying...");
                            Thread.sleep(5000);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    attempts++;
                }

                if (doc == null) {
                    continue;
                }

                Element highLowElement = doc.select("li:contains(High / Low) .nowrap.value").first();
                Element currentPriceElement = doc.select("li:contains(Current Price) .nowrap.value").first();

                if (highLowElement != null && currentPriceElement != null) {
                    Elements numbers = highLowElement.select("span.number");
                    if (numbers.size() >= 2) {
                        String highText = numbers.get(0).text();
                        String lowText = numbers.get(1).text();

                        if (isValidNumber(highText) && isValidNumber(lowText)) {
                            double high = Double.parseDouble(highText.replace(",", ""));
                            double low = Double.parseDouble(lowText.replace(",", ""));

                            Elements currentPriceNumbers = currentPriceElement.select("span.number");
                            if (!currentPriceNumbers.isEmpty()) {
                                String currentPriceText = currentPriceNumbers.first().text();
                                if (isValidNumber(currentPriceText)) {
                                    double currentPrice = Double.parseDouble(currentPriceText.replace(",", ""));
                                    Stock stock = new Stock(symbol, high, low, currentPrice);
                                    stocks.add(stock);
                                } else {
                                    System.out.println("Could not find valid current price for stock: " + symbol);
                                }
                            } else {
                                System.out.println("Could not find current price for stock: " + symbol);
                            }
                        } else {
                            System.out.println("Could not find valid high/low values for stock: " + symbol);
                        }
                    } else {
                        System.out.println("Could not find high/low values in the expected format for stock: " + symbol);
                    }
                } else {
                    System.out.println("Could not find high/low or current price container for stock: " + symbol);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return stocks;
    }

    public List<Stock> getStocksNearAllTimeLow(List<Stock> stocks, double thresholdPercentage) {
        List<Stock> nearAllTimeLowStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            double low = stock.getLow();
            double currentPrice = stock.getCurrentPrice();

            double thresholdPrice = low * (1 + thresholdPercentage / 100);
            if (currentPrice <= thresholdPrice) {
                nearAllTimeLowStocks.add(stock);
            }
        }
        return nearAllTimeLowStocks;
    }

    public List<Stock> findStocksNearAllTimeLow() {
        return stockRepository.findAll();
    }

    private boolean isValidNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.replace(",", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
