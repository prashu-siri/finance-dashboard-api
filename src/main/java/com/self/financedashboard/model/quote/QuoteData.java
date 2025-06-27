package com.self.financedashboard.model.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteData {
    public String symbol;
    public String name;
    public String type;
    public double price;
    public double open;
    public double high;
    public double low;
    public int volume;
    @JsonProperty("previous_close")
    public double previousClose;
    public double change;
    @JsonProperty("change_percent")
    public double changePercent;
    @JsonProperty("pre_or_post_market")
    public Double preOrPostMarket;
    @JsonProperty("pre_or_post_market_change")
    public Double preOrPostMarketChange;
    @JsonProperty("pre_or_post_market_change_percent")
    public Double preOrPostMarketPercentage;
    @JsonProperty("last_update_utc")
    public String lastUpdateUtc;
}
