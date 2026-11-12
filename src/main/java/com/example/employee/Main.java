package com.example.employee;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.exceptions.InvalidDepartmentException;
import com.example.employee.exceptions.InvalidSalaryException;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(repository);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while(choice != 9){
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Search by Department");
            System.out.println("7. Find Highest Paid Employee");
            System.out.println("8. View Top Paid Employees");
            System.out.println("9. Exit");

            choice = readInt(scanner, "Enter your choice: ");
            switch(choice){
                case 1:
                    int id = readInt(scanner, "Enter Employee ID : ");
                    System.out.println("Enter Employee Name : ");
                    String name = scanner.nextLine().trim();
                    System.out.println("Enter Department Name : ");
                    String department = scanner.nextLine().trim();
                    double salary = readDouble(scanner, "Enter Employee Salary : ");
                    Employee employee = new Employee(id, name, department, salary);
                    try { // we should handle this try catch here as addEmployee using a function which is using checked invalidsalayEsception
                        // Because InvalidSalaryException is a checked exception,
                        // we must catch it or declare it using throws.
                        // If it propagates out of main() without being handled,
                        // the application will terminate with an exception.
                        EmployeeOperationResult result = employeeService.addEmployee(employee);
                        switch (result) {
                            case SUCCESS:
                                System.out.println("Employee added successfully");
                                break;
                            case DUPLICATE_ID:
                                System.out.println("Employee ID already exists");
                                break;
                            case INVALID_ID:
                                System.out.println("Employee ID must be greater than 0.");
                                break;
                            case INVALID_NAME:
                                System.out.println("Employee name cannot be empty.");
                                break;
                            case INVALID_DEPARTMENT:
                                System.out.println("Department name cannot be empty.");
                                break;
//                          INVALID Salary case no longer needed in the switch because salary validation now throws an exception.
                            default:
                                System.out.println("Unable to add Employee.");
                                break;
                        }
                    } catch(InvalidSalaryException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    List<Employee> allEmployees = employeeService.getEmployees();
                    if(allEmployees.isEmpty()){
                        System.out.println("No Employees Found..");
                    } else {
                        for(Employee e: allEmployees){
                            System.out.println(e);
                        }
                    }
                    break;
                case 3:
                    int searchId = readInt(scanner, "Enter Employee ID to search : ");
                    EmployeeSearchResult searchResult = employeeService.searchEmployeeById(searchId);
                    if(searchResult.getResult() == EmployeeOperationResult.SUCCESS){
                        System.out.println(searchResult.getEmployee());
                    } else if(searchResult.getResult() == EmployeeOperationResult.INVALID_ID){
                        System.out.println("Employee ID must be greater than 0.");
                    } else {
                        System.out.println("Employee not found");
                    }
                    break;
                case 4:
                    int updateId = readInt(scanner, "Enter Employee ID to update details : ");
                    EmployeeSearchResult employeeSearchResult = employeeService.searchEmployeeById(updateId);
                    if(employeeSearchResult.getResult() == EmployeeOperationResult.INVALID_ID){
                        System.out.println("Employee ID must be greater than 0.");
                        break;
                    }
                    if(employeeSearchResult.getResult() == EmployeeOperationResult.EMPLOYEE_NOT_FOUND){
                        System.out.println("Employee not found with this ID.");
                        break;
                    }
                    System.out.println("Enter Employee Name : ");
                    String newName = scanner.nextLine().trim();
                    System.out.println("Enter Department Name : ");
                    String newDepartment = scanner.nextLine().trim();
                    double newSalary = readDouble(scanner, "Enter Employee Salary : ");
                    try {
                        EmployeeOperationResult updateResult = employeeService.updateEmployee(updateId, newName, newDepartment, newSalary);
                        switch (updateResult) {
                            case SUCCESS:
                                System.out.println("Employee updated successfully");
                                break;
                            case INVALID_ID:
                                System.out.println("Invalid Employee ID");
                                break;
                            case EMPLOYEE_NOT_FOUND:
                                System.out.println("Employee not found with this ID");
                                break;
                            case INVALID_NAME:
                                System.out.println("Employee name cannot be empty.");
                                break;
                            case INVALID_DEPARTMENT:
                                System.out.println("Department name cannot be empty.");
                                break;
                            default:
                                System.out.println("Unable to update Employee.");
                                break;
                        }
                    } catch (InvalidSalaryException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    int deleteId = readInt(scanner, "Enter Employee ID to delete : ");
                    EmployeeOperationResult deleteResult = employeeService.deleteEmployee(deleteId);
                    switch(deleteResult){
                        case SUCCESS:
                            System.out.println("Employee deleted successfully!");
                            break;
                        case EMPLOYEE_NOT_FOUND:
                            System.out.println("Employee not found with this ID.");
                            break;
                        case INVALID_ID:
                            System.out.println("Employee ID must be greater than 0.");
                            break;
                        default:
                            System.out.println("Unable to delete Employee.");
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Enter Department Name to Search : ");
                    String departmentName = scanner.nextLine().trim();
                    try {
                        List<Employee> departmentWiseEmployees = employeeService.getEmployeesByDepartment(departmentName);
                        if (departmentWiseEmployees.isEmpty()) {
                            System.out.println("No Employees found with this department: " + departmentName);
                        } else {
                            for (Employee e : departmentWiseEmployees) {
                                System.out.println(e);
                            }
                        }
                    } catch(InvalidDepartmentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    Employee highestPaidEmployee = employeeService.getHighestSalaryEmployee();
                    if(highestPaidEmployee != null){
                        System.out.println("Highest paid employee : ");
                        System.out.println(highestPaidEmployee);
                    } else{
                        System.out.println("No employees available.");
                    }
                    break;
                case 8:
                    if(employeeService.getEmployees().isEmpty()){
                        System.out.println("No employees found.");
                        break;
                    }
                    int count = readInt(scanner, "How many top-paid employees list you want to see?");
                    if(count <= 0){
                        System.out.println("Please enter a number greater than 0.");
                        break;
                    }
                    List<Employee> topPaidEmployeesList = employeeService.getTopPaidEmployees(count);
                    for(Employee e : topPaidEmployeesList){
                        System.out.println(e);
                    }
                    break;
                case 9:
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 9.");
            }
        }
        scanner.close();
    }
    public static int readInt(Scanner scanner, String message) {
        while(true){
            System.out.print(message);
            try{
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch(InputMismatchException e){
                System.out.println("Please enter a valid whole number.");
                scanner.nextLine();
            }
        }
    }
    public static double readDouble(Scanner scanner, String message) {
        while(true){
            System.out.print(message);
            try{
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch(InputMismatchException e){
                System.out.println("Please enter a valid numeric value.");
                scanner.nextLine();
            }
        }
    }
}