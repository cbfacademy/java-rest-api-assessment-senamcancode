package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicDownturn extends Event {
    public EconomicDownturn(String eventName) {
        super(eventName);
    }

    @Override
    public void executeEvent(Company company){
        company.reduceRevenue(15);

    }
}
