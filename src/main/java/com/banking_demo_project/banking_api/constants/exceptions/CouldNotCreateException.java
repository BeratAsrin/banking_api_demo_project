package com.banking_demo_project.banking_api.constants.exceptions;

public class CouldNotCreateException extends Exception{
    public CouldNotCreateException(){}
    public CouldNotCreateException(String message){
        super(message);
    }
}
