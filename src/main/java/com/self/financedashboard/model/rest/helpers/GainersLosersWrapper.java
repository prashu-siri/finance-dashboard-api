package com.self.financedashboard.model.rest.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GainersLosersWrapper {
        public ArrayList<GainerLosers> gainers;
        public ArrayList<GainerLosers> losers;
}
