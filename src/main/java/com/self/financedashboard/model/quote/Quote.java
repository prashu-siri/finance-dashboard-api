package com.self.financedashboard.model.quote;

import lombok.Data;

import java.util.List;

@Data
public class Quote {
    public String status;
    public String requestId;
    public List<QuoteData> data;
}
