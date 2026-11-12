package com.example.employee;

import com.example.employee.enums.EmployeeOperationResult;
import com.example.employee.exceptions.InvalidSalaryException;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeSearchResult;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;

//import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainPractice {
    public static void main(String[] args) throws InvalidSalaryException {
        Test test = new Test();
        test.test();
//        System.out.println(Double.isFinite(1000.0));       // true
//        System.out.println(Double.isFinite(0.0));          // true
//        System.out.println(Double.isFinite(-500.0));       // true

//        System.out.println(Double.isFinite(Double.NaN));   // false
//        System.out.println(Double.isFinite(Double.POSITIVE_INFINITY)); // false
//        System.out.println(Double.isFinite(Double.NEGATIVE_INFINITY)); // false
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

//            System.out.print("Enter your choice: ");
//            if(scanner.hasNextInt()){
            choice = readInt(scanner, "Enter your choice: ");
//            } else{
//                System.out.println("Enter valid number.");
//                scanner.next(); // consume the invalid input
//                continue; // if someone enters invalid input other thatn number,it comes this block but, also runs the switch case with previous value and prints previous value output to prevent this add continue to continue the while loop.
//            }
            switch(choice){
                case 1:
//                    System.out.println("Enter Employee ID : ");
//                    if(!scanner.hasNextInt()){
//                        System.out.println("Invalid Employee ID. Please Enter a number.");
//                        scanner.next(); // consumes invalid input
//                        break;
//                    }
//                    int id = scanner.nextInt();
//                    scanner.nextLine();
                    int id = readInt(scanner, "Emter Employee ID : ");
                    System.out.println("Enter Employee Name : ");
                    String name = scanner.nextLine().trim();
//                    if(name.isEmpty()){
//                        System.out.println("Employee name can not be empty");
//                        break;
//                    }
                    System.out.println("Enter Department Name : ");
                    String department = scanner.nextLine().trim();
//                    if(department.isEmpty()){
//                        System.out.println("Department name can not be empty");
//                        break;
//                    }
//                    System.out.println("Enter Employee Salary : ");
//                    if(scanner.hasNextDouble()){
//                        double salary = scanner.nextDouble();
                    double salary = readDouble(scanner, "Enter Employee Salary : ");
                    Employee employee = new Employee(id, name, department, salary);
                    EmployeeOperationResult result = employeeService.addEmployee(employee);
                    switch(result){
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
//                        case INVALID_SALARY:
//                            System.out.println("Salary must be greater than 0.");
//                            break; //it is removed as we handling this with checked exception
                        default:
                            System.out.println("Unable to add Employee.");
                            break;
                    }
//                    } else{
//                        System.out.println("Invalid Salary. Please enter a numeric value.");
//                        scanner.next(); // consumes invalid input
//                    }
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
//                    System.out.println("Enter Employee ID to search : ");
//                    if(!scanner.hasNextInt()){
//                        System.out.println("Invalid Employee ID.");
//                        scanner.next();
//                        break;
//                    }
//                    int searchId = scanner.nextInt();
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
//                    System.out.println("Enter Employee ID to update details : ");
//                    if(!scanner.hasNextInt()){
//                        System.out.println("Invalid Employee ID. Please Enter a numeric value.");
//                        scanner.next(); // consumes invalid input
//                        break;
//                    }
//                    int updateId = scanner.nextInt();
//                    scanner.nextLine();
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
//                    if(newName.isEmpty()){
//                        System.out.println("Employee name can not be empty.");
//                        break;
//                    }
                    System.out.println("Enter Department Name : ");
                    String newDepartment = scanner.nextLine().trim();
//                    if(newDepartment.isEmpty()){
//                        System.out.println("Department name can not be empty.");
//                        break;
//                    }
//                    System.out.println("Enter Employee Salary : ");
//                    if(!scanner.hasNextDouble()){
//                        System.out.println("Invalid salary. Please enter a numetic value.");
//                        scanner.next();
//                        break;
//                    }
//                    double newSalary = scanner.nextDouble();
                    double newSalary = readDouble(scanner, "Enter Employee Salary : ");
//                    if(newSalary <= 0){
//                        System.out.println("Salary must be greater than 0.");
//                        break;
//                    }
//                    scanner.nextLine();
                    EmployeeOperationResult updateResult = employeeService.updateEmployee(updateId, newName,  newDepartment, newSalary);
                    switch(updateResult){
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
//                        case INVALID_SALARY:
//                            System.out.println("Salary must be greater than 0.");
//                            break;
                        default:
                            System.out.println("Unable to update Employee.");
                            break;
                    }
                    break;
                case 5:
//                    System.out.println("Enter Employee ID to delete : ");
//                    if(!scanner.hasNextInt()){
//                        System.out.println("Invalid Employee ID. Please Enter a numeric value.");
//                        scanner.next(); // consume invalid input
//                        break;
//                    }
//                    int deleteId = scanner.nextInt();
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
//                    scanner.nextLine(); // consume leftover line
                    System.out.println("Enter Department Name to Search : ");
                    String departmentName = scanner.nextLine().trim();
                    if(departmentName.isBlank()){
                        System.out.println("Department name can not be empty.");
                        break;
                    }
                    List<Employee> departmentWiseEmployees = employeeService.getEmployeesByDepartment(departmentName);
                    if(departmentWiseEmployees.isEmpty()){
                        System.out.println("No Employees found with this department: " + departmentName);
                    } else{
                        for(Employee e : departmentWiseEmployees){
                            System.out.println(e);
                        }
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
//                    System.out.println("How many top-paid employees list you want to see?");
//                    if(!scanner.hasNextInt()){
//                        System.out.println("Please enter a valid number.");
//                        scanner.next();
//                        break;
//                    }
//                    int count = scanner.nextInt();
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
        /* block 1
        EmployeeRepository  repository = new EmployeeRepository();
        // Add Employee
        Employee emp1 = new Employee(10304, "Chandrika", "Software", 2600000);
        Employee emp2  = new Employee(10305, "RaviTeja", "Software", 2800000);
        Employee emp3  = new Employee(10306, "", "Software", 2800000);
        Employee emp4  = new Employee(10307, "  ", "Software", 2800000);
        Employee emp5  = new Employee(10308, "Test", "IT", 0);
        Employee emp6  = new Employee(10309, "Test1", "IT", -12321);
        Employee emp7  = new Employee(10310, "Mounika", "IT", 1200000);
        boolean add1 = repository.addEmployee(emp1);
        boolean add2 = repository.addEmployee(emp1);
        boolean add3 = repository.addEmployee(emp2);
        boolean add4 = repository.addEmployee(emp3);
        boolean add5 = repository.addEmployee(emp4);
        boolean add6 = repository.addEmployee(emp5);
        boolean add7 = repository.addEmployee(emp6);
        boolean add8 = repository.addEmployee(emp7);
        System.out.println("Employee 1 Added - " + add1);
        System.out.println("Employee 1 Added Again - " + add2);
        System.out.println("Employee 2 Added - " + add3);
        System.out.println("Employee 3 Added - " + add4);
        System.out.println("Employee 4 Added - " + add5);
        System.out.println("Employee 5 Added - " + add6);
        System.out.println("Employee 6 Added - " + add7);
        System.out.println("Employee 6 Added - " + add8);
        // Get Employees
        List<Employee> employeesList = repository.getEmployees();
        System.out.println("Employees added successfully...Here is the List.");
        for (Employee e : employeesList){
            System.out.println(e);
        }
        // Get Employees By Departmentwise
        List<Employee> departmentWiseEmployees = repository.getEmployeesByDepartment("software");
        System.out.println("Employees of software department : ");
        for(Employee e : departmentWiseEmployees){
            System.out.println(e);
        }

        // GetEmployee by Id
        System.out.println("Given Employee Details");
        Employee selectedEmployee = repository.getEmployeeById(10304);
        System.out.println(selectedEmployee);

        //Update Employee
        boolean updated = repository.updateEmployee(10304, "Chandrika", "Software", 3000000);
        System.out.println("Employee Details updated successfully..." + updated);
        System.out.println(repository.getEmployeeById(10304));

        //Delete Employess
        boolean deleted = repository.deleteEmployee(10305);
        System.out.println("Employee Details deleted successfully..." + deleted);
        System.out.println(repository.getEmployeeById(10305));

        // Get Hśighest paid Employee details
        Employee highestPaidEmployee = repository.getHighestSalaryEmployee();
        System.out.println("Highest salary employee : " + highestPaidEmployee.getEmployeeName() + " with " + highestPaidEmployee.getSalary() + " salary.");

        // Get Highest salary employees with count
        List<Employee> highestPaidEmployees = repository.getTopPaidEmployess(0);
        System.out.println("Highest paid employees order wise");
        for(Employee e : highestPaidEmployees){
            System.out.println(e);
        }*/
        // Getter Setter Usage block 2
        /*
        Employee employee = new Employee(10304, "Chandrika", "Software", 1500000);

        Integer employeeId = employee.getEmployeeId();
        String employeeName = employee.getEmployeeName();
        String departmentName = employee.getDepartment();
        double salary = employee.getSalary();

        System.out.println(employeeId);
        System.out.println(employeeName);
        System.out.println(departmentName);
        System.out.println(salary);

        System.out.println(employee.toString());

        employee.setEmployeeName("Chandrika K");
        employee.setDepartment("AI");
        employee.setSalary(2600000);

        String employeeName1 = employee.getEmployeeName();
        String departmentName1 = employee.getDepartment();
        double salary1 = employee.getSalary();

        System.out.println(employeeName1);
        System.out.println(departmentName1);
        System.out.println(salary1);

        System.out.println(employee.toString());
        */
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