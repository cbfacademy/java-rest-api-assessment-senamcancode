package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicBoom extends Event {
    public EconomicBoom(String eventName) {
        super(eventName);
    }

    @Override
    public String executeEvent(Company company){
        company.increaseRevenue(10);
        return "ECONOMIC BOOM: The economy is trending upwards (Lucky You!). You have 10% increase in revenue"; //i want these to have personality
    }
}
