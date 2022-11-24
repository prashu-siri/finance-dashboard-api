package com.self.financedashboard.controller;

import com.google.gson.Gson;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock/")
public class StockController {

    private final StockService stockService;
    private final Gson gson= new Gson();

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @CrossOrigin
    @PostMapping("add")
    public ResponseEntity<?> addStock(@RequestBody List<Stock> stocks){

        try {
            stockService.addStock(stocks);
        }catch (Exception exception) {
            return new ResponseEntity<>(gson.toJson("Error while adding stock details"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(gson.toJson("Stock added successfully"), HttpStatus.OK);
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

    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public void deleteStock(@PathVariable int id) {
        stockService.deleteStock(id);
    }
}
