package com.self.financedashboard.model;

import com.self.financedashboard.model.rest.IndustryInfo;
import com.self.financedashboard.model.rest.Info;
import com.self.financedashboard.model.rest.Metadata;
import com.self.financedashboard.model.rest.PreOpenMarket;
import com.self.financedashboard.model.rest.PriceInfo;
import com.self.financedashboard.model.rest.SecurityInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StockDetails {
    private Info info;
    private Metadata metadata;
    private SecurityInfo securityInfo;
    private PriceInfo priceInfo;
    private IndustryInfo industryInfo;
    private PreOpenMarket preOpenMarket;
    private String logo;
}
