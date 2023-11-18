package com.cbfacademy.apiassessment.ExceptionClasses;

public class InvalidActionException extends Exception {
    public InvalidActionException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return String.join(" - ", super.getMessage());

    }
}
