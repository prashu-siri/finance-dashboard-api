package com.self.financedashboard.model.marketTrends;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Trend {
    private String symbol;
    private String type;
    private String name;
    private double price;
    private double change;
    private double changePercent;
    private double previousClose;
    private String lastUpdateUtc;
    private String googleMid;
    private double preOrPostMarket;
    private double preOrPostMarketChange;
    private double preOrPostMarketChangePercent;
    private String currency;
    private String exchange;
    private String exchangeOpen;
    private String exchangeClose;
    private String timezone;
    private int utcOffsetSec;
    private String countryCode;
}
