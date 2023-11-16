package com.cbfacademy.apiassessment;


import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;


import java.lang.reflect.Field;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompanyTest {
    private Game game;
    private Company company;
    @BeforeEach
    public void setUp(){
        this.game = new Game();
        this.company = game.getCompany();
    }

    @Test
    @DisplayName("Testing crowdFund method increases revenue by 500000 in Company ")
    public void testCrowdFund() {
        double initialRevenue = company.getRevenue();

        company.crowdFund();

        double newRevenue = company.getRevenue();
        assertEquals(initialRevenue + 500000, newRevenue);

    }


    @Test
    @DisplayName("hasSufficientFunds returns false when cost of hiriing employees is greater than revenue")
    public void testInsufficientFunds(){
        boolean result = company.hasSufficientFunds(1000);

        assertFalse(result);

    }



    @Test
    @DisplayName("Testing addEmployee increases employees in Company ")
    public void testAddEmployee() {
        double initialEmployee = company.getEmployees();
        company.addEmployee(1);
        double newEmployeeNum = company.getEmployees();

        assertEquals(initialEmployee + 1, newEmployeeNum);

    }


    @Test
    @DisplayName("addEmployee does not change number of employees or revenue if insufficientFunds returns false")
    public void testAddEmployeeWithInsufficientFunds(){
        double initRevenue = company.getRevenue();
        double initialEmployee = company.getEmployees();
        company.addEmployee(1000);
        double newEmployeeNum = company.getEmployees();
        double newRevenue = company.getRevenue();

        assertEquals(initialEmployee, newEmployeeNum);
        assertEquals(initRevenue, newRevenue);
    }



    @Test
    @DisplayName("Testing the removeEmployee method does not remove employees if the number of employees a company has is less than that to remove")
    public void testRemoveEmployeeDoesNotFunctionWhenEmployeeNumIsNotLargeEnough(){
        company.setEmployees(2);
        int initEmployees = company.getEmployees();

        company.removeEmployee(3);
        int newEmployees = company.getEmployees();

        assertEquals(initEmployees, newEmployees);
    }

    @Test
    @DisplayName("Testing the removeEmployee method removes employees and departments")
    public void testRemoveEmployee() {
        company.setEmployees(20);
        company.setDepartments(2);

        int initEmployees = company.getEmployees();
        int initDepartments = company.getDepartments();
        double initRevenue = company.getRevenue();

        company.removeEmployee(20);

        int newDepartments = company.getDepartments();
        int newEmployees = company.getEmployees();
        double newRevenue = company.getRevenue();

        assertNotEquals(initRevenue, newRevenue);
        assertEquals(initEmployees - 20, newEmployees);
        assertEquals(initDepartments - 2, newDepartments);
    }
    @Test
    @DisplayName("Testing the removeEmployee method increases revenue")
    public void testRemoveEmployeeIncreasesRevenue() {
        company.setEmployees(20);

        double initRevenue = company.getRevenue();

        company.removeEmployee(1);

        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + 5000, newRevenue);
    }


    //Test to fix as exception should not be thrown
    @Test
    @DisplayName("Testing the removeEmployee method returns the relevant string and does not change revenue")
    public void testRemoveEmployeeDoesNotChangeRevenue() {
        double initRevenue = company.getRevenue();
        company.setEmployees(5);
        String result = company.removeEmployee(6);
        double newRevenue = company.getRevenue();


        assertEquals(initRevenue, newRevenue);
        assertEquals(". You cannot get rid of more employees than you already have.", result);

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
    public void testAddDepartment() {
        int initDepartments = company.getDepartments();
        company.addEmployee(10);
        company.addDepartment();
        int newDepartments = company.getDepartments();

        assertEquals(initDepartments + 1, newDepartments);

    }

    @Test
    @DisplayName("Testing researchAndDev method increases in productXP ")
    public void testResearchAndDev()  {
        int initProductXP = company.getProductXP();
        double initRevenue = company.getRevenue();

        company.researchAndDev();


        int newProductXP = company.getProductXP();
        double newRevenue = company.getRevenue();


        assertEquals(initProductXP + 2, newProductXP);
        assertEquals( initRevenue - 50000, newRevenue);
    }



    @Test
    @DisplayName("Testing the researchAndDev method doesnt change revenue and returns the relevat string, when the revenue is less than the cost of research and development")
    public void testResearchAndDevWhenRevenueIsTooLow()  {
        company.setRevenue(4999);

        double initRevenue = company.getRevenue();
        String result = company.researchAndDev();
        double newRevenue = company.getRevenue();

        assertEquals(initRevenue, newRevenue);
        assertEquals("Insufficient funds: You don't have enough to do research and development", result);
    }

    @Test
    @DisplayName("Testing the researchAndDev method doesnt change revenue and returns the relevat string, when the productXP is maxed out")
    public void testResearchAndDevWhenProductXPisMaxedOut(){
        company.setProductXP(company.getMaxProductXP());

        double initRevenue = company.getRevenue();
        String result = company.researchAndDev();
        double newRevenue = company.getRevenue();

        assertEquals(initRevenue, newRevenue);
        assertEquals("You have maxed out your product XP and so can no longer use the R&D method", result);

    }



    @Test
    @DisplayName("Testing researchAndDev method increases in productXP to a multiple of 10 increases the customerBase ")
    public void testResearchAndDevAddsToCustomerBase() {
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
        assertEquals(initCustomerBase + 500, newCustomerBase);
    }


    @Test
    @DisplayName("Testing marketing method increases customerBase by 1000 and reduces revenue by 10000 ")
    public void testMarketing()  {
        int initCustomerBase = company.getCustomerBase();
        double initRevenue = company.getRevenue();

        company.marketing();

        int newCustomerBase = company.getCustomerBase();
        double newRevenue = company.getRevenue();

        assertEquals(initCustomerBase + 1000, newCustomerBase);
        assertEquals(initRevenue - 10000,newRevenue);


    }


    @Test
    @DisplayName("Testing the marketing method does not change revenue and returns the correct string if revenue is less than 10000")
    public void testMarketingException() {

        company.setRevenue(1000);
        double initRevenue = company.getRevenue();
        String result = company.marketing();
        double newRevenue = company.getRevenue();

        assertEquals(initRevenue, newRevenue);
        assertEquals("Insufficient funds: You do not have enough funds to implement marketing", result);
    }


    @Test
    @DisplayName("Testing sniper invest method changes revenue")
    public void testSniperInvestMethod() {
        double initRevenue = company.getRevenue();
        company.sniperInvestment();
        double newRevenue = company.getRevenue();

        assertNotEquals(initRevenue, newRevenue);
    }


    @Test
    @DisplayName("Testing passive invest method changes revenue")
    public void testPassiveInvestMethod() {
        double initRevenue = company.getRevenue();
        company.passiveInvestment();
        double newRevenue = company.getRevenue();

        assertNotEquals(initRevenue, newRevenue);
    }

    //Test to fix - no exception should be thrown - but the function should be tested
    @Test
    @DisplayName("Testing max investment count reached for sniperInvestment method")
    public void testMaxInvestmentCountReachedForSniperInvestment() {
        company.setInvestCount(company.getMaxInvestCount());

        String result = company.sniperInvestment();

        assertEquals("You can only invest once per turn", result);
    }



    @Test
    @DisplayName("Testing max investment count reached for passiveInvestment method")
    public void testMaxInvestmentCountReachedForPassiveInvestment() {
        company.setInvestCount(company.getMaxInvestCount());

        String result = company.sniperInvestment();

        assertEquals("You can only invest once per turn", result);
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

    @DisplayName("Testing the reduceCustomerBase method sets customers to 0 if customer base is < input and reduces customers by input is customers is large enough")
    public class customerBaseReduction {

        static Stream<Arguments> reduceCustomerBase() {
            return Stream.of(
                    arguments(5,5),
                    arguments(6,0)
            );
        }

    }

    @Test
    @DisplayName("Testing the motherlode cheat code method sets revenue to 5,000,000, departments to 3, employees to 30, customerBase to 10000 and productXP to 30")
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

    @Test
    @DisplayName("Testing moneyMoneyMoney cheat codemethod sets revenue to 9999999")
    public void moneyMoneyMoney(){
        double initRevenue = company.getRevenue();
        company.moneyMoneyMoney();
        double newRevenue = company.getRevenue();

        assertEquals(initRevenue + 8999999, newRevenue);
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

    @Test
    @DisplayName("Testing the getter for maxProductXP gets value")
    public void getMaxProductXP()throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("maxProductXP");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getMaxProductXP();

        assertEquals(result, 100);
    }


    @Test
    @DisplayName("Testing the getter for employeesNeededForDepartment")
    public void getEmployeesNeededForDepartment() throws NoSuchFieldException, IllegalAccessException {
        final Field field = company.getClass().getDeclaredField("maxProductXP");
        field.setAccessible(true);
        field.set(company,100);

        final int result = company.getMaxProductXP();

        assertEquals(result, 100);

    }




}
