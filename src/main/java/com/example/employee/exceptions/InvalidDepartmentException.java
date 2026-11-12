package com.example.employee.exceptions;

public class InvalidDepartmentException extends RuntimeException{
    // this is unchecked exception it is not compulsory to catch it or declare it using throws
    public InvalidDepartmentException(String message){
        super(message);
    }
}
