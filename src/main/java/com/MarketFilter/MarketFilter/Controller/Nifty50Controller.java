package com.MarketFilter.MarketFilter.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MarketFilter.MarketFilter.Service.Nifty50Service;

@RestController
@RequestMapping("/api") 
@CrossOrigin(origins = "*") 
public class Nifty50Controller {
    
    private final Nifty50Service nifty50Service;

    @Autowired
    public Nifty50Controller(Nifty50Service nifty50Service) {
        this.nifty50Service = nifty50Service;
    }

    @GetMapping("/Nifty50")
    public List<Map<String, String>> getCompanyData() {
        return nifty50Service.getCompanyData();
    }
}
