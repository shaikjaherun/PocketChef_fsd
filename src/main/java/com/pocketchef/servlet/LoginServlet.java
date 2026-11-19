package com.pocketchef.servlet;

import com.pocketchef.dao.UserDAO;
import com.pocketchef.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.loginUser(
                        email,
                        password
                );

        if (user != null) {

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "userId",
                    user.getUserId()
            );

            session.setAttribute(
                    "userName",
                    user.getName()
            );

            session.setAttribute(
                    "userEmail",
                    user.getEmail()
            );

            // Login successful → Dashboard
            response.sendRedirect(
                    "dashboard.html"
            );

        } else {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<meta charset='UTF-8'>" +
                            "<link rel=\"stylesheet\" href=\"style.css\">" +
                            "<title>Login Failed</title>" +
                            "</head>" +
                            "<body>" +

                            "<h2>Login Failed!</h2>" +

                            "<p>" +
                            "Invalid email or password." +
                            "</p>" +

                            "<a href='login.html'>" +
                            "Back to Login" +
                            "</a>" +

                            "</body>" +
                            "</html>"
            );
        }
    }
}