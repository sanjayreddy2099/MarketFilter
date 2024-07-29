package com.MarketFilter.MarketFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"Controller", "Service", "Model"})

public class MarketFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketFilterApplication.class, args);
	}

}
