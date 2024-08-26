package com.MarketFilter.MarketFilter.Controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MarketFilter.MarketFilter.Service.NseDataService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Nifty")
public class NseDataController {
    
  
    
    @Autowired
    private NseDataService nseDataService;
    
  
    
    @GetMapping("/fetch-nifty-value")
    public Map<String, Map<String, String>> fetchNiftyValue() {
        
        return nseDataService.fetchAllIndexData();
    }
}
