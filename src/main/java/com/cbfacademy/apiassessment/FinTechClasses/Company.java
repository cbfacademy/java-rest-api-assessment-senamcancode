package com.cbfacademy.apiassessment.FinTechClasses;

import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;

import java.security.SecureRandom;

public class Company {
    private int employees = 1;
    private int departments = 0;
    public final static double costOfEmployee = 5000;
    private int customerBase = 0;
    private int productXP = 0;
    private double revenue = 1000000;
    private final int maxCrowdFundCount = 1;
    private final int maxInvestCount = 1;
    private int investCount = 0;

    private int crowdFundCount = 0;



    //company methods to be used in the API game
    //crowdFund method - increases revenue but can only be used once per turn - not sure how to handle this
    public void crowdFund() throws InvalidActionException {
        if(crowdFundCount < maxCrowdFundCount) {
                revenue += 500000;
                incrementCrowdFundCount();
            }
        else{
            throw new InvalidActionException("Invalid action - You can only crowd fund once per turn");
        }
    }

    public boolean hasSufficientFunds(int numberOfEmployees){
        double costOfHiring = costOfEmployee * numberOfEmployees;
        return revenue >= costOfHiring;
    }

    //hireEmployee method - company can add employees but no more than 10 employees per turn
    public void addEmployee(int numberOfEmployees) throws InsufficientFundsException {
        if(!hasSufficientFunds(numberOfEmployees)){
            throw new InsufficientFundsException("You do not have enough funds available");
        }

        if(numberOfEmployees <= 10){
            employees += numberOfEmployees;
            double costOfHiring = costOfEmployee * numberOfEmployees;
            revenue -= costOfHiring;
        }
    }

    public void removeEmployee(int numberOfEmployees) throws InvalidActionException{
        if(employees < numberOfEmployees){
            throw new InvalidActionException("You cannot get rid of more employees than you already have");
        }

        if(employees > 0 && employees > numberOfEmployees) {
            employees -= numberOfEmployees;
            double costOfHiring = costOfEmployee * numberOfEmployees;
            revenue += costOfHiring;
        }

        //need to also remove the department
        int employeesNeededForDepartment = 10;
        int departmentsToRemove = numberOfEmployees / employeesNeededForDepartment;

        if(departmentsToRemove > 0){
            departments -= departmentsToRemove;
        }

    }

    public void productivityBoost(){
        if(employees > 25){
            revenue += (1.5 * revenue);
        }
        //this needs to be telegraphed to the user too
    }

    public void customerRevenueBoost(){
        double customerRevenue = customerBase * 20;
        revenue += customerRevenue;
    }


    //addDepartment - add a department but they need a minimum number of employees - need to re-write
    public void addDepartment() throws InvalidActionException {
        int employeesNeeded = (departments + 1) * 10;

        if(employees < employeesNeeded){
            throw new InvalidActionException("You do not have enough employees to make a department");

        } else {
            departments++;
        }
    }


    //researchAndDev - adds 2 to the product XP (when XP is a multiple of 10 this adds 1000 to customer base)
    public void researchAndDev() throws InvalidActionException {
        if(revenue < 50000){
            throw new InvalidActionException("Insufficient funds: You don't have enough to do research and development");
        }


        if (revenue > 50000 && productXP < 100) {

            productXP += 2;
            revenue -= 50000;

            //might make a separate productXP boost so that I can tell the user that this happened - if not i can put it in the read me
            if (productXP % 10 == 0 && productXP != 100) {
                customerBase += 1000;
            } else if(productXP == 100){
                customerBase += 2000;
            }
        }

        if(productXP == 100){
            throw new InvalidActionException("You have maxed out your product XP and so can no longer use the R&D method");
        }
    }

    //marketing - adds 100 to customer base but costs money
    public void marketing() throws InvalidActionException {
        if(revenue < 10000){
            throw new InvalidActionException("Insufficient funds: You do not have enough funds to implement marketing");
        } else {
            customerBase += 1000;
            revenue -= 10000;
        }
    }

    //    public int investmentGenerator(){
//        SecureRandom rand = new SecureRandom();
//        int firstRandomNumber = rand.nextInt(2);
//        int secondRandomNumber = rand.nextInt(100001);
//
//        return firstRandomNumber;
//    }

    //sniper invest method - increases or decreases revenue after 2 turns
    public void sniperInvestment() throws InvalidActionException{
        //How could I refactor this??
        if(investCount < maxInvestCount){
        SecureRandom rand = new SecureRandom();
        int firstRandomNumber = rand.nextInt(2);
        int secondRandomNumber = rand.nextInt(500001);

            if(firstRandomNumber == 0) {
                revenue += secondRandomNumber;
            } else if(revenue > secondRandomNumber) {
                revenue -= secondRandomNumber;
            } else if(revenue < secondRandomNumber){
                revenue = 0;
            }

            incrementInvestCount();

        //return firstRandomNumber because I want to be able to tell the user what happened
        //want to be able to return what investment was made - so that they know they either lost or made money
        } else {
            throw new InvalidActionException("You can only invest once per turn");
        }
    }

    public void passiveInvestment() throws InvalidActionException{
        if(investCount < maxInvestCount){
        SecureRandom rand = new SecureRandom();
        int firstRandomNumber = rand.nextInt(2);
        int secondRandomNumber = rand.nextInt(100001);

        if(firstRandomNumber == 0) {
            revenue += secondRandomNumber;
        } else if(revenue > secondRandomNumber) {
            revenue -= secondRandomNumber;
        } else if(revenue < secondRandomNumber){
            revenue = 0;
        }

        incrementInvestCount();

        //return firstRandomNumber because I want to be able to tell the user what happened
        //want to be able to return what investment was made - so that they know they either lost or made money
        } else {
            throw new InvalidActionException("You can only invest once per turn");
        }
    }



    public void incrementCrowdFundCount(){
        crowdFundCount++;
    }

    public void incrementInvestCount(){
        investCount++;
    }
    //this method will be used in the advance turn method
    public void resetCrowdFundCount(){
        crowdFundCount = 0;
    }

    public void resetInvestCount(){
        investCount = 0;
    }

    public int getCrowdFundCount() {
        return crowdFundCount;
    }


    public void reduceRevenue(double percentage){
        double reduction = (percentage / 100) * revenue;
        revenue -= reduction;
    }

    public void increaseRevenue(double percentage){
        double reduction = (percentage / 100) * revenue;
        revenue += reduction;
    }

    public void increaseCustomerBase(int customers){
        customerBase += customers;
    }

    public void reduceCustomerBase(int customers){
        if(customerBase > customers) {
            customerBase -= customers;
        } else {
            customerBase = 0;
        }
    }



    //company getters and setters
    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public int getDepartments() {
        return departments;
    }

    public void setDepartments(int departments) {
        this.departments = departments;
    }

    public int getCustomerBase() {
        return customerBase;
    }

    public void setCustomerBase(int customerBase) {
        this.customerBase = customerBase;
    }

    public int getProductXP() {
        return productXP;
    }

    public void setProductXP(int productXP) {
        this.productXP = productXP;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getMaxCrowdFundCount() {
        return maxCrowdFundCount;
    }

    public int getMaxInvestCount() {
        return maxInvestCount;
    }

    public int getInvestCount() {
        return investCount;
    }
}
