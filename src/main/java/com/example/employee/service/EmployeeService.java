package com.example.employee.service;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.exceptions.InvalidDepartmentException;
import com.example.employee.exceptions.InvalidSalaryException;
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
    public EmployeeOperationResult addEmployee(Employee employee) throws InvalidSalaryException {
        if(employee == null){
            return EmployeeOperationResult.INVALID_EMPLOYEE;
        }
        if(!isValidId(employee.getEmployeeId())){
            return EmployeeOperationResult.INVALID_ID;
        }
        if(repository.getEmployeeById(employee.getEmployeeId()) != null){
            return EmployeeOperationResult.DUPLICATE_ID;
        }
        if(!isValidName(employee.getEmployeeName())){
            return EmployeeOperationResult.INVALID_NAME;
        }
        if(!isValidDepartment(employee.getDepartment())){
            return EmployeeOperationResult.INVALID_DEPARTMENT;
        }
        validateSalary(employee.getSalary());
        return repository.addEmployee(employee);
    }
    public EmployeeOperationResult updateEmployee(int id, String name, String department, double salary) throws InvalidSalaryException {
        if(!isValidId(id)){
            return EmployeeOperationResult.INVALID_ID;
        }
        if(repository.getEmployeeById(id) == null){
            return EmployeeOperationResult.EMPLOYEE_NOT_FOUND;
        }
        if(!isValidName(name)){
            return EmployeeOperationResult.INVALID_NAME;
        }
        if(!isValidDepartment(department)){
            return EmployeeOperationResult.INVALID_DEPARTMENT;
        }
        validateSalary(salary);
        return repository.updateEmployee(id, name, department, salary);
    }
    public EmployeeOperationResult deleteEmployee(int id) {
        if(!isValidId(id)){
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
        if(!isValidDepartment(department)){
            throw new InvalidDepartmentException("Department cannot be empty.");
        }
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
    private boolean isValidDepartment(String department){
        return department != null && !department.isBlank();
    }
    private boolean isValidName(String name){
        return name != null && !name.isBlank();
    }
    private boolean isValidId(int id){
        return id > 0;
    }
    private void validateSalary(double salary) throws InvalidSalaryException {
        // we made it private bcz Only EmployeeService needs this method. Main should not call it directly.
        // This is an example of encapsulation.
        if(!Double.isFinite(salary) || salary <= 0){ // Double.isFinite() rejects NaN, positive infinity and negative infinity.
            throw new InvalidSalaryException("Salary must be a finite number greater than 0.");
        }
    }
}
