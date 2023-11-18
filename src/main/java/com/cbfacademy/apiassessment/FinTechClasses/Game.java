package com.cbfacademy.apiassessment.FinTechClasses;

import com.cbfacademy.apiassessment.FinTechClasses.EventClasses.*;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Game {
    private String gameId;
    private final Date dateCreated = new Date();
    private String month = "Jan";
    private int currentTurn = 1;
    private final int maxTurnsPerGame = 20;
    private boolean isGameCompleted = false;
    private boolean isGameOver = false;
    private int currentNumberOfActions = 0;
    private int actionsPerTurn = 3;
    private Company company = new Company();

    private final String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public List<Event> listOfEvents = new ArrayList<>();



    public Game(){
        this.gameId = UUID.randomUUID().toString();
        listOfEvents.add(new NoEvent("No Event"));
        listOfEvents.add(new NoEvent("No Event"));
        listOfEvents.add(new CybersecurityLeak("Cybersecurity Leak"));
        listOfEvents.add(new EconomicBoom("Economic Boom"));
        listOfEvents.add(new EconomicDownturn("Economic Downturn"));
        listOfEvents.add(new SocialMediaViral("Social media viral event"));
    }

    public void setMonth(){
        int index = (currentTurn - 1) % arrayOfMonths.length;
        this.month = arrayOfMonths[index];
    }


    public String triggerRandomEvent(){
        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(listOfEvents.size());
        Event randomEvent = listOfEvents.get(randomIndex);
        return randomEvent.executeEvent(company);
    }


    public void actionIncrement(){
        currentNumberOfActions++;

    }
    public boolean checkGameIsCompleted(){
        if(company.getRevenue() >= 5000000 &&
                company.getDepartments() >= 3
                && company.getEmployees() >= 30
                && company.getCustomerBase() >= 10000
                && company.getProductXP() >= 30
        ){
            return isGameCompleted = true;
        }

        return isGameCompleted = false;
    }

    public boolean checkGameIsOver(){
        if(currentTurn >= maxTurnsPerGame && !checkGameIsCompleted()){
            return isGameOver = true;

        }
        return false;
    }

    public void resetCurrentNumberOfActions(){
        currentNumberOfActions = 0;
    }

    public boolean invalidAction(){
        if(actionsPerTurn > currentNumberOfActions){
            return true;
        } else if(actionsPerTurn <= currentNumberOfActions){
            return false;
        }
        return false;
    }

    public int actionsRemaining(){
        return actionsPerTurn - currentNumberOfActions;
    }

    public void advanceTurn(){
        resetCurrentNumberOfActions();
        company.resetCrowdFundCount();
        company.resetInvestCount();
        company.customerRevenueBoost();

        checkGameIsOver();
        checkGameIsCompleted();

        currentTurn++;
        setMonth();
    }

    //getters and setters
    public List<Event> getEvents() {
        return listOfEvents;
    }

    public String getGameId() {
        return gameId;
    }


    public Company getCompany() {
        return company;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public int getCurrentNumberOfActions(){
        return currentNumberOfActions;
    }

    public String getMonth() {
        return month;
    }

    public int getMaxTurnsPerGame() {
        return maxTurnsPerGame;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }


    public int getActionsPerTurn() {
        return actionsPerTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setGameCompleted(boolean gameCompleted) {
        isGameCompleted = gameCompleted;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setCurrentNumberOfActions(int currentNumberOfActions) {
        this.currentNumberOfActions = currentNumberOfActions;
    }

    public void setActionsPerTurn(int actionsPerTurn) {
        this.actionsPerTurn = actionsPerTurn;
    }

    public boolean isGameCompleted() {

        return isGameCompleted;
    }

    public void setGameIsOverToTrue(){
        isGameOver = true;
    }

    public void setGameIsCompletedToTrue(){
        isGameCompleted = true;
    }


}
