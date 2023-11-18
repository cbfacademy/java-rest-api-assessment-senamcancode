package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class EconomicDownturn extends Event {
    public EconomicDownturn(String eventName) {
        super(eventName);
    }

    @Override
    public String executeEvent(Company company){
        company.reduceRevenue(15);
        return "ECONOMIC DOWNTURN!! The economy is trending downwards (Isn't it always). Your revenue has taken a 15% hit!"; //i want these to have personality

    }
}
