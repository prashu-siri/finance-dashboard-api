package com.self.financedashboard.model.quote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class Quote {
    public String status;
    public String requestId;
    public JsonNode data;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<QuoteData> getListData() {
        if (data != null && data.isObject()) {
            QuoteData quoteData = objectMapper.convertValue(data, QuoteData.class);
            return List.of(quoteData);
        }
        if (data != null && data.isArray()) {
            return objectMapper.convertValue(data, new TypeReference<>() {
            });
        }
        return null;
    }
}
