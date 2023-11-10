package com.cbfacademy.apiassessment.FinTechClasses;

import com.cbfacademy.apiassessment.FinTechClasses.EventClasses.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private String gameId;
    //public final LocalDateTime creationDateTime;
    private int currentTurn = 1;
    private final int maxTurnsPerGame = 20;
    private boolean isGameCompleted = false;
    private int currentNumberOfActions = 0;
    private int actionsPerTurn = 3;
    private Company company = new Company();
    private String month = "Jan";

    //NB - you can exclude this variable from the json using the key word transient

    //change to a calendar object to get the date
    private final String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public List<Event> listOfEvents = new ArrayList<>();



    public Game(){
        this.gameId = UUID.randomUUID().toString();
        //this.creationDateTime = LocalDateTime.now();
        listOfEvents.add(new NoEvent("No Event"));
        listOfEvents.add(new CybersecurityLeak("Cybersecurity Leak"));
        listOfEvents.add(new EconomicBoom("Economic Boom"));
        listOfEvents.add(new EconomicDownturn("Economic Downturn"));
        listOfEvents.add(new SocialMediaViral("Social media viral event"));
    }

    public String triggerRandomEvent(){
        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(listOfEvents.size());
        Event randomEvent = listOfEvents.get(randomIndex);
         randomEvent.executeEvent(company);
         return randomEvent.getEventName();
        //this will return the name of the triggered Event
    }

    public void resetActionsTaken(){

        currentNumberOfActions = 0;
    }

    public void addToCurrentNumberOfActions(){
        currentNumberOfActions ++;
    }

    public List<Event> getEvents() {

        return listOfEvents;
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
        setMonth();
        company.resetCrowdFundCount();
        triggerRandomEvent();
    }

    public boolean isGameCompleted() {
        return isGameCompleted;
    }


    public boolean isGameOver(){
        return currentTurn == maxTurnsPerGame;
    }


    //we need a method to limit the actions that the user can take
}
