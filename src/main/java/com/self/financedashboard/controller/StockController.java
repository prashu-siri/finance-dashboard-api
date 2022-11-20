package com.self.financedashboard.controller;

import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.service.StockService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock/")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @CrossOrigin
    @PostMapping("add")
    public String addStock(@RequestBody List<Stock> stocks){
        stockService.addStock(stocks);
        return "Stock added successfully";
    }

    @CrossOrigin
    @GetMapping("stocks")
    public List<Stock> getStocks() {
        return stockService.getStocks();
    }

    @CrossOrigin
    @GetMapping("dashboard")
    public List<DashboardSummary> getUserStocks() {
        return stockService.getUserStocks();
    }

    @CrossOrigin
    @GetMapping("symbols")
    public List<Ticker> getSymbols() {
        return stockService.getSymbols();
    }
}
