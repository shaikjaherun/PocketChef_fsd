package com.pocketchef.servlet;

import com.pocketchef.dao.UserDAO;
import com.pocketchef.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get data from registration form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create User object
        User user = new User(name, email, password);

        // Save user into database
        UserDAO userDAO = new UserDAO();

        boolean result = userDAO.registerUser(user);

        // Send response
        response.setContentType("text/html");

        if (result) {
            response.getWriter().println(
                    "<h2>Registration Successful!</h2>"
            );
        } else {
            response.getWriter().println(
                    "<h2>Registration Failed!</h2>"
            );
        }
    }
}