package com.cbfacademy.apiassessment.FinTechClasses;

import java.util.UUID;

public class Game {
    private String gameId;
    private Company company;
    private String month;
    private String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}; //should month be an array of months that is cycled through but starting at january
    private int currentTurn;

    private boolean isGameCompleted;

    public Game(String gameId, Company company, String month, int currentTurn, boolean isGameCompleted){
        this.gameId = UUID.randomUUID().toString();
        this.company = new Company();
        this.currentTurn = 1;
        this.isGameCompleted = false;
        //this is just a placeholder for the data that I need to add to the game class
    }

    public void advanceTurn(){
        currentTurn++;
    }

    public void setMonth(){
        int index = (currentTurn - 1) % arrayOfMonths.length;
        this.month = arrayOfMonths[index];
    }




}
