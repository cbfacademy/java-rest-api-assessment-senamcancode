package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class CybersecurityLeak extends Event {
    public CybersecurityLeak(String eventName) {
        super(eventName);
    }

    @Override
    public String executeEvent(Company company){
    //reduce the company revenue by 5% and customer base by 500 unless they have less than 500 customers - in which case the customer base is reset to 0
        company.reduceRevenue(5);
        company.reduceCustomerBase(500);
        //should change the customerBase reduction to 1000



        return "CYBER SECURITY LEAK!! Your company has taken a hit! You have lost 5% revenue and 500 customers!";

    }
}
