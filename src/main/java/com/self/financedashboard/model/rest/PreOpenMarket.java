package com.self.financedashboard.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PreOpenMarket{
    public ArrayList<Preopen> preopen;
    public Ato ato;
    @JsonProperty("IEP")
    public double iEP;
    public int totalTradedVolume;
    public double finalPrice;
    public int finalQuantity;
    public String lastUpdateTime;
    public int totalBuyQuantity;
    public int totalSellQuantity;
    public int atoBuyQty;
    public int atoSellQty;
}
