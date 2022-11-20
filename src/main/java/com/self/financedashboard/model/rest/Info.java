package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Info {
    public String symbol;
    public String companyName;
    public String industry;
    public ArrayList<String> activeSeries;
    public ArrayList<Object> debtSeries;
    public ArrayList<Object> tempSuspendedSeries;
    public boolean isFNOSec;
    public boolean isCASec;
    public boolean isSLBSec;
    public boolean isDebtSec;
    public boolean isSuspended;
    public boolean isETFSec;
    public boolean isDelisted;
    public String isin;
    public boolean isTop10;
    public String identifier;
}
