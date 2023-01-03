package com.self.financedashboard.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SecurityInfo{
    public String boardStatus;
    public String tradingStatus;
    public String tradingSegment;
    public String sessionNo;
    public String slb;
    public String classOfShare;
    public String derivatives;
    public Surveillance surveillance;
    public int faceValue;
    public Long issuedSize;
}
