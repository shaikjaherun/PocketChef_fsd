package com.pocketchef.servlet;

import com.pocketchef.model.Recipe;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/favorites", "/favorite"})
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.html"
            );
            return;
        }

        String recipeIdParameter =
                request.getParameter("recipeId");

        if (recipeIdParameter == null ||
                recipeIdParameter.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/ingredients.html"
            );
            return;
        }

        try {

            int userId =
                    (Integer) session.getAttribute("userId");

            int recipeId =
                    Integer.parseInt(recipeIdParameter);

            String action =
                    request.getParameter("action");

            if ("remove".equals(action)) {
                removeFavorite(userId, recipeId);
            } else {
                addFavorite(userId, recipeId);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect(
                request.getContextPath() + "/favorites"
        );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.html"
            );
            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String removeRecipeId =
                request.getParameter("remove");

        if (removeRecipeId != null &&
                !removeRecipeId.isEmpty()) {

            try {

                int recipeId =
                        Integer.parseInt(removeRecipeId);

                removeFavorite(userId, recipeId);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect(
                    request.getContextPath() + "/favorites"
            );

            return;
        }

        List<Recipe> favoriteRecipes =
                getFavoriteRecipes(userId);

        String userName =
                (String) session.getAttribute("userName");

        response.getWriter().println("""
            <!DOCTYPE html>
            <html lang="en">

            <head>

                <meta charset="UTF-8">

                <meta name="viewport"
                      content="width=device-width,
                               initial-scale=1.0">
                 <link rel="stylesheet" href="style.css">

                <title>PocketChef | Favorites</title>

                <style>

                    * {
                        box-sizing: border-box;
                        margin: 0;
                        padding: 0;
                    }

                    body {
                        font-family:
                            'Segoe UI',
                            Arial,
                            sans-serif;

                        background:
                            linear-gradient(
                                135deg,
                                #fff7f2,
                                #ffffff,
                                #fff1eb
                            );

                        color: #263238;
                        min-height: 100vh;
                    }

                    header {
                        height: 72px;
                        background: white;

                        display: flex;
                        align-items: center;
                        justify-content: space-between;

                        padding: 0 50px;

                        box-shadow:
                            0 4px 20px
                            rgba(0,0,0,0.06);

                        position: sticky;
                        top: 0;
                        z-index: 10;
                    }

                    .logo {
                        display: flex;
                        align-items: center;
                        gap: 10px;

                        font-size: 27px;
                        font-weight: 800;
                        color: #ff7043;
                    }

                    .logo-icon {
                        width: 42px;
                        height: 42px;

                        border-radius: 13px;

                        background:
                            linear-gradient(
                                135deg,
                                #ff7043,
                                #ff9a76
                            );

                        display: flex;
                        align-items: center;
                        justify-content: center;

                        font-size: 22px;
                    }

                    .nav {
                        display: flex;
                        gap: 8px;
                    }

                    .nav a {
                        text-decoration: none;
                        color: #667085;

                        font-size: 14px;
                        font-weight: 600;

                        padding: 10px 13px;
                        border-radius: 9px;
                    }

                    .nav a:hover {
                        background: #fff0ea;
                        color: #ff7043;
                    }

                    .container {
                        width: 92%;
                        max-width: 1000px;

                        margin: 45px auto;
                    }

                    .heading {
                        text-align: center;
                        margin-bottom: 35px;
                    }

                    .heading-icon {
                        font-size: 38px;
                        margin-bottom: 10px;
                    }

                    .heading h1 {
                        font-size: 32px;
                        margin-bottom: 8px;
                    }

                    .heading p {
                        color: #7b8490;
                        font-size: 15px;
                    }

                    .recipe-card {
                        background: white;

                        padding: 28px;

                        margin-bottom: 20px;

                        border-radius: 20px;

                        box-shadow:
                            0 12px 35px
                            rgba(38,50,56,0.08);

                        border-left:
                            5px solid #ff7043;
                    }

                    .recipe-card h2 {
                        color: #263238;
                        margin-bottom: 12px;
                        font-size: 22px;
                    }

                    .recipe-card p {
                        line-height: 1.6;
                        color: #737b84;
                        margin-bottom: 12px;
                    }

                    .tags {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 8px;
                        margin-bottom: 15px;
                    }

                    .tag {
                        padding: 8px 13px;

                        background: #fff0eb;
                        color: #ff7043;

                        border-radius: 20px;

                        font-size: 13px;
                        font-weight: 700;
                    }

                    .tag.green {
                        background: #eefaf1;
                        color: #26934d;
                    }

                    .instructions {
                        background: #fffaf7;

                        padding: 18px;

                        border-radius: 12px;

                        margin-top: 15px;

                        color: #707780;

                        line-height: 1.7;
                    }

                    .buttons {
                        display: flex;
                        gap: 10px;
                        margin-top: 20px;
                    }

                    .view-btn {
                        display: inline-block;

                        padding: 11px 20px;

                        background: #ff7043;
                        color: white;

                        text-decoration: none;

                        border-radius: 9px;

                        font-weight: 700;
                    }

                    .remove-btn {
                        width: 100%;

                        margin-top: 18px;

                        padding: 12px;

                        background: #fff0eb;
                        color: #ff7043;

                        border: none;
                        border-radius: 10px;

                        font-weight: 700;
                        cursor: pointer;
                    }

                    .remove-btn:hover {
                        background: #ffe0d6;
                    }

                    .no-favorites {
                        background: white;

                        padding: 55px 30px;

                        text-align: center;

                        border-radius: 22px;

                        box-shadow:
                            0 12px 35px
                            rgba(38,50,56,0.08);
                    }

                    .no-favorites .icon {
                        font-size: 55px;
                        margin-bottom: 15px;
                    }

                    .no-favorites h2 {
                        color: #ff7043;
                        margin-bottom: 12px;
                    }

                    .no-favorites p {
                        color: #777;
                    }

                    .find-btn {
                        display: inline-block;

                        margin-top: 25px;

                        padding: 13px 25px;

                        background: #ff7043;
                        color: white;

                        text-decoration: none;

                        border-radius: 10px;

                        font-weight: 700;
                    }

                    footer {
                        text-align: center;
                        padding: 25px;

                        color: #92969c;
                        font-size: 13px;
                    }

                    footer span {
                        color: #ff7043;
                        font-weight: 700;
                    }

                    @media(max-width: 700px) {

                        header {
                            padding: 0 20px;
                        }

                        .nav a:nth-child(n+3) {
                            display: none;
                        }

                        .container {
                            margin-top: 30px;
                        }

                        .recipe-card {
                            padding: 22px;
                        }

                    }

                </style>

            </head>

            <body>

            <header>

                <div class="logo">

                    <div class="logo-icon">
                        🍳
                    </div>

                    PocketChef

                </div>

                <div class="nav">

                    <a href="dashboard.html">
                        🏠 Dashboard
                    </a>

                    <a href="ingredients.html">
                        🔎 Recipes
                    </a>

                    <a href="search-history">
                        📋 History
                    </a>

                    <a href="profile">
                        👤 Profile
                    </a>

                    <a href="logout">
                        🚪 Logout
                    </a>

                </div>

            </header>

            <div class="container">

                <div class="heading">

                    <div class="heading-icon">
                        ❤️
                    </div>

                    <h1>
                        My Favorite Recipes
                    </h1>

                    <p>
                        Your saved recipes, all in one place
                    </p>

                </div>
            """);

        if (favoriteRecipes.isEmpty()) {

            response.getWriter().println("""
                <div class="no-favorites">

                    <div class="icon">
                        ❤️
                    </div>

                    <h2>
                        No Favorite Recipes Yet
                    </h2>

                    <p>
                        Add your favorite recipes
                        and they will appear here.
                    </p>

                    <a class="find-btn"
                       href="ingredients.html">

                        🔎 Find Recipes

                    </a>

                </div>
                """);

        } else {

            for (Recipe recipe : favoriteRecipes) {

                response.getWriter().println(
                        "<div class=\"recipe-card\">"
                );

                response.getWriter().println(
                        "<h2>" +
                                escapeHtml(
                                        recipe.getRecipeName()
                                ) +
                                "</h2>"
                );

                response.getWriter().println(
                        "<p>" +
                                escapeHtml(
                                        recipe.getDescription()
                                ) +
                                "</p>"
                );

                response.getWriter().println(
                        "<div class=\"tags\">" +

                                "<span class=\"tag\">" +
                                "⏱️ " +
                                recipe.getCookingTime() +
                                " minutes" +
                                "</span>" +

                                "<span class=\"tag green\">" +
                                "🍽️ " +
                                escapeHtml(
                                        recipe.getFoodPreference()
                                ) +
                                "</span>" +

                                "</div>"
                );

                response.getWriter().println(
                        "<div class=\"instructions\">" +

                                "<strong>👨‍🍳 Instructions</strong>" +

                                "<br><br>" +

                                escapeHtml(
                                        recipe.getInstructions()
                                ) +

                                "</div>"
                );

                response.getWriter().println(
                        "<a class=\"remove-btn\" " +
                                "href=\"favorites?remove=" +
                                recipe.getRecipeId() +
                                "\">" +

                                "💔 Remove from Favorites" +

                                "</a>"
                );

                response.getWriter().println(
                        "</div>"
                );
            }

            response.getWriter().println("""
                <div style="text-align:center;">

                    <a class="find-btn"
                       href="ingredients.html">

                        ← Find More Recipes

                    </a>

                </div>
                """);
        }

        response.getWriter().println("""
            </div>

            <footer>
                © 2026
                <span>PocketChef</span>
                | Smart Recipe Recommendation System
            </footer>

            </body>

            </html>
            """);
    }

    private void addFavorite(
            int userId,
            int recipeId) {

        String sql =
                "INSERT IGNORE INTO favorites " +
                        "(user_id, recipe_id) VALUES (?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, recipeId);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Recipe> getFavoriteRecipes(
            int userId) {

        List<Recipe> recipes =
                new ArrayList<>();

        String sql =
                "SELECT r.recipe_id, r.recipe_name, " +
                        "r.description, r.cooking_time, " +
                        "r.food_preference, r.instructions " +
                        "FROM favorites f " +
                        "JOIN recipes r " +
                        "ON f.recipe_id = r.recipe_id " +
                        "WHERE f.user_id = ? " +
                        "ORDER BY f.created_at DESC";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Recipe recipe =
                        new Recipe(
                                resultSet.getInt("recipe_id"),
                                resultSet.getString("recipe_name"),
                                resultSet.getString("description"),
                                resultSet.getInt("cooking_time"),
                                resultSet.getString("food_preference"),
                                resultSet.getString("instructions")
                        );

                recipes.add(recipe);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private void removeFavorite(
            int userId,
            int recipeId) {

        String sql =
                "DELETE FROM favorites " +
                        "WHERE user_id = ? " +
                        "AND recipe_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, recipeId);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}