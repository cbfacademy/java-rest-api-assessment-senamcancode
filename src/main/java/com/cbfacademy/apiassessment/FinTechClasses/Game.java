package com.cbfacademy.apiassessment.FinTechClasses;

import com.cbfacademy.apiassessment.FinTechClasses.EventClasses.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private String gameId;
    //public final LocalDateTime creationDateTime;
    private int currentTurn = 1;
    private boolean isGameCompleted = false;
    private Company company = new Company();
    private String month = "Jan";

    //NB - you can exclude this variable from the json using the key word transient
    private final String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private List<Event> event = new ArrayList<>();



    public Game(){
        this.gameId = UUID.randomUUID().toString();
        //this.creationDateTime = LocalDateTime.now();
        event.add(new NoEvent());
        event.add(new CybersecurityLeak());
        event.add(new EconomicBoom());
        event.add(new EconomicDownturn());
        event.add(new SocialMediaViral());
    }

    public void triggerRandomEvent(){
        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(event.size());
        Event randomEvent = event.get(randomIndex);
        randomEvent.executeEvent(company);
    }

    public List<Event> getEvents() {
        return event;
    }

    public String getGameId() {
        return gameId;
    }

//    public LocalDateTime getCreationDateTime(){
//        return creationDateTime;
//    }
    public Company getCompany() {
        return company;
    }

    public void setMonth(){
        int index = (currentTurn - 1) % arrayOfMonths.length;
        this.month = arrayOfMonths[index];
    }

//    private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException, IOException {
//        in.defaultReadObject();
//        setMonth(); // Manually update month after deserialisation - this is done so that the month will still be altered but the arrayof months isnt present in the json file
//    }
    public String getMonth() {
        return month;
    }


    public int getCurrentTurn() {
        return currentTurn;
    }

    public void advanceTurn(){
        currentTurn++;
        company.resetCrowdFundCount();
        triggerRandomEvent();
    }

    public boolean isGameCompleted() {
        return isGameCompleted;
    }


}
