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
import com.MarketFilter.MarketFilter.Service.niftyMidCapService;

@RestController
@RequestMapping("/api") 
@CrossOrigin(origins = "*") 
public class niftyMidCapController {
	
	@Autowired
	 private  niftyMidCapService niftyMidCapService;
	    
	   

	    @GetMapping("/NiftyMidCap")
	    public List<Map<String, String>> getCompanyData() {
	        return niftyMidCapService.getCompanyData();
	    }

}
