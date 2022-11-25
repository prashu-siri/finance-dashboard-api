package com.self.financedashboard.model.rest.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GainerLosers {
    public int priority;
    public String symbol;
    public String identifier;
    public String series;
    public double open;
    public double dayHigh;
    public double dayLow;
    public double lastPrice;
    public double previousClose;
    public double change;
    public double pChange;
    public int totalTradedVolume;
    public double totalTradedValue;
    public String lastUpdateTime;
    public double yearHigh;
    public double ffmc;
    public double yearLow;
    public double nearWKH;
    public double nearWKL;
    public double perChange365d;
    public String date365dAgo;
    public String chart365dPath;
    public String date30dAgo;
    public double perChange30d;
    public String chart30dPath;
    public String chartTodayPath;
    public Meta meta;
}
