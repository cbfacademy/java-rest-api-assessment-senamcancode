package com.cbfacademy.apiassessment.FinTechClasses;

public class Company {
    private int employees;
    private int departments = 0;
    public final static double costOfEmployee = 5000;
    private int customerBase = 0;
    private int productXP = 1;
    private double revenue = 1000000;


    public void crowdFund(){
        revenue += 100000;
    }


    //company methods to be used in the API game
    //crowdFund method - increases revenue but can only be used once per turn


    //sniper invest method - increases or decreases revenue after 2 turns

    //passive invest method - increases or decreases revenue after 3 turns

    //hireEmployee method - company can add employees based on the revenue but no more than 10 employees per turn

    //addDepartment - add a departmetn but they need a minimum number of employees

    //researchAndDev - adds 2 to the product XP (when XP is a multiple of 10 this adds 1000 to customer base )

    //marketing - adds 100 to customer base and 1 to product XP








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