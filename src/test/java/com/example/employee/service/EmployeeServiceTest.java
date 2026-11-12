package com.example.employee.service;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.exceptions.InvalidDepartmentException;
import com.example.employee.exceptions.InvalidSalaryException;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;
import com.example.employee.repository.EmployeeRepository;

import com.example.employee.repository.EmployeeRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private EmployeeRepository repository;
    private EmployeeService service;

    @BeforeEach
    void setup() {
        repository = new EmployeeRepository();
        service = new EmployeeService(repository);
    }

    private Employee createEmployee() {
        return new Employee(101, "Chandrika", "Software", new BigDecimal("25000000"));
    }

    @Test
    void shouldAddEmployeeSuccessfully() throws InvalidSalaryException {
        EmployeeOperationResult result = service.addEmployee(createEmployee());
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        assertNotNull(repository.getEmployeeById(101));
    }

    @Test
    void shouldRejectDuplicateEmployeeId() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        Employee duplicateEmployee = new Employee(101, "Ravi Teja", "Software", new BigDecimal("25000000"));
        EmployeeOperationResult result = service.addEmployee(duplicateEmployee);
        assertEquals(EmployeeOperationResult.DUPLICATE_ID, result);
    }

    @Test
    void shouldRejectInvalidSalary() {
        Employee employee = new Employee(101, "Chandrika", "Software", new BigDecimal("-25000000"));
        assertThrows(InvalidSalaryException.class, () -> service.addEmployee(employee));
        assertTrue(repository.getEmployees().isEmpty());
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        EmployeeOperationResult result = service.updateEmployee(101, "Chandrika 1", "Software 1", new BigDecimal("25000000"));
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        Employee updated = repository.getEmployeeById(101);
        assertEquals("Chandrika 1", updated.getEmployeeName());
        assertEquals("Software 1", updated.getDepartment());
        assertEquals(new BigDecimal("25000000"), updated.getSalary());
    }

    @Test
    void shouldNotUpdateEmployeeWhenSalaryIsInvalid() throws  InvalidSalaryException {
        service.addEmployee(createEmployee());
        assertThrows(InvalidSalaryException.class, () -> service.updateEmployee(101, "Chandrika 2", "IT", new BigDecimal("-123123")));
        Employee employee = repository.getEmployeeById(101);
        assertEquals("Chandrika", employee.getEmployeeName());
        assertEquals("Software", employee.getDepartment());
        assertEquals(new BigDecimal("25000000"), employee.getSalary());
    }

    @Test
    void shouldReturnEmployeeNotFoundWhenUpdatingUnknownId() throws InvalidSalaryException {
        EmployeeOperationResult result = service.updateEmployee(102, "Chandrika", "Software", new BigDecimal("25000000"));
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result);
    }

    @Test
    void shouldRejectInvalidDepartmentDuringUpdate() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        EmployeeOperationResult result = service.updateEmployee(101, "Chandrika", " ", new BigDecimal("26000000"));
        assertEquals(EmployeeOperationResult.INVALID_DEPARTMENT, result);
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        EmployeeOperationResult result = service.deleteEmployee(101);
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        assertNull(repository.getEmployeeById(101));
    }

    @Test
    void shouldReturnInvalidIdWhenDeletingWithZero() {
        EmployeeOperationResult result = service.deleteEmployee(0);
        assertEquals(EmployeeOperationResult.INVALID_ID, result);
    }

    @Test
    void shouldRejectInvalidEmployeeId() throws InvalidSalaryException {
        Employee employee = new Employee(0, "Chandrika", "Software", new BigDecimal("25000000"));
        EmployeeOperationResult result = service.addEmployee(employee);
        assertEquals(EmployeeOperationResult.INVALID_ID, result);
    }

    @Test
    void shouldRejectInvalidEmployeeName() throws InvalidSalaryException {
        Employee employee = new Employee(101, " ", "Software", new BigDecimal("25000000"));
        EmployeeOperationResult result = service.addEmployee(employee);
        assertEquals(EmployeeOperationResult.INVALID_NAME, result);
    }

    @Test
    void shouldRejectInvalidDepartment() throws InvalidSalaryException {
        Employee employee = new Employee(101, "Chandrika", " ", new BigDecimal("25000000"));
        EmployeeOperationResult result = service.addEmployee(employee);
        assertEquals(EmployeeOperationResult.INVALID_DEPARTMENT, result);
    }

    @Test
    void shouldRejectNullEmployee() throws InvalidSalaryException {
        EmployeeOperationResult result = service.addEmployee(null);
        assertEquals(EmployeeOperationResult.INVALID_EMPLOYEE, result);
    }

    @Test
    void shouldReturnEmployeeNotFoundWhenDeletingUnknownId() throws InvalidSalaryException {
        EmployeeOperationResult result = service.deleteEmployee(12313);
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result);
    }

    @Test
    void shouldFindExistingEmployee() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        EmployeeSearchResult result = service.searchEmployeeById(101);
        assertEquals(EmployeeOperationResult.SUCCESS, result.getResult());
        assertNotNull(result.getEmployee());
        assertEquals(101, result.getEmployee().getEmployeeId());
    }

    @Test
    void shouldReturnEmployeeNotFoundWhenSearchingUnknownId() throws InvalidSalaryException {
        EmployeeSearchResult result = service.searchEmployeeById(999);
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result.getResult());
        assertNull(result.getEmployee());
    }

    @Test
    void shouldThrowExceptionForInvalidDepartment() {
        assertThrows(InvalidDepartmentException.class, () -> service.getEmployeesByDepartment(" "));
    }

    // Now we'll check whether the application behaves correctly with unusual but valid or invalid values.
    @Test
    void shouldRejectZeroSalary() {
        Employee employee = new Employee(101, "Chandrika", "Software", new BigDecimal("0"));
        assertThrows(InvalidSalaryException.class, () -> service.addEmployee(employee));
    }

    @Test
    void shouldRejectNaNSalary() {
        Employee employee = new Employee(101, "Chandrika", "Software", Double.NaN);
        assertThrows(InvalidSalaryException.class, () -> service.addEmployee(employee));
    }

    @Test
    void shouldRejectInfiniteSalary(){
        Employee employee = new Employee(101, "Chandrika", "Software", Double.POSITIVE_INFINITY);
        assertThrows(InvalidSalaryException.class, () -> service.addEmployee(employee));
    }

    @Test
    void shouldNotModifyOtherEmployeesDuringUpdate() throws InvalidSalaryException {
        service.addEmployee(createEmployee());
        Employee secondEmployee = new Employee(102, "Chandrika 1", "Software 1", new BigDecimal("26000000"));
        service.addEmployee(secondEmployee);
        service.updateEmployee(101, "Chandrika Kammadanam", "Software Engineering", new BigDecimal("27000000"));
        Employee unchanged = repository.getEmployeeById(102);
        assertEquals("Chandrika 1", unchanged.getEmployeeName());
        assertEquals("Software 1", unchanged.getDepartment());
        assertEquals(new BigDecimal("26000000"), unchanged.getSalary());
    }
}
