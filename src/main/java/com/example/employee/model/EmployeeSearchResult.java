package com.example.employee.model;

import com.example.employee.enums.EmployeeOperationResult;

public class EmployeeSearchResult {
    private final EmployeeOperationResult result;
    private final Employee employee;
    public EmployeeSearchResult(EmployeeOperationResult result, Employee employee) {
        this.result = result;
        this.employee = employee;
    }
    public EmployeeOperationResult getResult() {
        return result;
    }
    public Employee getEmployee() {
        return employee;
    }
}
