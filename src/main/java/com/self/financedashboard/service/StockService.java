package com.self.financedashboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.financedashboard.enumeration.TickerSymbol;
import com.self.financedashboard.model.DashboardSummary;
import com.self.financedashboard.model.Intraday;
import com.self.financedashboard.model.Stock;
import com.self.financedashboard.model.Summary;
import com.self.financedashboard.model.Ticker;
import com.self.financedashboard.model.company.CompanyDetails;
import com.self.financedashboard.model.marketTrends.MarketTrends;
import com.self.financedashboard.model.marketTrends.TrendResponse;
import com.self.financedashboard.model.quote.Quote;
import com.self.financedashboard.model.quote.QuoteData;
import com.self.financedashboard.repository.StockRepository;
import com.self.financedashboard.repository.SummaryRepository;
import com.self.financedashboard.util.DashboardSummaryComparator;
import jakarta.transaction.Transactional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;
    private final SummaryRepository summaryRepository;
    private final WebClient webClient;
    private final static String EXCHANGE_NSE = ":NSE";

    @Value("${rapidapi.key}")
    String apiKey;

    @Value("${rapidapi.base-url}")
    String baseUrl;

    public StockService(StockRepository stockRepository, SummaryRepository summaryRepository, WebClient webClient) {
        this.stockRepository = stockRepository;
        this.summaryRepository = summaryRepository;
        this.webClient = webClient;
    }

    public Quote fetchAllStockDetailsOkHttp(String symbols) {
        ObjectMapper mapper = new ObjectMapper();
        String encodedSymbolsParamValue;
        encodedSymbolsParamValue = URLEncoder.encode(symbols, StandardCharsets.UTF_8);

        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/stock-quote")
                .queryParam("symbol", encodedSymbolsParamValue)
                .queryParam("language", "en")
                .build()
                .toUriString();

        Request request = new Request.Builder()
                .url(uri)
                .get()
                .addHeader("x-rapidapi-key", apiKey)
                .addHeader("x-rapidapi-host", "real-time-finance-data.p.rapidapi.com")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            if(response.body() != null) {
                String responseBody = response.body().string();
                return mapper.readValue(responseBody, Quote.class);
            }
        } catch (IOException e) {
            System.err.println("Error during API call with OkHttp: " + e.getMessage());
            throw new RuntimeException("API call failed with OkHttp", e);
        }

        return null;
    }

    public TrendResponse getMarketTrends() {
        MarketTrends topGainers = webClient.get()
                .uri("/market-trends?trend_type=GAINERS&country=in")
                .retrieve()
                .bodyToMono(MarketTrends.class)
                .block();

        MarketTrends topLosers = webClient.get()
                .uri("/market-trends?trend_type=LOSERS&country=in")
                .retrieve()
                .bodyToMono(MarketTrends.class)
                .block();

        TrendResponse trendResponse = new TrendResponse();
        trendResponse.setTopGainers(topGainers);
        trendResponse.setTopLosers(topLosers);

        return trendResponse;
    }

    public void addStock(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
        updateSummary(stocks, "add");
    }

    public Map<String, List<Stock>> getStocks(int userId) {
        List<Stock> computedStocks = new ArrayList<>();
        Map<String, List<Stock>> map = new HashMap<>();
        List<Stock> stocks = stockRepository.findUserStocks(userId);

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
            ticker.setLogo(symbol.getLogo());

            tickers.add(ticker);
        }

        return tickers;
    }

    public List<DashboardSummary> getUserStocks(int userId) {
        List<DashboardSummary> dashboardSummaryList = new ArrayList<>();
        List<Summary> summaryList = new ArrayList<>(summaryRepository.findAllUserStocks(userId));

        logger.info("StockService :: getUserStocks :: summaryList size :: {}", summaryList.size());

        if(!summaryList.isEmpty()) {
            Map<String, Double> stockCurrentPrice = getStockCurrentPrice(summaryList);

            for (Summary summary: summaryList) {
                double currentPrice = 0.00;
                if(stockCurrentPrice.containsKey(summary.getSymbol() + EXCHANGE_NSE)) {
                    currentPrice = stockCurrentPrice.get(summary.getSymbol() + EXCHANGE_NSE);
                }

                logger.info("StockService :: getUserStocks :: {} current price is {}", summary.getSymbol(), currentPrice);

                DashboardSummary dashboardSummary = getDashboardSummary(summary, currentPrice);

                dashboardSummaryList.add(dashboardSummary);
            }

            dashboardSummaryList.sort(new DashboardSummaryComparator());

        }

        return dashboardSummaryList;
    }

    private DashboardSummary getDashboardSummary(Summary summary, double currentPrice) {
        double totalCurrentPrice = currentPrice * summary.getQuantity();

        DashboardSummary dashboardSummary = new DashboardSummary();
        dashboardSummary.setId(summary.getId());
        dashboardSummary.setName(summary.getName());
        dashboardSummary.setQuantity(summary.getQuantity());
        dashboardSummary.setSymbol(summary.getSymbol());
        dashboardSummary.setLogo(TickerSymbol.getLogoBySymbol(summary.getSymbol()));
        dashboardSummary.setInvestedAmount(summary.getInvestedAmount());
        dashboardSummary.setHoldings(totalCurrentPrice - summary.getInvestedAmount());
        dashboardSummary.setCurrentPrice(currentPrice);
        dashboardSummary.setTotalCurrentAmount(totalCurrentPrice);
        return dashboardSummary;
    }


    /**
     * Create a comma separated string of ticker symbols and gets the stock values, eg: TCS:NSE,INFY:NSE
     * Create a map of ticker against the stock value
     * @param summaryList Contains user stocks
     * @return map
     */
    private Map<String, Double> getStockCurrentPrice(List<Summary> summaryList) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < summaryList.size(); i++) {
            Summary summary = summaryList.get(i);
            if(i != summaryList.size() - 1) {
                sb.append(summary.getSymbol());
                sb.append(EXCHANGE_NSE);
                sb.append(",");
            } else {
                sb.append(summary.getSymbol());
                sb.append(EXCHANGE_NSE);
            }
        }

        Quote stockQuote = fetchAllStockDetailsOkHttp(sb.toString());

        Map<String, Double> stockPrice = new HashMap<>();

        for(QuoteData quote: stockQuote.getData()) {
            logger.info("Current price for {} is {} ", quote.getSymbol(), quote.getPrice());
            stockPrice.put(quote.getSymbol(), quote.getPrice());
        }

       return stockPrice;
    }

    public void deleteStock(Map<String, Object> details) {
        int id = (int) details.get("id");
        int userId = (int) details.get("userId");
        Optional<Stock> stock = stockRepository.findById(id);
        stockRepository.deleteById(id);

        if(stock.isPresent()) {
            List<Stock> stocks = stockRepository.findByStockSymbolAndUserId(stock.get().getStockSymbol(), userId);
            if (stocks.isEmpty()) {
                summaryRepository.deleteBySymbolAndUserId(stock.get().getStockSymbol(), userId);
            } else {
                updateSummary(stocks);
            }
        }
    }

    public String updateStock(Stock stock) {
        try {
            stockRepository.save(stock);
            List<Stock> stocks = stockRepository.findByStockSymbolAndUserId(stock.getStockSymbol(), stock.getUserId());
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

        Optional<Summary> s = summaryRepository.findBySymbolAndUserId(stocks.get(0).getStockSymbol(), stocks.get(0).getUserId());
        summary = s.orElse(new Summary());

        if(action.length > 0 && s.isPresent()) {
            investedAmount += s.get().getInvestedAmount();
            totalQuantity += s.get().getQuantity();
        }

        summary.setName(stocks.get(0).getStockName());
        summary.setSymbol(stocks.get(0).getStockSymbol());
        summary.setInvestedAmount(investedAmount);
        summary.setQuantity(totalQuantity);
        summary.setUserId(stocks.get(0).getUserId());

        summaryRepository.save(summary);
    }

    public void deleteAllStocks(int id) {
        stockRepository.deleteAllStocks(id);
        summaryRepository.deleteAllStocks(id);
    }

    public Intraday getIntraday(String symbol) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("api/equity/intraday/{symbol}")
                .build(symbol))
                .retrieve()
                .bodyToMono(Intraday.class)
                .block();
    }

    public CompanyDetails getCompanyDetails(String symbol) {
        CompanyDetails companyDetails = webClient.get()
                .uri("/stock-overview?symbol=" + symbol)
                .retrieve()
                .bodyToMono(CompanyDetails.class)
                .block();

        if(companyDetails != null) {
            String companySymbol = companyDetails.getData().getSymbol().split(":")[0];
            companyDetails.setLogo(TickerSymbol.getLogoBySymbol(companySymbol));
        }

        return companyDetails;
    }
}
