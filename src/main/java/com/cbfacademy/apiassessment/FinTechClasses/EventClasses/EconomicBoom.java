package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicBoom extends Event {
    public EconomicBoom(String eventName) {
        super(eventName);
    }

    @Override
    public void executeEvent(Company company){
        company.increaseRevenue(10);

    }
}
