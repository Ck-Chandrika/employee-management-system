package com.example.employee.repository;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;

import java.util.*;

public class EmployeeRepository {
    // List<Employee> - a collection that stores Employee objects
    // ArrayList<>() - the actual implementation of the list
    // private - prevents other classes to directly accessing the list
    // final - prevents assigning different list to the employees variable
    private final List<Employee> employees = new ArrayList<>();
    public EmployeeOperationResult addEmployee(Employee employee){
        employees.add(employee);
        return EmployeeOperationResult.SUCCESS;
    }
    public List<Employee> getEmployees(){
        return new ArrayList<>(employees);
    }
    public Employee getEmployeeById(int id){
        for(Employee e: employees){
            if(e.getEmployeeId().equals(id)) {
                return e;
            }
        }
        return null;
    }
    public EmployeeOperationResult updateEmployee(int id, String name, String department, double salary){
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            return EmployeeOperationResult.EMPLOYEE_NOT_FOUND;
        }
        employee.setEmployeeName(name);
        employee.setDepartment(department);
        employee.setSalary(salary);
        return EmployeeOperationResult.SUCCESS;
    }
    public EmployeeOperationResult deleteEmployee(int id){
//        for(Employee e: employees){
//            if(e.getEmployeeId().equals(id)) {
//                employees.remove(e);
//                return true;
//            }
//        }
//        return false;
        Iterator<Employee> iterator = employees.iterator();
        while(iterator.hasNext()){
            Employee e = iterator.next();
            if(e.getEmployeeId().equals(id)) {
                iterator.remove();
                return EmployeeOperationResult.SUCCESS;
            }
        }
        return EmployeeOperationResult.EMPLOYEE_NOT_FOUND;
    }
    public List<Employee> getEmployeesByDepartment(String department){
        if(department == null ||  department.isBlank()){
            return new ArrayList<>();
        }
        List<Employee> departmentEmployeeList = new ArrayList<>();
        for(Employee e: employees){
            // if(e.getDepartment().equalsIgnoreCase(department)) this can throw NullPointerException so we need to add null check
            if(e.getDepartment() != null && e.getDepartment().equalsIgnoreCase(department)){// equalsIgnoreCase ignores case sensitivity
                departmentEmployeeList.add(e);
            }
        }
        return departmentEmployeeList;
    }

    public Employee getHighestSalaryEmployee(){
        if(employees.isEmpty()){
            return null;
        }
        Employee highestSalaryEmployee = employees.get(0);
        for(Employee e: employees){
            if(e.getSalary() > highestSalaryEmployee.getSalary()){
                highestSalaryEmployee = e;
            }
        }
        return highestSalaryEmployee;
    }

    public List<Employee> getTopPaidEmployees(int count){
        if(count <= 0){
            return new ArrayList<>();
        }
        Comparator<Employee> comparator = (e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary());
        // we need to get the employees in descending, highest to lowest depending on their salaries
        List<Employee> sortedEmployees = new ArrayList<>(employees);
        sortedEmployees.sort(comparator);
        if(sortedEmployees.size() > count) {
//            sortedEmployees = sortedEmployees.subList(0, count);
            return sortedEmployees.subList(0, Math.min(count, sortedEmployees.size()));
        }
        return sortedEmployees;
    }
    public EmployeeSearchResult searchEmployeeById(int id){
        if(id <= 0){
            return new EmployeeSearchResult(EmployeeOperationResult.INVALID_ID, null);
        }
        Employee employee = getEmployeeById(id);
        if(employee == null){
            return new EmployeeSearchResult(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, null);
        }
        return new EmployeeSearchResult(EmployeeOperationResult.SUCCESS, employee);
    }
}
