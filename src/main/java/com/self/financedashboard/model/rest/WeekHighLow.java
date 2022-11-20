package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WeekHighLow{
    public double min;
    public String minDate;
    public double max;
    public String maxDate;
    public double value;
}
