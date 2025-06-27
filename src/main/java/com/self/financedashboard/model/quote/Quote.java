package com.self.financedashboard.model.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    public String status;
    @JsonProperty("request_id")
    public String requestId;
    public List<QuoteData> data;
}
