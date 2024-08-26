package com.MarketFilter.MarketFilter.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MarketFilter.MarketFilter.Service.Nifty50Service;
import com.MarketFilter.MarketFilter.Service.NiftyNext50Service;

@RestController
@RequestMapping("/api") 
@CrossOrigin(origins = "*") 
public class NiftyNext50Controller {
	
	@Autowired
	 private  NiftyNext50Service niftynext50Service;

	    
	   

	    @GetMapping("/NiftyNext50")
	    public List<Map<String, String>> getCompanyData() {
	        return niftynext50Service.getCompanyData();
	    }

}
