package com.self.financedashboard.model.quote;

import lombok.Data;

@Data
public class Quote {
    public String status;
    public String requestId;
    public QuoteData data;
}
