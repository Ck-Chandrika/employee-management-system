package com.example.employee.exceptions;

public class  InvalidSalaryException extends Exception{
    // this is checked exception it is compulsory to catch it or declare it using throws
    // handle it using try catch or declare using throws
    public InvalidSalaryException(String message){
        super(message); // Passes message to the parent Exception class.
    }
}
