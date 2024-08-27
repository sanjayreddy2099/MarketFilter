package com.MarketFilter.MarketFilter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DataFetchSchedulerService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private Nifty50Service nifty50Service;
    @Autowired
    private NiftyNext50Service niftyNext50Service;
    @Autowired
    private NiftySmallCapService niftySmallCapService;
    @Autowired
    private NseDataService nseDataService;

    private static final long CACHE_DURATION_MINUTES = 5; // 30 minutes

    @PostConstruct
    public void startScheduledTasks() {
        System.out.println("----> Scheduling data fetch tasks");

        // Fetch data immediately at startup, then every 30 minutes
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Fetching data from Nifty50Service");
                nifty50Service.fetchAndCacheData();
            } catch (Exception e) {
                System.err.println("Error in Nifty50Service fetch task: " + e.getMessage());
            }
        }, 0, CACHE_DURATION_MINUTES, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Fetching data from NiftyNext50Service");
                niftyNext50Service.fetchAndCacheData();
            } catch (Exception e) {
                System.err.println("Error in NiftyNext50Service fetch task: " + e.getMessage());
            }
        }, 0, CACHE_DURATION_MINUTES, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Fetching data from NiftySmallCapService");
                niftySmallCapService.fetchAndCacheData();
            } catch (Exception e) {
                System.err.println("Error in NiftySmallCapService fetch task: " + e.getMessage());
            }
        }, 0, CACHE_DURATION_MINUTES, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Fetching data from NseDataService");
                nseDataService.fetchAndCacheData();
            } catch (Exception e) {
                System.err.println("Error in NseDataService fetch task: " + e.getMessage());
            }
        }, 0, CACHE_DURATION_MINUTES, TimeUnit.MINUTES);
    }
}
