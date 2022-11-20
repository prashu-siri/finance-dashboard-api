package com.self.financedashboard.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PriceInfo {
    public double lastPrice;
    public double change;
    public double pChange;
    public double previousClose;
    @JsonProperty("open")
    public double myopen;
    public double close;
    public double vwap;
    public String lowerCP;
    public String upperCP;
    public String pPriceBand;
    public double basePrice;
    public IntraDayHighLow intraDayHighLow;
    public WeekHighLow weekHighLow;
}
