package com.pocketchef.servlet;

import com.pocketchef.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String name = "";
        String email = "";

        String sql =
                "SELECT name, email FROM users WHERE user_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                name =
                        resultSet.getString("name");

                email =
                        resultSet.getString("email");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        response.getWriter().println(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<link rel=\"stylesheet\" href=\"style.css\">" +
                        "<title>Edit Profile</title>" +

                        "<style>" +

                        "body {" +
                        "margin: 0;" +
                        "font-family: Arial, sans-serif;" +
                        "background: #f5f7fa;" +
                        "}" +

                        ".container {" +
                        "width: 400px;" +
                        "margin: 60px auto;" +
                        "background: white;" +
                        "padding: 30px;" +
                        "border-radius: 15px;" +
                        "box-shadow: 0 5px 20px rgba(0,0,0,0.1);" +
                        "}" +

                        "h2 {" +
                        "text-align: center;" +
                        "color: #333;" +
                        "margin-bottom: 30px;" +
                        "}" +

                        ".profile-icon {" +
                        "width: 80px;" +
                        "height: 80px;" +
                        "margin: 0 auto 20px;" +
                        "border-radius: 50%;" +
                        "background: #ff7043;" +
                        "color: white;" +
                        "display: flex;" +
                        "align-items: center;" +
                        "justify-content: center;" +
                        "font-size: 35px;" +
                        "}" +

                        "label {" +
                        "display: block;" +
                        "margin-bottom: 7px;" +
                        "color: #555;" +
                        "font-size: 14px;" +
                        "}" +

                        "input {" +
                        "width: 100%;" +
                        "box-sizing: border-box;" +
                        "padding: 12px;" +
                        "margin-bottom: 18px;" +
                        "border: 1px solid #ddd;" +
                        "border-radius: 8px;" +
                        "font-size: 15px;" +
                        "}" +

                        "button {" +
                        "width: 100%;" +
                        "padding: 12px;" +
                        "border: none;" +
                        "border-radius: 8px;" +
                        "background: #ff7043;" +
                        "color: white;" +
                        "font-size: 16px;" +
                        "cursor: pointer;" +
                        "}" +

                        "button:hover {" +
                        "background: #f4511e;" +
                        "}" +

                        ".back-btn {" +
                        "display: block;" +
                        "text-align: center;" +
                        "margin-top: 15px;" +
                        "padding: 12px;" +
                        "background: #eeeeee;" +
                        "color: #333;" +
                        "text-decoration: none;" +
                        "border-radius: 8px;" +
                        "}" +

                        "</style>" +
                        "</head>" +

                        "<body>" +

                        "<div class='container'>" +

                        "<div class='profile-icon'>👤</div>" +

                        "<h2>Edit Profile</h2>" +

                        "<form action='edit-profile' method='post'>" +

                        "<label>Name</label>" +

                        "<input type='text' " +
                        "name='name' " +
                        "value='" + escapeHtml(name) + "' " +
                        "required>" +

                        "<label>Email</label>" +

                        "<input type='email' " +
                        "name='email' " +
                        "value='" + escapeHtml(email) + "' " +
                        "required>" +

                        "<label>New Password</label>" +

                        "<input type='password' " +
                        "name='password' " +
                        "placeholder='Enter new password'>" +

                        "<button type='submit'>" +
                        "Update Profile" +
                        "</button>" +

                        "</form>" +

                        "<a href='profile' class='back-btn'>" +
                        "Cancel" +
                        "</a>" +

                        "</div>" +

                        "</body>" +
                        "</html>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        try {

            Connection connection =
                    DBConnection.getConnection();

            int result;

            if (password == null ||
                    password.trim().isEmpty()) {

                String sql =
                        "UPDATE users " +
                                "SET name = ?, email = ? " +
                                "WHERE user_id = ?";

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setInt(3, userId);

                result =
                        preparedStatement.executeUpdate();

                preparedStatement.close();

            } else {

                String sql =
                        "UPDATE users " +
                                "SET name = ?, email = ?, password = ? " +
                                "WHERE user_id = ?";

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setInt(4, userId);

                result =
                        preparedStatement.executeUpdate();

                preparedStatement.close();
            }

            connection.close();

            if (result > 0) {

                session.setAttribute(
                        "userName",
                        name
                );

                session.setAttribute(
                        "userEmail",
                        email
                );

                response.sendRedirect("profile");

            } else {

                response.getWriter().println(
                        "<h2>Profile update failed.</h2>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Error updating profile.</h2>"
            );
        }
    }

    private String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}