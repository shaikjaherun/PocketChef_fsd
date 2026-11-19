package com.pocketchef.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/pocketchef";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "jahe2506";

    public static Connection getConnection() {

        try {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println(
                    "Database connected successfully!"
            );

            return connection;

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "MySQL JDBC Driver not found!"
            );

            throw new RuntimeException(e);

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}