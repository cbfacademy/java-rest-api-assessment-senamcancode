package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    @DisplayName("Testing crowdfund limit of once per turn")
    public void testCrowdFundLimit(){
        Game game = new Game();
        Company company = game.getCompany();

        int initialCount = company.getCrowdFundCount();

        company.crowdFund();

        int countAfterCrowdFund = company.getCrowdFundCount();

        company.crowdFund();

        int countAfterCrowdFundTwo = company.getCrowdFundCount();

        assertEquals( initialCount + 1, countAfterCrowdFund);
        assertEquals(countAfterCrowdFund, countAfterCrowdFundTwo);
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
