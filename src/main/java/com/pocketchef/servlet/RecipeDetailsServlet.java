package com.pocketchef.servlet;

import com.pocketchef.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/recipe-details")
public class RecipeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendRedirect("ingredients.html");
            return;
        }

        int recipeId;

        try {
            recipeId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            response.sendRedirect("ingredients.html");
            return;
        }

        String recipeName = "";
        String description = "";
        String foodPreference = "";
        String instructions = "";
        int cookingTime = 0;

        try {

            Connection connection =
                    DBConnection.getConnection();

            String sql =
                    "SELECT recipe_name, description, " +
                            "cooking_time, food_preference, instructions " +
                            "FROM recipes WHERE recipe_id = ?";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, recipeId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (!resultSet.next()) {

                resultSet.close();
                statement.close();
                connection.close();

                response.sendRedirect("ingredients.html");
                return;
            }

            recipeName =
                    resultSet.getString("recipe_name");

            description =
                    resultSet.getString("description");

            cookingTime =
                    resultSet.getInt("cooking_time");

            foodPreference =
                    resultSet.getString("food_preference");

            instructions =
                    resultSet.getString("instructions");

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Error loading recipe.</h2>"
            );

            return;
        }

        response.getWriter().println(
                "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +

                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>" +
                        "<link rel=\"stylesheet\" href=\"style.css\">" +

                        "<title>PocketChef | Recipe Details</title>" +

                        "<style>" +

                        "*{box-sizing:border-box;margin:0;padding:0;}" +

                        "body{" +
                        "font-family:'Segoe UI',Arial,sans-serif;" +
                        "background:linear-gradient(135deg,#fff7f2,#fff,#fff1eb);" +
                        "color:#263238;" +
                        "min-height:100vh;" +
                        "}" +

                        "header{" +
                        "height:72px;" +
                        "background:white;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:space-between;" +
                        "padding:0 55px;" +
                        "box-shadow:0 4px 20px rgba(0,0,0,.06);" +
                        "position:sticky;" +
                        "top:0;" +
                        "z-index:10;" +
                        "}" +

                        ".logo{" +
                        "display:flex;" +
                        "align-items:center;" +
                        "gap:10px;" +
                        "font-size:27px;" +
                        "font-weight:800;" +
                        "color:#ff7043;" +
                        "}" +

                        ".logo-icon{" +
                        "width:42px;height:42px;" +
                        "border-radius:13px;" +
                        "background:linear-gradient(135deg,#ff7043,#ff9a76);" +
                        "display:flex;align-items:center;justify-content:center;" +
                        "font-size:22px;" +
                        "color:white;" +
                        "}" +

                        ".nav{" +
                        "display:flex;" +
                        "gap:8px;" +
                        "}" +

                        ".nav a{" +
                        "text-decoration:none;" +
                        "color:#667085;" +
                        "font-size:14px;" +
                        "font-weight:600;" +
                        "padding:10px 13px;" +
                        "border-radius:9px;" +
                        "}" +

                        ".nav a:hover{" +
                        "background:#fff0ea;" +
                        "color:#ff7043;" +
                        "}" +

                        ".container{" +
                        "width:92%;" +
                        "max-width:900px;" +
                        "margin:45px auto;" +
                        "}" +

                        ".recipe-card{" +
                        "background:white;" +
                        "border-radius:25px;" +
                        "padding:40px;" +
                        "box-shadow:0 15px 40px rgba(38,50,56,.08);" +
                        "position:relative;" +
                        "overflow:hidden;" +
                        "}" +

                        ".recipe-card:before{" +
                        "content:'';" +
                        "position:absolute;" +
                        "top:0;left:0;width:100%;height:6px;" +
                        "background:linear-gradient(90deg,#ff7043,#ff4f81);" +
                        "}" +

                        ".title-area{" +
                        "display:flex;" +
                        "justify-content:space-between;" +
                        "align-items:flex-start;" +
                        "gap:20px;" +
                        "margin-bottom:20px;" +
                        "}" +

                        "h1{" +
                        "font-size:36px;" +
                        "color:#263238;" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".food-icon{" +
                        "width:75px;height:75px;" +
                        "border-radius:22px;" +
                        "background:#fff1eb;" +
                        "display:flex;align-items:center;" +
                        "justify-content:center;" +
                        "font-size:40px;" +
                        "flex-shrink:0;" +
                        "}" +

                        ".description{" +
                        "font-size:16px;" +
                        "line-height:1.7;" +
                        "color:#707780;" +
                        "margin-bottom:22px;" +
                        "}" +

                        ".tags{" +
                        "display:flex;" +
                        "gap:10px;" +
                        "flex-wrap:wrap;" +
                        "margin-bottom:30px;" +
                        "}" +

                        ".tag{" +
                        "padding:9px 14px;" +
                        "border-radius:20px;" +
                        "background:#fff1eb;" +
                        "color:#ff7043;" +
                        "font-size:13px;" +
                        "font-weight:700;" +
                        "}" +

                        ".tag.green{" +
                        "background:#eefaf1;" +
                        "color:#26934d;" +
                        "}" +

                        ".section{" +
                        "margin-top:25px;" +
                        "}" +

                        ".section h2{" +
                        "font-size:21px;" +
                        "margin-bottom:12px;" +
                        "}" +

                        ".instructions{" +
                        "background:#fffaf7;" +
                        "border-left:5px solid #ff7043;" +
                        "border-radius:12px;" +
                        "padding:22px;" +
                        "line-height:1.8;" +
                        "color:#555;" +
                        "white-space:pre-line;" +
                        "}" +

                        ".buttons{" +
                        "display:flex;" +
                        "gap:12px;" +
                        "margin-top:30px;" +
                        "flex-wrap:wrap;" +
                        "}" +

                        ".btn{" +
                        "padding:13px 22px;" +
                        "border-radius:10px;" +
                        "text-decoration:none;" +
                        "font-weight:700;" +
                        "font-size:14px;" +
                        "}" +

                        ".primary{" +
                        "background:#ff7043;color:white;" +
                        "}" +

                        ".secondary{" +
                        "background:#fff0ea;color:#ff7043;" +
                        "}" +

                        "footer{" +
                        "text-align:center;" +
                        "padding:20px;" +
                        "color:#92969c;" +
                        "font-size:13px;" +
                        "}" +

                        "footer span{" +
                        "color:#ff7043;font-weight:700;" +
                        "}" +

                        "@media(max-width:650px){" +
                        "header{padding:0 20px;}" +
                        ".nav a:nth-child(n+3){display:none;}" +
                        ".recipe-card{padding:28px 22px;}" +
                        "h1{font-size:28px;}" +
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
                        "<a href='profile'>👤 Profile</a>" +
                        "</div>" +

                        "</header>" +

                        "<div class='container'>" +

                        "<div class='recipe-card'>" +

                        "<div class='title-area'>" +

                        "<div>" +

                        "<h1>" +
                        escapeHtml(recipeName) +
                        "</h1>" +

                        "<p class='description'>" +
                        escapeHtml(description) +
                        "</p>" +

                        "</div>" +

                        "<div class='food-icon'>🍽️</div>" +

                        "</div>" +

                        "<div class='tags'>" +

                        "<span class='tag'>" +
                        "⏱️ " + cookingTime + " minutes" +
                        "</span>" +

                        "<span class='tag green'>" +
                        "🍽️ " +
                        escapeHtml(foodPreference) +
                        "</span>" +

                        "</div>" +

                        "<div class='section'>" +

                        "<h2>👨‍🍳 Cooking Instructions</h2>" +

                        "<div class='instructions'>" +
                        escapeHtml(instructions) +
                        "</div>" +

                        "</div>" +

                        "<div class='buttons'>" +

                        "<a class='btn primary' " +
                        "href='ingredients.html'>" +
                        "← Find More Recipes" +
                        "</a>" +

                        "<a class='btn secondary' " +
                        "href='favorites'>" +
                        "❤️ My Favorites" +
                        "</a>" +

                        "</div>" +

                        "</div>" +

                        "</div>" +

                        "<footer>" +
                        "© 2026 <span>PocketChef</span> | " +
                        "Smart Recipe Recommendation System" +
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