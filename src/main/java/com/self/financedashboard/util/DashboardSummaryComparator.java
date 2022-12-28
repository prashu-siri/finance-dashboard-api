package com.self.financedashboard.util;

import com.self.financedashboard.model.DashboardSummary;

import java.util.Comparator;

public class DashboardSummaryComparator implements Comparator<DashboardSummary> {
    @Override
    public int compare(DashboardSummary o1, DashboardSummary o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
