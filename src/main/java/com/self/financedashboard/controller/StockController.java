package com.self.financedashboard.controller;

import com.google.gson.Gson;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.ErrorResponse;
import com.self.financedashboard.model.Intraday;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.StockDetails;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.model.company.CompanyDetails;
import com.self.financedashboard.model.marketTrends.TrendResponse;
import com.self.financedashboard.model.quote.Quote;
import com.self.financedashboard.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    private static final String STOCK_EXCHANGE = ":NSE";

    private final StockService stockService;
    private final Gson gson;

    public StockController(StockService stockService, Gson gson) {
        this.stockService = stockService;
        this.gson = gson;
    }

    @CrossOrigin
    @GetMapping("quote/{symbol}")
    public ResponseEntity<?> stockQuote(@PathVariable String symbol) {
        try {
            logger.info("StockController :: stockQuote :: {}", symbol);
            String stockSymbol = symbol + STOCK_EXCHANGE;
            Quote stockQuote = stockService.getStockQuote(stockSymbol);
            return new ResponseEntity<>(stockQuote, HttpStatus.OK);
        }catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while getting the stock quote",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("marketTrends")
    public ResponseEntity<?> getMarketTrends() {
        try {
            TrendResponse marketTrends = stockService.getMarketTrends();
            return new ResponseEntity<>(marketTrends, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while getting the stock quote",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping("add")
    public ResponseEntity<?> addStock(@RequestBody List<Stock> stocks){

        try {
            logger.info("StockController :: addStock :: Adding {} stocks", stocks.get(0).getStockName());
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
    @GetMapping("stocks/{userId}")
    public Map<String, List<Stock>> getStocks(@PathVariable int userId) {
        return stockService.getStocks(userId);
    }

    @CrossOrigin
    @GetMapping("dashboard/{userId}")
    public ResponseEntity<?> getUserStocks(@PathVariable int userId) {
        try {
            logger.info("StockController :: getUserStocks :: getting stocks for {}", userId);
            List<DashboardSummary> stocks = stockService.getUserStocks(userId);
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
    @DeleteMapping("delete")
    public void deleteStock(@RequestBody Map<String, Object> details) {
        stockService.deleteStock(details);
    }

    @CrossOrigin
    @DeleteMapping("deleteAll/{id}")
    public void deleteAllStocks(@PathVariable int id) {
        stockService.deleteAllStocks(id);
    }

    @CrossOrigin
    @PostMapping("update")
    public Map<String, Object> updateStock(@RequestBody Stock stock) {
        Map<String, Object> responseMap = new HashMap<>();
        logger.info("StockController :: updateStock :: updating {} stock", stock.getStockName());
        try {
            String response = stockService.updateStock(stock);
            responseMap.put("status", HttpStatus.OK);
            responseMap.put("data", response);
        } catch (Exception e) {
            logger.error("Error while updating the {} stock", stock.getStockName());
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            responseMap.put("data","Error while updating the stock details");
        }

        return responseMap;
    }

    @CrossOrigin
    @GetMapping("intraday/{symbol}")
    public Intraday intraday(@PathVariable String symbol) {
        return stockService.getIntraday(symbol);
    }

    @CrossOrigin
    @GetMapping("company/{symbol}")
    public CompanyDetails getCompanyDetails(@PathVariable String symbol) {
        logger.info("StockController :: getCompanyDetails :: {}", symbol);
        String stockSymbol = symbol + STOCK_EXCHANGE;
        return stockService.getCompanyDetails(stockSymbol);
    }
}
