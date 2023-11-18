package com.cbfacademy.apiassessment.FinTechClasses;

public abstract class Event {
    private String eventName;
    public Event(String eventName){
        this.eventName = eventName;
    }

    public String getEventName(){
        return eventName;
    }

    public abstract String executeEvent(Company company);
}
