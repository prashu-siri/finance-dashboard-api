package com.self.financedashboard.repository;

import com.self.financedashboard.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {
    List<Stock> findByStockSymbol(String stockSymbol);
}
