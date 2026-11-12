package com.example.employee;

import com.example.employee.exceptions.InvalidDepartmentException;
import com.example.employee.exceptions.InvalidSalaryException;

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {
    public void test(){
        double a = 1000;
        double b = 0.0 / 0.0;
        double c = 1.0 / 0.0;
        double d = -1.0 / 0.0;

        System.out.println(Double.isFinite(a));
        System.out.println(Double.isFinite(b));
        System.out.println(Double.isFinite(c));
        System.out.println(Double.isFinite(d));
//        Scanner scanner = new Scanner(System.in);
//        boolean flag = false;
//        while(!flag){
//            try {
//                System.out.println("Enter Employee ID: ");
//                int id = scanner.nextInt();
//                System.out.println("Employee ID: " + id);
//                flag = true;
//            } catch(InputMismatchException e){
//                System.out.println("Invalid Employee ID. Please enter a valid number.");
//                scanner.next();
//            }
//        }
    }
    public void testMultipleCatch(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Enter employee annual salary");
            int annualSalary = scanner.nextInt();
            System.out.println("Enter how many months");
            int months = scanner.nextInt();
            int monthlySalay = annualSalary / months;
            System.out.println("Monthly salary : " + monthlySalay);
        } catch(InputMismatchException e){
            System.out.println("Invalid input. Please enter numeric values only.");
        } catch(ArithmeticException e) {
            System.out.println("Months can not be 0.");
        }
    }
    public void testFinally(){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Enter in integer");
            int integer = scanner.nextInt();
            System.out.println("Given Integer : " + integer);
        } catch(InputMismatchException e){
            System.out.println("Invalid input. Please enter numeric values only.");
        } finally{
            System.out.println("Execution Completed.");
        }
    }
    public void validateSalary(double salary) throws InvalidSalaryException {
        if(salary <= 0){
            throw new InvalidSalaryException("salary must be greater than 0.");
        }
        System.out.println("Valid Salary : " + salary);
    }
    public void processSalary(double salary) throws InvalidSalaryException{
        System.out.println("Processing Salary...");
//        try {
            validateSalary(salary);
//            System.out.println("Salary Processed Successfully.");
//        } catch(InvalidSalaryException e){
//            System.out.println("Error inside processSalary: " + e.getMessage());
//        }
//        System.out.println("Process Completed");
    }
    public void readFile() throws IOException { //It does not handle the exception. The caller must handle it or declare it further.
        FileReader reader = new FileReader("employee.txt");
        System.out.println("File Opened Sucessfully.");
        reader.close();
    }
    public void validateAge(int age) {
        if(age < 18){
            throw new IllegalArgumentException("Age must be greater than 18.");
        }
        System.out.println("Eligible");
    }
    public void mainMethod() {
        try{
            validateSalary(0);
        } catch(InvalidSalaryException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    public void validateDepartement(String department){
        if(department == null || department.isEmpty()){
            throw new InvalidDepartmentException("Department cannot be empty.");
        }
        System.out.println("Valid Department : " + department);
    }
    //main code
    /*
     Test test = new Test();
//        test.test();
//        test.testMultipleCatch();
//        test.testFinally();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter Employee Salary: ");
//        double salary = input.nextDouble();
        try{
            test.processSalary(0);
            System.out.println("Salary Processed Successfully.");
        } catch(InvalidSalaryException e){
            System.out.println("Error inside processSalary: " + e.getMessage());
        }
//        try {
//            test.validateDepartement("");
//        } catch(InvalidDepartmentException e){
//            System.out.println("Exception: " + e.getMessage());
//        }
//        test.validateDepartement("IT");
//        try{ or throws at main method
//            test.readFile();
//        } catch(IOException e){
//            System.out.println("Unable to read file.");
//        }
     */
}
