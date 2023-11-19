package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicBoom extends Event {
    public EconomicBoom(String eventName) {
        super(eventName);
    }

    @Override
    public String executeEvent(Company company){
        //This method increases revenue by 10%
        company.increaseRevenue(10);
        return "ECONOMIC BOOM: The economy is trending upwards (Lucky You!). You have 10% increase in revenue!";
    }
}
