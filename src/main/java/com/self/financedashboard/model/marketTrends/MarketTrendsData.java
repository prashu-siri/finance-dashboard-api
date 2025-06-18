package com.self.financedashboard.model.marketTrends;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketTrendsData {
    private ArrayList<Trend> trends;
    private ArrayList<News> news;
}
