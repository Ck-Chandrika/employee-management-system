package com.example.employee.exceptions;

public class InvalidDepartmentException extends RuntimeException{
    public InvalidDepartmentException(String message){
        super(message);
    }
}
