package com.example.employee.repository;
import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepositoryTest {
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    private Employee createEmployee(int id, String name, String department, double salary) {
        return new Employee(id, name, department, salary);
    }

    @Test
    void shouldAddEmployeeSuccessfully() {
        Employee employee = createEmployee(101, "Chandrika", "Software", 25000000);
        EmployeeOperationResult result = repository.addEmployee(employee);
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        assertEquals(1, repository.getEmployees().size());
    }

    @Test
    void shouldReturnAllEmployees() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 25000000));
        repository.addEmployee(createEmployee(102, "Ravi Teja", "Software", 26000000));
        List<Employee> employees = repository.getEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    void shouldReturnEmployeeById() {
        Employee employee = createEmployee(101, "Chandrika", "Software", 25000000);
        repository.addEmployee(employee);
        Employee result = repository.getEmployeeById(101);
        assertNotNull(result);
        assertEquals("Chandrika", result.getEmployeeName());
    }

    @Test
    void shouldReturnNullWhenEmployeeIdDoesNotExists() {
        Employee result = repository.getEmployeeById(999);
        assertNull(result);
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 25000000));
        EmployeeOperationResult result = repository.updateEmployee(101, "Chandrika Kammadanam", "Software Engineering", 26000000);
        Employee updatedEmployee = repository.getEmployeeById(101);
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        assertEquals("Software Engineering", updatedEmployee.getDepartment());
        assertEquals(26000000, updatedEmployee.getSalary());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingEmployee() {
        EmployeeOperationResult result = repository.updateEmployee(999, "Chandrika", "Software", 25000000);
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result);
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 25000000));
        EmployeeOperationResult result = repository.deleteEmployee(101);
        assertEquals(EmployeeOperationResult.SUCCESS, result);
        assertNull(repository.getEmployeeById(101));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingEmployee() {
        EmployeeOperationResult result = repository.deleteEmployee(999);
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result);
    }

    @Test
    void shouldFindEmployeesByDepartmentIgnoringCase() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 25000000));
        repository.addEmployee(createEmployee(102, "Ravi Teja", "IT", 26000000));
        List<Employee> employees = repository.getEmployeesByDepartment("software");
        assertEquals(1, employees.size());
        assertEquals(101, employees.get(0).getEmployeeId());
    }

    @Test
    void shouldReturnEmptyListForBlankDepartment() {
        List<Employee> employees = repository.getEmployeesByDepartment(" ");
        assertTrue(employees.isEmpty());
    }

    @Test
    void shouldReturnHighestPaidEmployee() {
        repository.addEmployee(createEmployee(101,  "Chandrika", "Software", 24000000));
        repository.addEmployee(createEmployee(102, "Ravi Teja",  "Software", 26000000));
        repository.addEmployee(createEmployee(103, "Chandrika", "Software", 25000000));
        Employee employee = repository.getHighestSalaryEmployee();
        assertNotNull(employee);
        assertEquals(102, employee.getEmployeeId());
        assertEquals(26000000, employee.getSalary());
    }

    @Test
    void shouldReturnNullWhenNoEmployeesExistsForHighestSalary() {
        Employee result = repository.getHighestSalaryEmployee();
        assertNull(result);
    }

    @Test
    void shouldReturnTopPaidEmployeesInDescendingOrder() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 24000000));
        repository.addEmployee(createEmployee(102, "Ravi Teja", "IT", 26000000));
        repository.addEmployee(createEmployee(103, "Priya", "HR", 25000000));
        List<Employee> topPaidEmployees = repository.getTopPaidEmployees(2);
        assertEquals(2, topPaidEmployees.size());
        assertEquals(102, topPaidEmployees.get(0).getEmployeeId());
        assertEquals(103, topPaidEmployees.get(1).getEmployeeId());
    }

    @Test
    void shouldReturnEmptyListWhenTopPaidCountIsZeroOrNegative() {
        assertTrue(repository.getTopPaidEmployees(0).isEmpty());
        assertTrue(repository.getTopPaidEmployees(-1).isEmpty());
    }

    @Test
    void shouldReturnSuccessWhenSearchingForExistingEmployee() {
        repository.addEmployee(createEmployee(101, "Chandrika", "Software", 25000000));
        EmployeeSearchResult result = repository.searchEmployeeById(101);
        assertEquals(EmployeeOperationResult.SUCCESS, result.getResult());
        assertNotNull(result.getEmployee());
    }

    @Test
    void shouldReturnNotFoundWhenSearchingForMissingEmployee() {
        EmployeeSearchResult result = repository.searchEmployeeById(123123);
        assertEquals(EmployeeOperationResult.EMPLOYEE_NOT_FOUND, result.getResult());
        assertNull(result.getEmployee());
    }

    @Test
    void shouldReturnInvalidIdWhenSearchingWithInvalidId() {
        EmployeeSearchResult result = repository.searchEmployeeById(0);
        assertEquals(EmployeeOperationResult.INVALID_ID, result.getResult());
        assertNull(result.getEmployee());
    }
}
