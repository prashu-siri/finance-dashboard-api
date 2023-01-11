package com.self.financedashboard.service;

import com.self.financedashboard.configuration.Config;
import com.self.financedashboard.enumeration.TickerSymbol;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.StockDetails;
import com.self.financedashboard.model.Summary;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.repository.StockRepository;
import com.self.financedashboard.repository.SummaryRepository;
import com.self.financedashboard.util.DashboardSummaryComparator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;
    private final SummaryRepository summaryRepository;
    private final Config config;

    public StockService(StockRepository stockRepository, SummaryRepository summaryRepository, Config config) {
        this.stockRepository = stockRepository;
        this.summaryRepository = summaryRepository;
        this.config = config;
    }

    public void addStock(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
        updateSummary(stocks, "add");
    }

    public Map<String, List<Stock>> getStocks() {
        List<Stock> computedStocks = new ArrayList<>();
        Map<String, List<Stock>> map = new HashMap<>();
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();

        for (Stock stock: stocks) {
            if(map.isEmpty()) {
                computedStocks.add(stock);
                map.put(stock.getStockName(), computedStocks);
            } else {
                if(map.containsKey(stock.getStockName())) {
                    List<Stock> value = map.get(stock.getStockName());
                    value.add(stock);
                    map.put(stock.getStockName(), value);
                } else {
                    computedStocks = new ArrayList<>();
                    computedStocks.add(stock);
                    map.put(stock.getStockName(), computedStocks);
                }
            }
        }

        return map;
    }

    public List<Ticker> getSymbols() {
        List<Ticker> tickers = new ArrayList<>();
        for (TickerSymbol symbol: TickerSymbol.values()) {
            Ticker ticker = new Ticker();
            ticker.setId(symbol.getId());
            ticker.setCompanyName(symbol.getCompanyName());
            ticker.setCompanySymbol(symbol.getSymbol());

            tickers.add(ticker);
        }

        return tickers;
    }

    public List<DashboardSummary> getUserStocks() {
        List<DashboardSummary> dashboardSummaryList = new ArrayList<>();
        List<Summary> summaryList = new ArrayList<>();
        summaryRepository.findAll().forEach(summaryList::add);

        for (Summary summary: summaryList) {
            double currentPrice = getStockCurrentPrice(summary.getSymbol());
            double totalCurrentPrice = currentPrice * summary.getQuantity();

            DashboardSummary dashboardSummary = new DashboardSummary();
            dashboardSummary.setId(summary.getId());
            dashboardSummary.setName(summary.getName());
            dashboardSummary.setQuantity(summary.getQuantity());
            dashboardSummary.setSymbol(summary.getSymbol());
            dashboardSummary.setInvestedAmount(summary.getInvestedAmount());
            dashboardSummary.setHoldings(totalCurrentPrice - summary.getInvestedAmount());
            dashboardSummary.setCurrentPrice(currentPrice);
            dashboardSummary.setTotalCurrentAmount(totalCurrentPrice);

            dashboardSummaryList.add(dashboardSummary);
        }

        dashboardSummaryList.sort(new DashboardSummaryComparator());

        return dashboardSummaryList;
    }

    private double getStockCurrentPrice(String symbol) {
        WebClient webClient = WebClient.builder().baseUrl(config.getExternalApiUrl()).build();
        StockDetails stockDetails = webClient.get().uri(uriBuilder -> uriBuilder
                .path("api/equity/{symbol}")
                .build(symbol))
                .retrieve()
                .bodyToMono(StockDetails.class)
                .block();

        if(stockDetails != null && stockDetails.getPriceInfo() != null) {
            return stockDetails.getPriceInfo().getLastPrice();
        }

        return 0.00;
    }

    public void deleteStock(int id) {
        Optional<Stock> stock = stockRepository.findById(id);
        stockRepository.deleteById(id);

        if(stock.isPresent()) {
            List<Stock> stocks = stockRepository.findByStockSymbol(stock.get().getStockSymbol());
            if (stocks.isEmpty()) {
                summaryRepository.deleteBySymbol(stock.get().getStockSymbol());
            } else {
                updateSummary(stocks);
            }
        }
    }

    public String updateStock(Stock stock) {
        try {
            stockRepository.save(stock);
            List<Stock> stocks = stockRepository.findByStockSymbol(stock.getStockSymbol());
            updateSummary(stocks);

        } catch(Exception exception) {
            throw new RuntimeException();
        }
        return "Stock updated successfully";
    }

    private void updateSummary(List<Stock> stocks, String ...action) {
        double investedAmount = 0.00;
        int totalQuantity = 0;
        Summary summary;

        for (Stock stock: stocks) {
            investedAmount = investedAmount + (stock.getPrice() * stock.getQuantity());
            totalQuantity = totalQuantity + stock.getQuantity();
        }

        Optional<Summary> s = summaryRepository.findBySymbol(stocks.get(0).getStockSymbol());
        summary = s.orElse(new Summary());

        if(action.length > 0 && s.isPresent()) {
            investedAmount += s.get().getInvestedAmount();
            totalQuantity += s.get().getQuantity();
        }

        summary.setName(stocks.get(0).getStockName());
        summary.setSymbol(stocks.get(0).getStockSymbol());
        summary.setInvestedAmount(investedAmount);
        summary.setQuantity(totalQuantity);

        summaryRepository.save(summary);
    }

    public void deleteAllStocks(int id) {
        stockRepository.deleteAllStocks(id);
        summaryRepository.deleteAllStocks(id);
    }
}
