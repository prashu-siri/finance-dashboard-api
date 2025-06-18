package com.self.financedashboard.model.quote;

import lombok.Data;

@Data
public class QuoteData {
    public String symbol;
    public String name;
    public String type;
    public double price;
    public int open;
    public double high;
    public int low;
    public int volume;
    public double previousClose;
    public double change;
    public double changePercent;
    public Object preOrPostMarket;
    public Object preOrPostMarketChange;
    public Object preOrPostMarketPercentage;
    public String lastUpdateUtc;
}
