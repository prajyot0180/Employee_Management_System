package com.ems;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       do {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }while(true);
    }

    private static void addEmployee() {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter department: ");
            String department = scanner.nextLine();
            System.out.print("Enter salary: ");
            double salary = scanner.nextDouble();
            System.out.print("Enter email: ");
            String email = scanner.next();

            String sql = "INSERT INTO employees (name, department, salary, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, department);
            stmt.setDouble(3, salary);
            stmt.setString(4, email);
            stmt.executeUpdate();

            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void viewEmployees() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql);
//
//            System.out.println("Employee List:");
//            while (rs.next()) {
//                System.out.println("ID: " + rs.getInt("id") +
//                        ", Name: " + rs.getString("name") +
//                        ", Department: " + rs.getString("department") +
//                        ", Salary: " + rs.getDouble("salary") +
//                        ", Email: " + rs.getString("email"));
//            }
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print table header
            System.out.println("---------------------------------------------------------------------------------------");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-15s", metaData.getColumnName(i)); // Print column names
            }
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------");

            // Print table rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-15s", rs.getString(i)); // Print column values
                }
                System.out.println();
            }
            System.out.println("---------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void updateEmployee() {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new department: ");
            String department = scanner.nextLine();
            System.out.print("Enter new salary: ");
            double salary = scanner.nextDouble();
            System.out.print("Enter new email: ");
            String email = scanner.next();

            String sql = "UPDATE employees SET name = ?, department = ?, salary = ?, email = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, department);
            stmt.setDouble(3, salary);
            stmt.setString(4, email);
            stmt.setInt(5, id);
            stmt.executeUpdate();

            System.out.println("Employee updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter employee ID to delete: ");
            int id = scanner.nextInt();

            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Employee deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

