package com.example.employee.exceptions;

public class InvalidSalaryException extends Exception{
    public InvalidSalaryException(String message){
        super(message); // Passes message to the parent Exception class.
    }
}
