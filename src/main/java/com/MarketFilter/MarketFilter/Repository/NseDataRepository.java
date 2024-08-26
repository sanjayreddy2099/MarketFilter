package com.MarketFilter.MarketFilter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MarketFilter.MarketFilter.Model.NseData;

public interface NseDataRepository extends JpaRepository<NseData, String>{

}
