package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class CybersecurityLeak extends Event {
    @Override
    public void executeEvent(Company company){
    //reduce the company revenue by 5% and customer base by 500 unless they have less than 500 customers - in which case the customer base is reset to 0
        company.reduceRevenue(5);
        company.reduceCustomerBase(500);

    }
}
