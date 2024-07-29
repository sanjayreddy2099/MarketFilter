package Service;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private ExccelReader excelReader;

    private static final String EXCEL_FILE_PATH = "D:\\project Bio\\Book1.xlsx";

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
                                    stocks.add(new Stock(symbol, high, low, currentPrice));
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

    public List<Stock> findStocksNearAllTimeLow(double thresholdPercentage) {
        List<String> stockSymbols = excelReader.readStockSymbols(EXCEL_FILE_PATH);
        List<Stock> stocks = fetchStockData(stockSymbols);
        return getStocksNearAllTimeLow(stocks, thresholdPercentage);
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
