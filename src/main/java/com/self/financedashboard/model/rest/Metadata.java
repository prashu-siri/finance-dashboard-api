package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Metadata {
    public String series;
    public String symbol;
    public String isin;
    public String status;
    public String listingDate;
    public String industry;
    public String lastUpdateTime;
    public double pdSectorPe = 0.00;
    public double pdSymbolPe = 0.00;
    public String pdSectorInd;
}
