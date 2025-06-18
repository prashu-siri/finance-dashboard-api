package com.self.financedashboard.model.marketTrends;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class News{
    private String articleTitle;
    private String articleUrl;
    private String articlePhotoUrl;
    private String source;
    private String postTimeUtc;
    private ArrayList<Trend> stocksInNews;
}
