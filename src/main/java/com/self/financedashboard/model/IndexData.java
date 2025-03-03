package com.self.financedashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexData {
    public String key;
    public String index;
    public String indexSymbol;
    public double last;
    public double variation;
    public double percentChange;
    @JsonProperty("open")
    public double myopen;
    public double high;
    public double low;
    public double previousClose;
    public double yearHigh;
    public double yearLow;
    public int indicativeClose;
    public String pe;
    public String pb;
    public String dy;
    public String declines;
    public String advances;
    public String unchanged;
    public Object perChange365d;
    public String date365dAgo;
    public String chart365dPath;
    public String date30dAgo;
    public double perChange30d;
    public String chart30dPath;
    public String chartTodayPath;
    public double previousDay;
    public double oneWeekAgo;
    public double oneMonthAgo;
    public double oneYearAgo;
}
