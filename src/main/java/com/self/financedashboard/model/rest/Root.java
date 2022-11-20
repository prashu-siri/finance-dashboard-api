package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Root{
    public Info info;
    public Metadata metadata;
    public SecurityInfo securityInfo;
    public PriceInfo priceInfo;
    public IndustryInfo industryInfo;
    public PreOpenMarket preOpenMarket;
}
