package com.self.financedashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DashboardSummary {
    private int id;
    private String name;
    private String symbol;
    private int quantity;
    private double investedAmount;
    private double currentPrice;
    private double holdings;
    private double totalCurrentAmount;
    private String logo;
}
