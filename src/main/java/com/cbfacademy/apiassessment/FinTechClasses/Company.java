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
    private int maxCrowdFundCount = 1;

    private int crowdFundCount = 0;



    //company methods to be used in the API game
    //crowdFund method - increases revenue but can only be used once per turn - not sure how to handle this
    public void crowdFund() throws InvalidActionException {
        if(crowdFundCount < maxCrowdFundCount) {
                revenue += 100000;
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

    public void removeEmployee(int numberOfEmployees){
        employees -= numberOfEmployees;
        double costOfHiring = costOfEmployee * numberOfEmployees;
        revenue -= costOfHiring;
    }

    //addDepartment - add a department but they need a minimum number of employees - need to re-write
    public void addDepartment(){
        int employeesNeeded = (departments+ 1) * 10;

        if (employees >= employeesNeeded){
            departments++;
        }
    }


    //researchAndDev - adds 2 to the product XP (when XP is a multiple of 10 this adds 1000 to customer base)
    public void researchAndDev(){
        if (revenue > 50000 && productXP < 100) {

            productXP += 2;
            revenue -= 50000;

            if (productXP % 10 == 0) {
                customerBase += 1000;
            }
        }

    }

    //marketing - adds 100 to customer base but costs money
    public void marketing(){
        if(revenue > 10000) {
            customerBase += 1000;
            revenue -= 10000;
        }
    }

    //sniper invest method - increases or decreases revenue after 2 turns
    public void invest(){
        SecureRandom rand = new SecureRandom();
        int firstRandomNumber = rand.nextInt(2);
        int secondRandomNumber = rand.nextInt(100001);

        if(firstRandomNumber == 0) {
            revenue += secondRandomNumber;
        } else {
            revenue -= secondRandomNumber;
        }
    }


    //passive invest method - increases or decreases revenue after 3 turns
    //how could I approach having a delay of the investment method

    public void incrementCrowdFundCount(){
        crowdFundCount++;
    }

    //this method will be used in the advance turn method
    public void resetCrowdFundCount(){
        crowdFundCount = 0;
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
}
