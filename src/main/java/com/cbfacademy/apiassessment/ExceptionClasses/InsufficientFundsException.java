package com.cbfacademy.apiassessment.ExceptionClasses;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message){
        super(message);

    }

    @Override
    public String getMessage(){
        return String.join(" - ", super.getMessage());
    }
}
