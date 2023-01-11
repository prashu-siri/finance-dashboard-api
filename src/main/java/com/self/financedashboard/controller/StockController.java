package com.self.financedashboard.controller;

import com.google.gson.Gson;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.ErrorResponse;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.service.StockService;
import lombok.extern.slf4j.Slf4j;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock/")
@Slf4j
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
            return new ResponseEntity<>(gson.toJson("Stock added successfully"), HttpStatus.OK);
        }catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while adding stock details",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("stocks")
    public Map<String, List<Stock>> getStocks() {
        return stockService.getStocks();
    }

    @CrossOrigin
    @GetMapping("dashboard")
    public ResponseEntity<?> getUserStocks() {
        try {
            List<DashboardSummary> stocks = stockService.getUserStocks();
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        }catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while fetching user stock details",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @CrossOrigin
    @DeleteMapping("deleteAll")
    public void deleteAllStocks(@PathVariable int id) {
        stockService.deleteAllStocks(id);
    }

    @CrossOrigin
    @PostMapping("update")
    public Map<String, Object> updateStock(@RequestBody Stock stock) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String response = stockService.updateStock(stock);
            responseMap.put("status", HttpStatus.OK);
            responseMap.put("data", response);
        } catch (Exception e) {
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            responseMap.put("data","Error while updating the stock details");
        }

        return responseMap;
    }
}
