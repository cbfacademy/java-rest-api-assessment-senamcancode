package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    //For the GameTest I tested relevant game methods (ie not all methods like the Company test class)


    @Test
    @DisplayName("Testing that the game class is initialised with Jan as the initial month")
    public void testInitialMonth() {
        Game game = new Game();

        String initialMonth = game.getMonth();

        assertEquals("Jan", initialMonth);

    }


    @Test
    @DisplayName("Testing setMonth method changes the currentMonth Game")
    public void testSetMonth() {
        Game game = new Game();

        game.advanceTurn();

        game.setMonth();

        String newMonth = game.getMonth();

        assertEquals("Feb", newMonth);

    }


    @Test
    @DisplayName("Testing the actionsManager limits the number of actions taken per turn to 3")
    public void actionsManager() throws InvalidActionException {
        Game game = new Game();

        int initActions = game.getCurrentNumberOfActions();
        game.actionsManager();
        game.actionsManager();
        game.actionsManager();

        int newActions = game.getCurrentNumberOfActions();

        assertEquals(initActions + 3, newActions);
    }

    @Test
    @DisplayName("Testing the actionsManager limits the number of actions taken per turn to 3")
    public void actionsManagerWithException() throws InvalidActionException {
        Game game = new Game();

        game.actionsManager(); //the actions is incremented from 0 to 1
        game.actionsManager(); //the actions is incremented from 1 to 2
        game.actionsManager(); //the actions is incremented from 2 to 3
        game.actionsManager(); //the actions is incremented from 3 to 4


        assertThrows(InvalidActionException.class, game::actionsManager);
    }

    @Test
    @DisplayName("Testing crowdfund limit of once per turn")
    public void testCrowdFundLimit() throws InvalidActionException {
        Game game = new Game();
        Company company = game.getCompany();

        company.crowdFund();

        assertThrows(InvalidActionException.class, company::crowdFund);
    }

    //Need to test the advanceTurn method

    //Need to test teh actionsRemaining

    //Need to test the resetCurrentNumberOfActions

    //Need to test isGameOver

    @Test
    @DisplayName("Testing checkGameIsCompleted method returns a true isGameCompleted")
    public void testCheckGameIsCompleted() {
        Game game = new Game();
        game.getCompany().setRevenue(5000000);
        game.getCompany().setEmployees(30);
        game.getCompany().setDepartments(3);
        game.getCompany().setCustomerBase(10000);
        game.getCompany().setProductXP(30);

        game.checkGameIsCompleted();

        assertTrue(game.isGameCompleted());

    }
}

    //need to find a way to test that the thing returned is a string
//    @Test
//    @DisplayName("Testing that the trigger random event returns a string")
//    public void testRandomEventTriggerMethod(){
//        Game game = new Game();
//        String event = game.triggerRandomEvent();
//
//        assertEquals("No Event", event);
//    }


