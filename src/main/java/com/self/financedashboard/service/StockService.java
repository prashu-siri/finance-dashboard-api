package com.self.financedashboard.service;

import com.self.financedashboard.enumeration.TickerSymbol;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.StockDetails;
import com.self.financedashboard.model.Summary;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.repository.StockRepository;
import com.self.financedashboard.repository.SummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final SummaryRepository summaryRepository;

    public StockService(StockRepository stockRepository, SummaryRepository summaryRepository) {
        this.stockRepository = stockRepository;
        this.summaryRepository = summaryRepository;
    }

    public void addStock(List<Stock> stocks) {
        double investedAmount = 0.00;
        int totalQuantity = 0;
        for (Stock stock: stocks) {
            investedAmount = investedAmount + (stock.getPrice() * stock.getQuantity());
            totalQuantity = totalQuantity + stock.getQuantity();
            stockRepository.save(stock);
        }

        Summary summary = new Summary();
        summary.setName(stocks.get(0).getStockName());
        summary.setSymbol(stocks.get(0).getStockSymbol());
        summary.setInvestedAmount(investedAmount);
        summary.setQuantity(totalQuantity);

        summaryRepository.save(summary);
    }

    public List<Stock> getStocks() {
        return (List<Stock>) stockRepository.findAll();
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

        return dashboardSummaryList;
    }

    private double getStockCurrentPrice(String symbol) {
        WebClient webClient = WebClient.builder().baseUrl("https://stock-nse-india.herokuapp.com/").build();
        StockDetails stockDetails = webClient.get().uri(uriBuilder -> uriBuilder
                .path("api/equity/{symbol}")
                .build(symbol))
                .retrieve()
                .bodyToMono(StockDetails.class)
                .block();

        if(stockDetails != null) {
            return stockDetails.getPriceInfo().getLastPrice();
        }

        return 0.00;
    }
}
