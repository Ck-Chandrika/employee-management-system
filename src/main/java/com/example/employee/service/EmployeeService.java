package com.example.employee.service;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;
import com.example.employee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository  repository;
    // 6:We are creating a reference to the repository so that the service can use its methods.
    // final means we cannot assign a different repository to this field after the constructor initializes it.
    public EmployeeService(EmployeeRepository repository) {
        // This is called constructor injection.
        // Instead of creating a repository inside the service using new EmployeeRepository(), we pass an existing repository object.
        this.repository = repository;
    }
    public EmployeeOperationResult addEmployee(Employee employee) {
        if(employee == null){
            return EmployeeOperationResult.INVALID_EMPLOYEE;
        }
        if(employee.getEmployeeId() <= 0){
            return EmployeeOperationResult.INVALID_ID;
        }
        if(repository.getEmployeeById(employee.getEmployeeId()) != null){
            return EmployeeOperationResult.DUPLICATE_ID;
        }
        if(employee.getEmployeeName() == null || employee.getEmployeeName().isBlank()){
            return EmployeeOperationResult.INVALID_NAME;
        }
        if(employee.getDepartment() == null || employee.getDepartment().isBlank()){
            return EmployeeOperationResult.INVALID_DEPARTMENT;
        }
        if(employee.getSalary() <= 0){
            return EmployeeOperationResult.INVALID_SALARY;
        }
        return repository.addEmployee(employee);
    }
    public EmployeeOperationResult updateEmployee(int id, String name, String department, double salary) {
        if(id <= 0){
            return EmployeeOperationResult.INVALID_ID;
        }
        if(repository.getEmployeeById(id) == null){
            return EmployeeOperationResult.EMPLOYEE_NOT_FOUND;
        }
        if(name == null || name.isBlank()){
            return EmployeeOperationResult.INVALID_NAME;
        }
        if(department == null || department.isBlank()){
            return EmployeeOperationResult.INVALID_DEPARTMENT;
        }
        if(salary <= 0){
            return EmployeeOperationResult.INVALID_SALARY;
        }
        return repository.updateEmployee(id, name, department, salary);
    }
    public EmployeeOperationResult deleteEmployee(int id) {
        if(id <= 0){
            return EmployeeOperationResult.INVALID_ID;
        }
        if(repository.getEmployeeById(id) == null){
            return EmployeeOperationResult.EMPLOYEE_NOT_FOUND;
        }
        return repository.deleteEmployee(id);
    }
    public EmployeeSearchResult searchEmployeeById(int id) {
        return repository.searchEmployeeById(id);
    }
    public List<Employee> getEmployees() {
        return repository.getEmployees();
    }
    public List<Employee> getEmployeesByDepartment(String department) {
        return repository.getEmployeesByDepartment(department);
    }
    public Employee getHighestSalaryEmployee() {
        return repository.getHighestSalaryEmployee();
    }
    public List<Employee> getTopPaidEmployees(int count) {
        return repository.getTopPaidEmployees(count);
    }
    public Employee getEmployeeById(int id) {
        return repository.getEmployeeById(id);
    }
}
