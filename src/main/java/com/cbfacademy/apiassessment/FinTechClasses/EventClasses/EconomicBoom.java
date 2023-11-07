package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicBoom extends Event {
    @Override
    public void executeEvent(Company company){
        company.increaseRevenue(10);

    }
}
