package com.pocketchef.test;

import com.pocketchef.dao.UserDAO;
import com.pocketchef.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Login test
        String email = "pocketchef@gmail.com";
        String password = "123456";

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            System.out.println("Login SUCCESSFUL!");
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());

        } else {

            System.out.println("Login FAILED!");
        }
    }
}