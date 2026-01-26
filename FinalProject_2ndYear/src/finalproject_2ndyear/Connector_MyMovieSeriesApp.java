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
public class Connector_MyMovieSeriesApp {
     private static Connection AppConnection = null;

    public static Connection getConnection() {
        if (AppConnection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                AppConnection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/myapp_movieseries", //NAME SA DATA BASE
                        "root", "root"
                );
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Connection failed! Check your database credentials and server.");
                e.printStackTrace();
            }
        }
        return AppConnection;
    }
}
