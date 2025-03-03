package com.self.financedashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dates {
    public String previousDay;
    public String oneWeekAgo;
    public String oneMonthAgo;
    public String oneYearAgo;
}
