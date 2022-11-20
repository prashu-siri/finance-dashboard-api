package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Preopen{
    public double price;
    public int buyQty;
    public int sellQty;
    public boolean iep;
}
