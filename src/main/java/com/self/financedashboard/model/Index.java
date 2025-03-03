package com.self.financedashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Index {
    public ArrayList<IndexData> data;
    public String timestamp;
    public int advances;
    public int declines;
    public int unchanged;
    public Dates dates;
    public String date30dAgo;
    public String date365dAgo;
}
