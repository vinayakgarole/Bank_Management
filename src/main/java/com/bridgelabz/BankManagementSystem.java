package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class BankManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root123";

    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Retrieve data from customerDetails table
            String query1 = "SELECT * FROM customerDetails";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);

            while (rs1.next()) {
                int id = rs1.getInt("ID");
                String firstName = rs1.getString("firstName");
                String middleName = rs1.getString("middleName");
                String lastName = rs1.getString("lastName");
                int mobileNumber = rs1.getInt("mobileNumber");
                System.out.println(id + ", " + firstName + " " + middleName + " " + lastName + ", " + mobileNumber);
            }

            // Retrieve data from accountDetails table
            String query2 = "SELECT * FROM accountDetails";
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);

            while (rs2.next()) {
                int customerId = rs2.getInt("Customer_Id");
                int accountNumber = rs2.getInt("Account_Number");
                String accountType = rs2.getString("Account_Type");
                int accountBalance = rs2.getInt("Account_Balance");
                String branchCode = rs2.getString("Branch_Code");
                System.out.println(customerId + ", " + accountNumber + ", " + accountType + ", " + accountBalance + ", " + branchCode);
            }

            // Close the connections
            rs1.close();
            stmt1.close();
            rs2.close();
            stmt2.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deposit(int accountNumber, int amount) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Get current account balance
            String query = "SELECT Account_Balance FROM accountDetails WHERE Account_Number = " + accountNumber;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int currentBalance = rs.getInt("Account_Balance");
            rs.close();
            stmt.close();
            int updatedBalance = currentBalance + amount;
            String updateQuery = "UPDATE accountDetails SET Account_Balance = " + updatedBalance + " WHERE Account_Number = " + accountNumber;
            Statement updateStmt = conn.createStatement();
            updateStmt.executeUpdate(updateQuery);
            System.out.println("Deposited " + amount + " to Account Number " + accountNumber);
            updateStmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void withdraw(int accountNumber, int amount) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Get current account balance
            String query = "SELECT Account_Balance FROM accountDetails WHERE Account_Number = " + accountNumber;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int currentBalance = rs.getInt("Account_Balance");
            rs.close();
            stmt.close();

            // Check if withdrawal amount is less than or equal to the current account balance
            if (amount <= currentBalance) {
                // Update account balance with the withdrawn amount
                int updatedBalance = currentBalance - amount;
                String updateQuery = "UPDATE accountDetails SET Account_Balance = " + updatedBalance + " WHERE Account_Number = " + accountNumber;
                Statement updateStmt = conn.createStatement();
                updateStmt.executeUpdate(updateQuery);
                System.out.println("Withdrawn " + amount + " from Account Number " + accountNumber);
                updateStmt.close();
            } else {
                System.out.println("Insufficient balance");
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int getBalance(int accountNumber) {
        int balance = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Get current account balance
            String query = "SELECT Account_Balance FROM accountDetails WHERE Account_Number = " + accountNumber;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            balance = rs.getInt("Account_Balance");
            rs.close();
            stmt.close();

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return balance;
    }
}
