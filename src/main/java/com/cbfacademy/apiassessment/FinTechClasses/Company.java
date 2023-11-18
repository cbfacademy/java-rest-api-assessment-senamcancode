package com.cbfacademy.apiassessment.FinTechClasses;


import com.cbfacademy.apiassessment.FinTechClasses.Game;
import java.security.SecureRandom;

public class Company {
    private String companyName = "FinTech Company";
    private int employees = 1;
    private int departments = 0;
    public final static double costOfEmployee = 5000;
    private int customerBase = 0;
    private int productXP = 0;
    private int maxProductXP = 30;
    private int employeesNeededForDepartment = 10;
    private double revenue = 1000000;
    private final int maxCrowdFundCount = 1;
    private final int maxInvestCount = 1;
    private int investCount = 0;

    private int crowdFundCount = 0;



    public String crowdFund() {
        if(crowdFundCount < maxCrowdFundCount) {
            double newRevenue = revenue += 500000;
            incrementCrowdFundCount();

            return "Congrats! You crowd funded £" + newRevenue;
            }
        else{
           return "Invalid Action: You can only crowd fund once per turn";
        }
    }

    public boolean hasSufficientFunds(int numberOfEmployees){
        double costOfHiring = costOfEmployee * numberOfEmployees;
        return revenue >= costOfHiring;
    }

    //hireEmployee method - company can add employees but no more than 10 employees per turn
    public String addEmployee(int numberOfEmployees){
        if(!hasSufficientFunds(numberOfEmployees)){
            return ". You do not have enough funds available to add " + numberOfEmployees + " employees";
        }

        if(hasSufficientFunds(numberOfEmployees) && numberOfEmployees <= 10){
            employees += numberOfEmployees;
            double costOfHiring = costOfEmployee * numberOfEmployees;
            revenue -= costOfHiring;

            return ".";
        }
        if(hasSufficientFunds(numberOfEmployees) && numberOfEmployees > 10){
            return ". You cannot add more than 10 employees each action.";
        }
        return null;
    }

    //remove employee method - removes employees and adds their salary to revenue - departments will also be removed at 10 employee intervals ie if a user removes 10 employees they remove 1 department if they remove 20 employees 2 departments and so on
    public String removeEmployee(int numberOfEmployees) {
        if(employees < numberOfEmployees){
            return ". You cannot get rid of more employees than you already have.";
        }

        if(employees > 0 && employees >= numberOfEmployees) {
            employees = employees - numberOfEmployees;

            double costOfHiring = costOfEmployee * numberOfEmployees;
            revenue += costOfHiring;


            //need to also remove the department
            int departmentsToRemove = numberOfEmployees / employeesNeededForDepartment;

            if(departmentsToRemove > 0){
                departments -= departmentsToRemove;
            }

            return ".";
        }

        return null;
    }



    //addDepartment - add a department, but they need a minimum number of employees
    public String addDepartment() {
        int employeesNeeded = (departments + 1) * employeesNeededForDepartment;

        if(employees < employeesNeeded){
            return ". You do not have enough employees to make a department";

        } else {
            departments++;
            return "";
        }
    }


    //researchAndDev - adds 2 to the product XP (when XP is a multiple of 10 this adds 1000 to customer base)
    public String researchAndDev()  {
        if(revenue < 50000){
            return "Insufficient funds: You don't have enough to do research and development.";
        }


        if (revenue >= 50000 && productXP < maxProductXP) {

            productXP += 2;
            revenue -= 50000;

            if (productXP % 10 == 0 && productXP != maxProductXP) {
                customerBase += 500;
            } else if(productXP >= maxProductXP){
                customerBase += 1000;
            }

            return "Research and development success, 2 XP added to the product.";
        }

        if(productXP >= 30){
            return "You have maxed out your product XP and so can no longer use the R&D method.";
        }

        return null;
    }

    //marketing - adds 1000 to customer base but costs 10000
    public String marketing() {
        if(revenue < 10000){
            return "Insufficient funds: You do not have enough funds to implement marketing";
        } else {
            customerBase += 1000;
            revenue -= 10000;
            return "Marketing was successful!";
        }
    }


    //Sniper investment is more of a risk because the amount you can gain or lose is greater
    public String sniperInvestment() {
        if(investCount < maxInvestCount){
        SecureRandom rand = new SecureRandom();
        int firstRandomNumber = rand.nextInt(2);
        int secondRandomNumber = rand.nextInt(500001);

            if(firstRandomNumber == 0) {
                revenue += secondRandomNumber;
                incrementInvestCount();
                return "Congrats! You gained £" + secondRandomNumber;
            }else{
                revenue -= secondRandomNumber;
                incrementInvestCount();
                return "You gambled and lost! You lost £" + secondRandomNumber;
            }
        } else {
            return "Invalid Action: You can only invest once per turn";
        }
    }


    //Passive investment is less of a risk because the amount you can gain or lose is less
    public String passiveInvestment() {
        if(investCount < maxInvestCount){
        SecureRandom rand = new SecureRandom();
        int firstRandomNumber = rand.nextInt(2);
        int secondRandomNumber = rand.nextInt(100001);

            if(firstRandomNumber == 0) {
                revenue += secondRandomNumber;
                incrementInvestCount();
                return "Congrats! You gained £" + secondRandomNumber;
            }else{
                revenue -= secondRandomNumber;
                incrementInvestCount();
                return "You gambled and lost! You lost £" + secondRandomNumber;
            }
        } else {
            return "You can only invest once per turn";
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

    public void customerRevenueBoost(){
        double customerRevenue = customerBase * 5;
        revenue += customerRevenue;
    }

    //A reference back to the old sims cheat code - this will give the company IPO status
    public void motherLode(){
        setRevenue(5000000);
        setDepartments(3);
        setEmployees(30);
        setCustomerBase(10000);
        setProductXP(30);
    }

    //Another cheat code method to increase the revenue - making it even easier to win
    public void moneyMoneyMoney(){
        revenue = 9999999;
    }


    //company getters and setters
    public int getCrowdFundCount() {
        return crowdFundCount;
    }


    public void setCrowdFundCount(int crowdFundCount) {
        this.crowdFundCount = crowdFundCount;
    }

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

    public void setInvestCount(int investCount) {
        this.investCount = investCount;
    }

    public int getInvestCount() {
        return investCount;
    }

    public int getMaxCrowdFundCount() {
        return maxCrowdFundCount;
    }

    public int getMaxInvestCount() {
        return maxInvestCount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getMaxProductXP() {
        return maxProductXP;
    }

    public int getEmployeesNeededForDepartment() {
        return employeesNeededForDepartment;
    }
}
