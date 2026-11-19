package com.pocketchef.test;

import com.pocketchef.util.DBConnection;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {

            System.out.println("PocketChef database connection test PASSED!");

            try {
                connection.close();
                System.out.println("Database connection closed.");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("PocketChef database connection test FAILED!");
        }
    }
}