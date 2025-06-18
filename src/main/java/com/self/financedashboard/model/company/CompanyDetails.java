package com.self.financedashboard.model.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetails {
    private String status;
    private String requestId;
    private CompanyDetailsData data;
    private String logo;
}
