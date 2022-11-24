package com.self.financedashboard.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public double pdSectorPe;
    @JsonIgnore
    public double pdSymbolPe;
    public String pdSectorInd;
}
