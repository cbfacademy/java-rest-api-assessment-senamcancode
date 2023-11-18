package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game;
    //For the GameTest Class I tested relevant game methods (ie not the getters and setters like the Company test class)


    @BeforeEach
    public void setUp(){
        this.game = new Game();
    }

    @Test
    @DisplayName("Testing that the game class is initialised with Jan as the initial month")
    public void testInitialMonth() {
        String initialMonth = game.getMonth();

        assertEquals("Jan", initialMonth);

    }

    @Test
    @DisplayName("Testing setMonth method changes the currentMonth Game")
    public void testSetMonth() {
        game.advanceTurn();
        game.setMonth();
        String newMonth = game.getMonth();

        assertEquals("Feb", newMonth);

    }


    @Test
    @DisplayName("Testing that the trigger random event returns a string (eventName)")
    public void testTriggerRandomEventMethod(){
        String event = game.triggerRandomEvent();

        assertTrue(event instanceof String);
    }



    @Test
    @DisplayName("Testing the actionIncrement increases the number of actions taken")
    public void actionsIncrementIncreasesCurrentNumberOfActions() {
        int initNumOfActions = game.getCurrentNumberOfActions();

        game.actionIncrement(); //the number of actions taken is incremented from 0 to 1
        game.actionIncrement(); // from 1 to 2
        game.actionIncrement(); // 2 to 3
        game.actionIncrement(); //from 3 to 4

        int newNumOfActions = game.getCurrentNumberOfActions();

        assertEquals(initNumOfActions + 4, newNumOfActions);

    }


    @Test
    @DisplayName("Testing checkGameIsCompleted method returns a true isGameCompleted")
    public void testCheckGameIsCompleted() {
        game.getCompany().setRevenue(5000000);
        game.getCompany().setEmployees(30);
        game.getCompany().setDepartments(3);
        game.getCompany().setCustomerBase(10000);
        game.getCompany().setProductXP(30);

        game.checkGameIsCompleted();

        assertTrue(game.isGameCompleted());

    }

    @Test
    @DisplayName("Testing the checkGameIsOver method sets isGameOver to true")
    public void testCheckGameIsOver(){
        game.setCurrentTurn(20);
        game.setGameCompleted(false);

        boolean result = game.checkGameIsOver();

        assertTrue(result);
    }

    @Test
    @DisplayName("Testing resetCurrentNumberOfActions resets currentNumberOfActions to 0")
    public void testResetCurrentNumberOfActions(){
        int initNumOfActions = game.getCurrentNumberOfActions();

        game.actionIncrement();
        game.resetCurrentNumberOfActions();

        int newNumOfActions = game.getCurrentNumberOfActions();

        assertEquals(initNumOfActions, newNumOfActions);
    }

    @Test
    @DisplayName("Testing invalidAction returns true if actionsPerTurn is more than currentNumberOfActions")
    public void testInvalidActionReturnsTrue(){
       game.setCurrentNumberOfActions(1);
       boolean result = game.invalidAction();

       assertTrue(result);
    }

    @Test
    @DisplayName("Testing invalidAction returns false if actionsPerTurn is less than or equal to currentNumberOfActions")
    public void testInvalidActionReturnsFalse(){
       game.setCurrentNumberOfActions(4);
       boolean result = game.invalidAction();

       assertFalse(result);
    }

    @Test
    @DisplayName("Testing actionsRemaining returns correct number of actions")
    public void testActionsRemaining(){
        game.setActionsPerTurn(2);
        game.setCurrentNumberOfActions(1);
        int result = game.actionsRemaining();

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Testing advanceTurn sets the month, resets the current number of actions to 0, resets the crowd fund count to 0, resets the invest count to 0, increases revenue based on number of customers and increments the current turn ")
    public void testAdvanceTurn(){
        double initRevenue = game.getCompany().getRevenue();

        game.getCompany().incrementCrowdFundCount();
        game.getCompany().incrementInvestCount();
        game.getCompany().setCustomerBase(10);
        game.setCurrentNumberOfActions(2);
        int initCurrentTurn = game.getCurrentTurn();

        game.advanceTurn();

        String newMonth = game.getMonth();
        int crowdFundCount = game.getCompany().getCrowdFundCount();
        int investCount = game.getCompany().getInvestCount();
        double newRevenue = game.getCompany().getRevenue();
        int newCurrentNumberOfActions = game.getCurrentNumberOfActions();
        int newCurrentTurn = game.getCurrentTurn();

        assertEquals(0,newCurrentNumberOfActions);
        assertEquals(0, crowdFundCount);
        assertEquals(0, investCount);
        assertEquals(initRevenue + 50,newRevenue);
        assertEquals(initCurrentTurn + 1, newCurrentTurn);
        assertEquals("Feb", newMonth);

    }

}



