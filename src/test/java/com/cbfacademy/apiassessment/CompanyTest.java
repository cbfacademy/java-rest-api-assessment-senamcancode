package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {
    //need to find a way to test the investment method - the problem is that the method provides random results

    private Game game;
    private Company company;
    @BeforeEach
    public void setUp(){
        this.game = new Game();
        this.company = game.getCompany();
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
        boolean result = company.hasSufficientFunds(1000);

        assertFalse(result);

    }

    @Test
    @DisplayName("Testing that the addEmployee method throws an Insufficient funds exception")
    public void testAddEmployeeException() throws InsufficientFundsException {

        assertThrows(InsufficientFundsException.class, () -> {
            company.addEmployee(100000);
        });

    }

    @Test
    @DisplayName("Testing method increases employees in Company ")
    public void testAddEmployee() throws InsufficientFundsException, InvalidActionException {
        double initialEmployee = company.getEmployees();

        company.addEmployee(1);

        double newEmployeeNum = company.getEmployees();
        assertEquals(initialEmployee + 1, newEmployeeNum);

    }


    @Test
    @DisplayName("Testing the removeEmployee method removes employees and departments")
    public void testRemoveEmployee() throws InvalidActionException {
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
        company.setRevenue(4999);

        assertThrows(InvalidActionException.class, () -> company.researchAndDev());

    }


    @Test
    @DisplayName("Testing researchAndDev method increases in productXP to a multiple of 10 increases the customerBase ")
    public void testResearchAndDevAddsToCustomerBase() throws InvalidActionException {
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
    @DisplayName("Testing max investment count reached for sniperInvestment method")
    public void testMaxInvestmentCountReachedForSniperInvestment() {
        company.setInvestCount(company.getMaxInvestCount());

        assertThrows(InvalidActionException.class, () -> company.sniperInvestment());
    }

    @Test
    @DisplayName("Testing max investment count reached for passiveInvestment method")
    public void testMaxInvestmentCountReachedForPassiveInvestment() {
        company.setInvestCount(company.getMaxInvestCount());

        assertThrows(InvalidActionException.class, () -> company.passiveInvestment());
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
    @DisplayName("Testing resetCrowdFundCount method resets crowdFundCount to 0")
    public void testResetCrowdFundCount(){
        int initCrowdFundCount = company.getCrowdFundCount();
        company.incrementCrowdFundCount();
        company.resetCrowdFundCount();

        assertEquals(initCrowdFundCount, 0);
    }



    @Test
    @DisplayName("Testing resetInvestCount method resets investCount to 0" )
    public void testResetInvestCount(){
        int initInvestCount = company.getInvestCount();
        company.incrementInvestCount();
        company.resetInvestCount();

        assertEquals(initInvestCount, 0);
    }

    @Test
    @DisplayName("Testing reduceRevenue method reduces revenue by inputted percentage")
    public void testReduceRevenue() {
        double initRevenue = company.getRevenue();
        company.reduceRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue - (0.1 * initRevenue), newRevenue);

    }

    @Test
    @DisplayName("Testing increaseRevenue method increases revenue by inputted percentage")
    public void testIncreasesRevenue() {
        double initRevenue = company.getRevenue();
        company.increaseRevenue(10);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + (0.1 * initRevenue), newRevenue);

    }

    @Test
    @DisplayName("Testing increaseCustomerBase method increases customers by inputted number")
    public void testIncreasesCustomerBase() {
        double initCustomerBase = company.getCustomerBase();
        company.increaseCustomerBase(10);
        double newCustomerBase = company.getCustomerBase();

        assertEquals(initCustomerBase + 10, newCustomerBase);

    }


    //@ParameterizedTest
    ///I think I can make these a parameterised test - for the next 2 tests!!!
    @Test
    @DisplayName("Testing reduceCustomerBase method reduces customers by inputted number if customer base is greater than customer loss")
    public void testReduceCustomerBaseIfCustomerBaseIsGreaterThanLoss() {
        company.increaseCustomerBase(10);
        double initCustomerBase = company.getCustomerBase();

        company.reduceCustomerBase(5);
        double newCustomerBase = company.getCustomerBase();

        assertEquals(initCustomerBase - 5, newCustomerBase);

    }

    @Test
    @DisplayName("Testing reduceCustomerBase method sets customers to 0 if customer base is < inputted reduction number")
    public void testReduceCustomerBase() {
        company.reduceCustomerBase(10);
        double newCustomerBase = company.getCustomerBase();

        assertEquals(0, newCustomerBase);

    }

    @Test
    @DisplayName("Testing the customerRevenueBoost method increases the revenue by 5 * customerBase")
    public void testCustomerRevenueBoost(){
        double initRevenue = game.getCompany().getRevenue();
        company.setCustomerBase(10);
        company.customerRevenueBoost();
        double newRevenue = game.getCompany().getRevenue();

        assertEquals(initRevenue + 50, newRevenue);


    }

    @Test
    @DisplayName("Testing the motherlode method sets revenue to 5,000,000, departments to 3, employees to 30, customerBase to 10000 and productXP to 30")
    public void testMotherLode(){
        double initRevenue = company.getRevenue();
        int initDepartments = company.getDepartments();
        int initEmployees = company.getEmployees();
        int initCustomerBase = company.getCustomerBase();
        int initProductXP = company.getProductXP();

        company.motherLode();

        double newRevenue = company.getRevenue();
        int newDepartments = company.getDepartments();
        int newEmployees = company.getEmployees();
        int newCustomerBase = company.getCustomerBase();
        int newProductXP = company.getProductXP();

        assertEquals(initRevenue + 4000000, newRevenue);
        assertEquals(initDepartments + 3, newDepartments);
        assertEquals(initEmployees + 29, newEmployees);
        assertEquals(initCustomerBase + 10000, newCustomerBase);
        assertEquals(initProductXP + 30, newProductXP);

    }

    //Tests for getters and setters
    @Test
    @DisplayName("Testing the setter for the crowdFuncCount sets value")
    public void setCrowdFundCount() throws NoSuchFieldException, IllegalAccessException {
        company.setCrowdFundCount(100);

        final Field field = company.getClass().getDeclaredField("crowdFundCount");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }

    @Test
    @DisplayName("Testing the getter for the crowdFundCount gets value")
    public void getCrowdFundCount() throws NoSuchFieldException, IllegalAccessException{
        final Field field = company.getClass().getDeclaredField("crowdFundCount");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getCrowdFundCount();

        assertEquals(result, 100);
    }

    @Test
    @DisplayName("Testing the setter for employees sets value")
    public void setEmployees() throws NoSuchFieldException, IllegalAccessException {
        company.setEmployees(100);

        final Field field = company.getClass().getDeclaredField("employees");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }


    @Test
    @DisplayName("Testing the getter for employees gets value")
    public void getEmployees()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("employees");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getEmployees();

        assertEquals(result, 100);
    }

    @Test
    @DisplayName("Testing the setter for departments sets value")
    public void setDepartments() throws NoSuchFieldException, IllegalAccessException {
        company.setDepartments(100);

        final Field field = company.getClass().getDeclaredField("departments");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }


    @Test
    @DisplayName("Testing the getter for departments gets value")
    public void getDepartments()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("departments");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getDepartments();

        assertEquals(result, 100);
    }

    @DisplayName("Testing the setter for customerBase sets value")
    public void setCustomerBase() throws NoSuchFieldException, IllegalAccessException {
        company.setCustomerBase(100);

        final Field field = company.getClass().getDeclaredField("customerBase");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }


    @Test
    @DisplayName("Testing the getter for customerBase gets value")
    public void getCustomerBase()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("customerBase");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getCustomerBase();

        assertEquals(result, 100);
    }

    @DisplayName("Testing the setter for productXP sets value")
    public void setProductXP() throws NoSuchFieldException, IllegalAccessException {
        company.setProductXP(100);

        final Field field = company.getClass().getDeclaredField("productXP");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }


    @Test
    @DisplayName("Testing the getter for productXP gets value")
    public void getProductXP()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("productXP");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getProductXP();

        assertEquals(result, 100);
    }

    @DisplayName("Testing the setter for revenue sets value")
    public void setRevenue() throws NoSuchFieldException, IllegalAccessException {
        company.setRevenue(100);

        final Field field = company.getClass().getDeclaredField("revenue");
        field.setAccessible(true);

        assertEquals(field.get(company), 100);
    }


    @Test
    @DisplayName("Testing the getter for revenue gets value")
    public void getRevenue()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("revenue");
        field.setAccessible(true);
        field.set(company,100);

        final double result = company.getRevenue();

        assertEquals(result, 100);
    }

    @Test
    @DisplayName("Testing the getter for investCount gets value")
    public void getInvestCount()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("investCount");
        field.setAccessible(true);
        field.set(company,100);

        final double result = company.getInvestCount();

        assertEquals(result, 100);
    }

    @Test
    @DisplayName("Testing the getter for companyName gets value")
    public void getCompanyName()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("companyName");
        field.setAccessible(true);
        field.set(company,"Hello World");

        final String result = company.getCompanyName();

        assertEquals(result, "Hello World");
    }

    @Test
    @DisplayName("Testing the setter for companyName sets value")
    public void setCompanyName() throws NoSuchFieldException, IllegalAccessException {
        company.setCompanyName("Hello World");

        final Field field = company.getClass().getDeclaredField("companyName");
        field.setAccessible(true);

        assertEquals(field.get(company), "Hello World");
    }





}
