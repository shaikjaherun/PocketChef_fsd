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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

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
                "SELECT name, email " +
                        "FROM users " +
                        "WHERE user_id = ?";

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

                        "<html lang='en'>" +

                        "<head>" +

                        "<meta charset='UTF-8'>" +
                        "<link rel=\"stylesheet\" href=\"style.css\">" +

                        "<meta name='viewport' " +
                        "content='width=device-width, " +
                        "initial-scale=1.0'>" +

                        "<title>PocketChef | My Profile</title>" +

                        "<style>" +

                        "* {" +
                        "box-sizing: border-box;" +
                        "margin: 0;" +
                        "padding: 0;" +
                        "}" +

                        "body {" +
                        "font-family: 'Segoe UI', Arial, sans-serif;" +

                        "background: linear-gradient(" +
                        "135deg, #fff7f2, #ffffff, #fff1eb);" +

                        "color: #263238;" +
                        "min-height: 100vh;" +
                        "}" +

                        "header {" +
                        "height: 72px;" +
                        "background: white;" +
                        "display: flex;" +
                        "align-items: center;" +
                        "justify-content: space-between;" +
                        "padding: 0 50px;" +
                        "box-shadow: 0 4px 20px " +
                        "rgba(0,0,0,0.06);" +
                        "position: sticky;" +
                        "top: 0;" +
                        "z-index: 10;" +
                        "}" +

                        ".logo {" +
                        "display: flex;" +
                        "align-items: center;" +
                        "gap: 10px;" +
                        "font-size: 27px;" +
                        "font-weight: 800;" +
                        "color: #ff7043;" +
                        "}" +

                        ".logo-icon {" +
                        "width: 42px;" +
                        "height: 42px;" +
                        "border-radius: 13px;" +
                        "background: linear-gradient(" +
                        "135deg, #ff7043, #ff9a76);" +
                        "display: flex;" +
                        "align-items: center;" +
                        "justify-content: center;" +
                        "font-size: 22px;" +
                        "}" +

                        ".nav {" +
                        "display: flex;" +
                        "gap: 8px;" +
                        "}" +

                        ".nav a {" +
                        "text-decoration: none;" +
                        "color: #667085;" +
                        "font-size: 14px;" +
                        "font-weight: 600;" +
                        "padding: 10px 13px;" +
                        "border-radius: 9px;" +
                        "}" +

                        ".nav a:hover {" +
                        "background: #fff0ea;" +
                        "color: #ff7043;" +
                        "}" +

                        ".container {" +
                        "width: 92%;" +
                        "max-width: 580px;" +
                        "margin: 50px auto;" +
                        "}" +

                        ".profile-card {" +
                        "background: white;" +
                        "padding: 40px;" +
                        "border-radius: 25px;" +
                        "box-shadow: 0 15px 40px " +
                        "rgba(38,50,56,0.08);" +
                        "position: relative;" +
                        "overflow: hidden;" +
                        "}" +

                        ".profile-card:before {" +
                        "content: '';" +
                        "position: absolute;" +
                        "top: 0;" +
                        "left: 0;" +
                        "width: 100%;" +
                        "height: 6px;" +
                        "background: linear-gradient(" +
                        "90deg, #ff7043, #ff4f81);" +
                        "}" +

                        ".profile-icon {" +
                        "width: 95px;" +
                        "height: 95px;" +
                        "margin: 5px auto 20px;" +
                        "border-radius: 50%;" +
                        "background: linear-gradient(" +
                        "135deg, #ff7043, #ff8b66);" +
                        "color: white;" +
                        "display: flex;" +
                        "align-items: center;" +
                        "justify-content: center;" +
                        "font-size: 42px;" +
                        "box-shadow: 0 10px 25px " +
                        "rgba(255,112,67,0.20);" +
                        "}" +

                        "h2 {" +
                        "text-align: center;" +
                        "font-size: 30px;" +
                        "margin-bottom: 30px;" +
                        "}" +

                        ".field {" +
                        "margin-bottom: 20px;" +
                        "}" +

                        ".label {" +
                        "font-size: 12px;" +
                        "font-weight: 800;" +
                        "color: #667085;" +
                        "margin-bottom: 7px;" +
                        "text-transform: uppercase;" +
                        "letter-spacing: .5px;" +
                        "}" +

                        ".value {" +
                        "padding: 14px 16px;" +
                        "background: #fffaf7;" +
                        "border: 1px solid #f0ddd6;" +
                        "border-radius: 11px;" +
                        "font-size: 16px;" +
                        "color: #374151;" +
                        "}" +

                        ".edit-btn {" +
                        "display: block;" +
                        "text-align: center;" +
                        "margin-top: 25px;" +
                        "padding: 14px;" +
                        "background: linear-gradient(" +
                        "135deg, #ff7043, #ff5d38);" +
                        "color: white;" +
                        "text-decoration: none;" +
                        "border-radius: 10px;" +
                        "font-weight: 700;" +
                        "box-shadow: 0 8px 18px " +
                        "rgba(255,112,67,0.22);" +
                        "}" +

                        ".back-btn {" +
                        "display: block;" +
                        "text-align: center;" +
                        "margin-top: 12px;" +
                        "padding: 14px;" +
                        "background: #fff0eb;" +
                        "color: #ff7043;" +
                        "text-decoration: none;" +
                        "border-radius: 10px;" +
                        "font-weight: 700;" +
                        "}" +

                        "footer {" +
                        "text-align: center;" +
                        "padding: 20px;" +
                        "color: #92969c;" +
                        "font-size: 13px;" +
                        "}" +

                        "footer span {" +
                        "color: #ff7043;" +
                        "font-weight: 700;" +
                        "}" +

                        "@media(max-width:700px) {" +

                        "header {" +
                        "padding: 0 20px;" +
                        "}" +

                        ".nav a:nth-child(n+3) {" +
                        "display: none;" +
                        "}" +

                        ".profile-card {" +
                        "padding: 30px 22px;" +
                        "}" +

                        "h2 {" +
                        "font-size: 27px;" +
                        "}" +

                        "}" +

                        "</style>" +

                        "</head>" +

                        "<body>" +

                        "<header>" +

                        "<div class='logo'>" +

                        "<div class='logo-icon'>🍳</div>" +

                        "PocketChef" +

                        "</div>" +

                        "<div class='nav'>" +

                        "<a href='dashboard.html'>🏠 Dashboard</a>" +

                        "<a href='ingredients.html'>🔎 Recipes</a>" +

                        "<a href='favorites'>❤️ Favorites</a>" +

                        "<a href='search-history'>📋 History</a>" +

                        "<a href='logout'>🚪 Logout</a>" +

                        "</div>" +

                        "</header>" +

                        "<div class='container'>" +

                        "<div class='profile-card'>" +

                        "<div class='profile-icon'>👤</div>" +

                        "<h2>My Profile</h2>" +

                        "<div class='field'>" +

                        "<div class='label'>Full Name</div>" +

                        "<div class='value'>" +

                        escapeHtml(name) +

                        "</div>" +

                        "</div>" +

                        "<div class='field'>" +

                        "<div class='label'>Email Address</div>" +

                        "<div class='value'>" +

                        escapeHtml(email) +

                        "</div>" +

                        "</div>" +

                        "<a href='edit-profile' " +
                        "class='edit-btn'>" +

                        "✏️ Edit Profile" +

                        "</a>" +

                        "<a href='dashboard.html' " +
                        "class='back-btn'>" +

                        "← Back to Dashboard" +

                        "</a>" +

                        "</div>" +

                        "</div>" +

                        "<footer>" +

                        "© 2026 " +
                        "<span>PocketChef</span> " +
                        "| Smart Recipe Recommendation System" +

                        "</footer>" +

                        "</body>" +

                        "</html>"
        );
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