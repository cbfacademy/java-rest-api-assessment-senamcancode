package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    @DisplayName("Testing crowdfund limit of once per turn")
    public void testCrowdFundLimit() throws InvalidActionException {
        Game game = new Game();
        Company company = game.getCompany();

        company.crowdFund();

        assertThrows(InvalidActionException.class, company::crowdFund);
    }

    @Test
    @DisplayName("Testing that the game class is initialised with Jan as the initial month")
    public void testInitialMonth(){
        Game game = new Game();

        String initialMonth = game.getMonth();

        assertEquals( "Jan", initialMonth);

    }


    @Test
    @DisplayName("Testing setMonth method changes the currentMonth Game")
    public void testSetMonth(){
        Game game = new Game();

        game.advanceTurn();

        game.setMonth();

        String newMonth = game.getMonth();

        assertEquals( "Feb", newMonth);

    }

    @Test
    @DisplayName("Testing checkGameIsCompleted method returns a true isGameCompleted")
    public void testCheckGameIsCompleted(){
        Game game = new Game();
        game.getCompany().setRevenue(10000000);
        game.getCompany().setEmployees(50);
        game.getCompany().setDepartments(5);
        game.getCompany().setCustomerBase(10000);
        game.getCompany().setProductXP(100);

        game.checkGameIsCompleted();

        assertTrue(game.isGameCompleted());

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

}
