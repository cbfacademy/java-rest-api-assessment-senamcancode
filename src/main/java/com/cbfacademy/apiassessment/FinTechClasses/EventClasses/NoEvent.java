package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class NoEvent extends Event {
    @Override
    public void  executeEvent(Company company){
        //this event does nothingreturn false;
    }
}
