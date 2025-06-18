package com.self.financedashboard.model.marketTrends;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrendResponse {
    MarketTrends topGainers;
    MarketTrends topLosers;
}
