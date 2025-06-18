package com.self.financedashboard.model.company;

import lombok.Data;

@Data
public class CompanyDetailsData {
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
    public double preOrPostMarket;
    public double preOrPostMarketChange;
    public double preOrPostMarketChangePercent;
    public String lastUpdateUtc;
    public String countryCode;
    public String exchange;
    public String exchangeOpen;
    public String exchangeClose;
    public String timezone;
    public int utcOffsetSec;
    public String currency;
    public String about;
    public int yearLow;
    public double yearHigh;
    public String primaryExchange;
    public String companyWebsite;
    public String companyCountryCode;
    public String companyCountry;
    public String companyState;
    public String companyCity;
    public String companyStreetAddress;
    public String companyCeo;
    public int companyEmployees;
    public String companyFoundedDate;
    public int avgVolume;
    public double companyPeRatio;
    public double companyMarketCap;
    public double companyDividendYield;
    public String wikipediaUrl;
    public String googleMid;
}
