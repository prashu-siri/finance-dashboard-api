package com.self.financedashboard.model.rest.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Meta {
    private String symbol;
    private String companyName;
    private String industry;
    List<String> activeSeries;
    List<String> debtSeries;
    List<String> tempSuspendedSeries;
    private boolean isFNOSec;
    private boolean isCASec;
    private boolean isSLBSec;
    private boolean isDebtSec;
    private boolean isSuspended;
    private boolean isETFSec;
    private boolean isDelisted;
    private String isin;
}
