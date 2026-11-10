package com.example.employee.model;

public class Employee {
    private final int employeeId; // don't need to change the employeeId so used final
    private String employeeName; // add private as other class can't access these values directly
    private String department;
    private double salary;

    public Employee(int employeeId, String employeeName,  String department, double salary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.salary = salary;
    }
    public String getEmployeeName(){
        return employeeName;
    }
    public void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
    }
    public Integer getEmployeeId(){
        return employeeId;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    @Override //add annotation as we overriding toString from parent class Object
    public String toString(){
        return String.format("Employee Details - Name: %s, Id: %d, Department: %s, Salary: ₹%,.2f", employeeName, employeeId, department, salary);
    }
}
