package com.cbfacademy.apiassessment.FinTechClasses;

import java.util.UUID;

public class Game {
    private String gameId = UUID.randomUUID().toString();;
    private Company company = new Company();
    private String month = "Jan";
    private final String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int currentTurn = 1;

    private boolean isGameCompleted = false;

    public String getGameId() {
        return gameId;
    }

    public Company getCompany() {
        return company;
    }

    public void setMonth(){
        int index = (currentTurn - 1) % arrayOfMonths.length;
        this.month = arrayOfMonths[index];
    }

    public String getMonth() {
        return month;
    }


    public int getCurrentTurn() {
        return currentTurn;
    }

    public void advanceTurn(){
        currentTurn++;
        company.resetCrowdFundCount();
    }

    public boolean isGameCompleted() {
        return isGameCompleted;
    }


}
