/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_2ndyear;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ConnectToSql{
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/myapp_movieseries";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    // Create a new connection each time
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check your database credentials and server.");
            e.printStackTrace();
        }
        return conn;
    }
}
