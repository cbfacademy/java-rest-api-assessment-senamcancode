package com.cbfacademy.apiassessment.FinTechClasses;

public class Company {
    private int employees = 1;
    private int departments = 0;
    public final static double costOfEmployee = 5000;
    private int customerBase = 0;
    private int productXP = 1;
    private double revenue = 1000000;
    private int maxCrowdFundCount = 1;

    private int crowdFundCount = 0;



    //company methods to be used in the API game
    //crowdFund method - increases revenue but can only be used once per turn - not sure how to handle this
    public void crowdFund(){
            if(crowdFundCount < maxCrowdFundCount) {
                revenue += 100000;
                incrementCrowdFundCount();
            }
    }

    private boolean hasSufficientFunds(int numberOfEmployees){
        double costOfHiring = costOfEmployee * numberOfEmployees;
        return revenue >= costOfHiring;
    }

    //hireEmployee method - company can add employees but no more than 10 employees per turn
    public void hireEmployee(int numberOfEmployees){
        if(numberOfEmployees <= 10 && hasSufficientFunds(numberOfEmployees)){
            employees += 5;
            double costOfHiring = costOfEmployee * numberOfEmployees;
            revenue -= costOfHiring;
        }
    }

    //addDepartment - add a department but they need a minimum number of employees
    public void addDepartment(){
        if(employees % 10 == 0 && employees > 10) {
            departments += 1;
        }
    }


    //researchAndDev - adds 2 to the product XP (when XP is a multiple of 10 this adds 1000 to customer base )
    public void addProductXP(){
        productXP += 2;
        revenue -= 50000;
    }

    //marketing - adds 100 to customer base and 1 to product XP
    public void market(){
        customerBase += 100;
        productXP += 1;
        revenue -= 10000;
    }

    //sniper invest method - increases or decreases revenue after 2 turns
    //have a method which generates 2 random numbers:
    //the first is between 1 or 2
    //the second is a number between 1000 and 100,000
    //if the number is 1, you add the second number generated to revenue
    //if the number is 2, you take away the second number generated from revenue


    //passive invest method - increases or decreases revenue after 3 turns


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
