package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class SocialMediaViral extends Event {
    public SocialMediaViral(String eventName) {
        super(eventName);
    }

    @Override
    public String executeEvent(Company company){
        //Increases customer base by 1500 and increases revenue by 2%
        company.increaseCustomerBase(1500);
        company.increaseRevenue(2);
        return "SOCIAL MEDIA VIRAL: Your company has gone social media viral (for the right reasons)! You now have 1500 more customers and an increase in revenue of 2%!" ;

    }
}
