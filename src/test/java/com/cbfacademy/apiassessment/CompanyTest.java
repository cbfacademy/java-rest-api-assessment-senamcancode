package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CompanyTest {
    //need to find a way to test the investment method - the problem is that the method provides random results

    @Test
    @DisplayName("Testing crowdFund method increases revenue by 100000 in Company ")
    public void testCrowdFund(){
        Game game = new Game();
        Company company = game.getCompany();
        double initialRevenue = company.getRevenue();

        company.crowdFund();

        double newRevenue = company.getRevenue();
        assertEquals(initialRevenue + 100000, newRevenue);

    }


    @Test
    @DisplayName("Testing insufficient funds")
    public void testInsufficientFunds(){
        Game game = new Game();
        Company company = game.getCompany();
        boolean result = company.hasSufficientFunds(1000);

        assertFalse(result);

    }

    @Test
    @DisplayName("Testing method increases employees in Company ")
    public void testAddEmployee(){
        Game game = new Game();
        Company company = game.getCompany();
        double initialEmployee = company.getEmployees();

        company.addEmployee(1);

        double newEmployeeNum = company.getEmployees();
        assertEquals(initialEmployee + 1, newEmployeeNum);

    }

    @Test
    @DisplayName("Testing method increases departments in copmany ")
    public void testAddDepartment(){
        Game game = new Game();
        Company company = game.getCompany();
        int initDepartments = company.getDepartments();

        company.addEmployee(10);

        company.addDepartment();
        int newDepartments = company.getDepartments();

        assertEquals(initDepartments + 1, newDepartments);

    }

    @Test
    @DisplayName("Testing researchAndDev method increases in productXP ")
    public void testResearchAndDev(){
        Game game = new Game();
        Company company = game.getCompany();
        int initProductXP = company.getProductXP();
        double initRevenue = company.getRevenue();

        company.researchAndDev();


        int newProductXP = company.getProductXP();
        double newRevenue = company.getRevenue();


        assertEquals(initProductXP + 2, newProductXP);
        assertEquals( initRevenue - 50000, newRevenue);
    }

    @Test
    @DisplayName("Testing researchAndDev method increases in productXP to a multiple of 10 increases the customerBase ")
    public void testResearchAndDevAddsToCustomerBase(){
        Game game = new Game();
        Company company = game.getCompany();
        int initProductXP = company.getProductXP();
        double initRevenue = company.getRevenue();
        int initCustomerBase = company.getCustomerBase();

        company.researchAndDev();
        company.researchAndDev();
        company.researchAndDev();
        company.researchAndDev();
        company.researchAndDev();

        int newProductXP = company.getProductXP();
        double newRevenue = company.getRevenue();
        int newCustomerBase = company.getCustomerBase();

        assertEquals(initProductXP + 10, newProductXP);
        assertEquals( initRevenue - 250000, newRevenue);
        assertEquals(initCustomerBase + 1000, newCustomerBase);
    }
    //need to add test for if revenue is insufficient

    @Test
    @DisplayName("Testing marketing method increases customerBase by 1000 and reduces revenue by 10000 ")
    public void testMarketing(){
        Game game = new Game();
        Company company = game.getCompany();
        int initCustomerBase = company.getCustomerBase();
        double initRevenue = company.getRevenue();

        company.marketing();

        int newCustomerBase = company.getCustomerBase();
        double newRevenue = company.getRevenue();

        assertEquals(initCustomerBase + 1000, newCustomerBase);
        assertEquals(initRevenue - 10000,newRevenue);


    }
    //need to add test for if revenue is insufficient

//    @Test
//    @DisplayName("Testing invest method")

    @Test
    @DisplayName("Testing increment crowdFund method")
    public void testIncrementCrowdFund(){
        Game game = new Game();
        Company company = game.getCompany();
        int initCrowdFund = company.getCrowdFundCount();

        company.incrementCrowdFundCount();

        int newCrowdFund = company.getCrowdFundCount();

        assertEquals(initCrowdFund + 1, newCrowdFund);

    }

    @Test
    @DisplayName("Testing reset crowdFund Count resets crowdFundCount to 0")
    public void testResetCrowdFund(){
        Game game = new Game();
        Company company = game.getCompany();
        int initCrowdFund = company.getCrowdFundCount();

        company.incrementCrowdFundCount();

        company.resetCrowdFundCount();

        assertEquals(initCrowdFund, 0);
    }

    @Test
    @DisplayName("Testing reduceRevenue method reduces revenue by inputted percentage")
    public void testReduceRevenue() {
        Game game = new Game();
        Company company = game.getCompany();
        double initRevenue = company.getRevenue();
        company.reduceRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue - 100000, newRevenue);

    }

    @Test
    @DisplayName("Testing increaseRevenue method increases revenue by inputted percentage")
    public void testIncreaseRevenue() {
        Game game = new Game();
        Company company = game.getCompany();
        double initRevenue = company.getRevenue();
        company.increaseRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + 100000, newRevenue);

    }

    @Test
    @DisplayName("Testing increaseCustomerBase method increases customers by inputted number")
    public void testIncreaseCustomerBase() {
        Game game = new Game();
        Company company = game.getCompany();
        double initCustomerBase = company.getCustomerBase();
        company.increaseCustomerBase(10);

        double newCustomerBase = company.getCustomerBase();

        assertEquals(initCustomerBase + 10, newCustomerBase);

    }


    @Test
    @DisplayName("Testing reduceCustomerBase method sets customers to 0 if customer base is < inputted reduction number")
    public void testReduceCustomerBase() {
        Game game = new Game();
        Company company = game.getCompany();

        company.reduceCustomerBase(10);

        double newCustomerBase = company.getCustomerBase();

        assertEquals(0, newCustomerBase);

    }

    @Test
    @DisplayName("Testing reduceCustomerBase method reduces customers by inputted number if customer base is greater than customer loss")
    public void testReduceCustomerBaseIfCustomerBaseIsGreaterThanLoss() {
        Game game = new Game();
        Company company = game.getCompany();

        company.increaseCustomerBase(10);
        double initCustomerBase = company.getCustomerBase();

        company.reduceCustomerBase(5);
        double newCustomerBase = company.getCustomerBase();

        assertEquals(initCustomerBase - 5, newCustomerBase);

    }



}
