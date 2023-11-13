package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {
    //need to find a way to test the investment method - the problem is that the method provides random results

    private Game game;
    private Company company;
    @BeforeEach
    public void gameAndCompanyInitialiser(){
        game = new Game();
        company = game.getCompany();
    }

    @Test
    @DisplayName("Testing crowdFund method increases revenue by 500000 in Company ")
    public void testCrowdFund() throws InvalidActionException {

        double initialRevenue = company.getRevenue();

        company.crowdFund();

        double newRevenue = company.getRevenue();
        assertEquals(initialRevenue + 500000, newRevenue);

    }


    @Test
    @DisplayName("Testing has sufficient funds method")
    public void testInsufficientFunds(){
//        Game game = new Game();
        Company company = game.getCompany();
        boolean result = company.hasSufficientFunds(1000);

        assertFalse(result);

    }

    @Test
    @DisplayName("Testing that the addEmployee method throws an Insufficient funds exception")
    public void testAddEmployeeException() throws InsufficientFundsException {
        Game game = new Game();
        Company company = game.getCompany();

        assertThrows(InsufficientFundsException.class, () -> {
            company.addEmployee(100000);
        });

    }

    @Test
    @DisplayName("Testing method increases employees in Company ")
    public void testAddEmployee() throws InsufficientFundsException, InvalidActionException {
        Game game = new Game();
        Company company = game.getCompany();
        double initialEmployee = company.getEmployees();

        company.addEmployee(1);

        double newEmployeeNum = company.getEmployees();
        assertEquals(initialEmployee + 1, newEmployeeNum);

    }


    @Test
    @DisplayName("Testing the removeEmployee method removes employees and departments")
    public void testRemoveEmployee() throws InvalidActionException {
        Game game = new Game();
        Company company = game.getCompany();
        company.setEmployees(20);
        company.setDepartments(2);
        int initDepartments = company.getDepartments();

        company.removeEmployee(20);

        int newDepartments = company.getDepartments();

        assertEquals(initDepartments - 2, newDepartments);
    }
    @Test
    @DisplayName("Testing the removeEmployee method increases revenue")
    public void testRemoveEmployeeIncreasesRevenue() throws InvalidActionException {
//        Game game = new Game();
//        Company company = game.getCompany();
        company.setEmployees(20);

        double initRevenue = company.getRevenue();

        company.removeEmployee(1);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + 5000, newRevenue);
    }


    @Test
    @DisplayName("Testing the removeEmployee method throws an exception")
    public void testRemoveEmployeeThrowsException() throws InvalidActionException{
        company.setEmployees(5);

        assertThrows(InvalidActionException.class, () -> company.removeEmployee(6));

    }

    @Test
    @DisplayName("Testing customerRevenueBoost method increases revenue")
    public void testCustomerRevenueBoostIncreasesRevenue(){
        double initRevenue = company.getRevenue();
        company.setCustomerBase(50);
        company.customerRevenueBoost();
        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + 250, newRevenue);

    }

    @Test
    @DisplayName("Testing add department method increases departments in company ")
    public void testAddDepartment() throws InsufficientFundsException, InvalidActionException {
        int initDepartments = company.getDepartments();
        company.addEmployee(10);
        company.addDepartment();
        int newDepartments = company.getDepartments();

        assertEquals(initDepartments + 1, newDepartments);

    }

    @Test
    @DisplayName("Testing researchAndDev method increases in productXP ")
    public void testResearchAndDev() throws InvalidActionException {
        int initProductXP = company.getProductXP();
        double initRevenue = company.getRevenue();

        company.researchAndDev();


        int newProductXP = company.getProductXP();
        double newRevenue = company.getRevenue();


        assertEquals(initProductXP + 2, newProductXP);
        assertEquals( initRevenue - 50000, newRevenue);
    }


    @Test
    @DisplayName("Testing the researchAndDev method throws an exception when the revenue is less than the cost of research and development")
    public void testResearchAndDevThrowsException() throws InvalidActionException {
        Game game = new Game();
        Company company = game.getCompany();

        company.setRevenue(4999);

        assertThrows(InvalidActionException.class, () -> company.researchAndDev());

    }


    @Test
    @DisplayName("Testing researchAndDev method increases in productXP to a multiple of 10 increases the customerBase ")
    public void testResearchAndDevAddsToCustomerBase() throws InvalidActionException {
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
    public void testMarketing() throws InvalidActionException {
        int initCustomerBase = company.getCustomerBase();
        double initRevenue = company.getRevenue();

        company.marketing();

        int newCustomerBase = company.getCustomerBase();
        double newRevenue = company.getRevenue();

        assertEquals(initCustomerBase + 1000, newCustomerBase);
        assertEquals(initRevenue - 10000,newRevenue);


    }

    @Test
    @DisplayName("Testing the marketing method throws an exception if revenue is less than 10000")
    public void testMarketingException() throws InvalidActionException {
        company.setRevenue(1000);

        assertThrows(InvalidActionException.class, () -> company.marketing());


    }


    @Test
    @DisplayName("Testing sniper invest method changes revenue")
    public void testSniperInvestMethod() throws InvalidActionException {
        double initRevenue = company.getRevenue();
        company.sniperInvestment();
        double newRevenue = company.getRevenue();

        assertNotEquals(initRevenue, newRevenue);
    }


    @Test
    @DisplayName("Testing passive invest method changes revenue")
    public void testPassiveInvestMethod() throws InvalidActionException {
        double initRevenue = company.getRevenue();
        company.passiveInvestment();
        double newRevenue = company.getRevenue();

        assertNotEquals(initRevenue, newRevenue);
    }

    @Test
    @DisplayName("Testing increment crowdFund method")
    public void testIncrementCrowdFund(){
        int initCrowdFund = company.getCrowdFundCount();
        company.incrementCrowdFundCount();
        int newCrowdFund = company.getCrowdFundCount();

        assertEquals(initCrowdFund + 1, newCrowdFund);

    }

    @Test
    @DisplayName("Testing reset crowdFund Count resets crowdFundCount to 0")
    public void testResetCrowdFund(){
        int initCrowdFund = company.getCrowdFundCount();
        company.incrementCrowdFundCount();
        company.resetCrowdFundCount();

        assertEquals(initCrowdFund, 0);
    }

    //Need a resetinvestcount test - start from here when making more tests 

    @Test
    @DisplayName("Testing reduceRevenue method reduces revenue by inputted percentage")
    public void testReduceRevenue() {
        Game game = new Game();
        Company company = game.getCompany();
        double initRevenue = company.getRevenue();
        company.reduceRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue - (0.1 * initRevenue), newRevenue);

    }

    @Test
    @DisplayName("Testing increaseRevenue method increases revenue by inputted percentage")
    public void testIncreasesRevenue() {
        Game game = new Game();
        Company company = game.getCompany();
        double initRevenue = company.getRevenue();
        company.increaseRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + (0.1 * initRevenue), newRevenue);

    }

    @Test
    @DisplayName("Testing increaseCustomerBase method increases customers by inputted number")
    public void testIncreasesCustomerBase() {
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

    @Test
    @DisplayName("Testing the customerRevenueBoost method increases the revenue by 5 * customerBase")
    public void testCustomerRevenueBoost(){
        Game game = new Game();
        Company company = game.getCompany();

        double initRevenue = game.getCompany().getRevenue();
        company.setCustomerBase(10);
        company.customerRevenueBoost();

        double newRevenue = game.getCompany().getRevenue();

        assertEquals(initRevenue + 50, newRevenue);


    }


}
