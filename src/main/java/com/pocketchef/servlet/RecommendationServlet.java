package com.pocketchef.servlet;

import com.pocketchef.dao.RecommendationDAO;
import com.pocketchef.model.Recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/recommend")
public class RecommendationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }

        String[] ingredientParameters =
                request.getParameterValues("ingredientIds");

        List<Integer> ingredientIds =
                new ArrayList<>();

        if (ingredientParameters != null) {

            for (String value : ingredientParameters) {

                try {

                    ingredientIds.add(
                            Integer.parseInt(value)
                    );

                } catch (NumberFormatException ignored) {
                }
            }
        }

        int cookingTime = 60;

        try {

            String time =
                    request.getParameter("cookingTime");

            if (time != null &&
                    !time.isEmpty()) {

                cookingTime =
                        Integer.parseInt(time);
            }

        } catch (NumberFormatException ignored) {
        }

        String foodPreference =
                request.getParameter("foodPreference");

        if (foodPreference == null ||
                foodPreference.isEmpty()) {

            foodPreference = "Any";
        }

        RecommendationDAO recommendationDAO =
                new RecommendationDAO();

        List<Recipe> recipes =
                recommendationDAO.getRecommendedRecipes(
                        ingredientIds,
                        cookingTime,
                        foodPreference
                );

        response.getWriter().println(

                "<!DOCTYPE html>" +

                        "<html lang='en'>" +

                        "<head>" +

                        "<meta charset='UTF-8'>" +
                        "<link rel=\"stylesheet\" href=\"style.css\">" +

                        "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>" +

                        "<title>PocketChef | Recommendations</title>" +

                        "<style>" +

                        "* {" +
                        "margin:0;" +
                        "padding:0;" +
                        "box-sizing:border-box;" +
                        "}" +

                        "body {" +
                        "font-family:'Segoe UI',Arial,sans-serif;" +
                        "min-height:100vh;" +
                        "background:linear-gradient(135deg,#fff7f2,#fff,#fff1eb);" +
                        "color:#263238;" +
                        "}" +

                        "header {" +
                        "height:72px;" +
                        "background:rgba(255,255,255,.96);" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:space-between;" +
                        "padding:0 55px;" +
                        "border-bottom:1px solid rgba(255,112,67,.12);" +
                        "box-shadow:0 4px 20px rgba(0,0,0,.05);" +
                        "position:sticky;" +
                        "top:0;" +
                        "z-index:10;" +
                        "}" +

                        ".logo {" +
                        "display:flex;" +
                        "align-items:center;" +
                        "gap:10px;" +
                        "font-size:27px;" +
                        "font-weight:800;" +
                        "color:#ff7043;" +
                        "}" +

                        ".logo-icon {" +
                        "width:42px;" +
                        "height:42px;" +
                        "border-radius:13px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "background:linear-gradient(135deg,#ff7043,#ff9a76);" +
                        "color:white;" +
                        "font-size:22px;" +
                        "}" +

                        ".header-links {" +
                        "display:flex;" +
                        "align-items:center;" +
                        "gap:8px;" +
                        "}" +

                        ".header-links a {" +
                        "text-decoration:none;" +
                        "color:#667085;" +
                        "font-size:14px;" +
                        "font-weight:600;" +
                        "padding:10px 13px;" +
                        "border-radius:9px;" +
                        "transition:.2s;" +
                        "}" +

                        ".header-links a:hover {" +
                        "background:#fff0ea;" +
                        "color:#ff7043;" +
                        "}" +

                        ".container {" +
                        "width:92%;" +
                        "max-width:1100px;" +
                        "margin:auto;" +
                        "padding:45px 0 50px;" +
                        "}" +

                        ".hero {" +
                        "text-align:center;" +
                        "margin-bottom:35px;" +
                        "}" +

                        ".hero-icon {" +
                        "width:65px;" +
                        "height:65px;" +
                        "margin:0 auto 15px;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "border-radius:20px;" +
                        "background:linear-gradient(135deg,#fff0ea,#fff8f5);" +
                        "font-size:34px;" +
                        "}" +

                        ".hero h1 {" +
                        "font-size:34px;" +
                        "margin-bottom:8px;" +
                        "}" +

                        ".hero h1 span {" +
                        "color:#ff7043;" +
                        "}" +

                        ".hero p {" +
                        "color:#7a7f87;" +
                        "font-size:15px;" +
                        "}" +

                        ".summary {" +
                        "display:flex;" +
                        "justify-content:center;" +
                        "flex-wrap:wrap;" +
                        "gap:10px;" +
                        "margin-top:20px;" +
                        "}" +

                        ".summary span {" +
                        "background:white;" +
                        "border:1px solid #eee;" +
                        "padding:8px 14px;" +
                        "border-radius:20px;" +
                        "font-size:13px;" +
                        "font-weight:600;" +
                        "color:#667085;" +
                        "}" +

                        ".recipe-grid {" +
                        "display:grid;" +
                        "grid-template-columns:repeat(2,1fr);" +
                        "gap:24px;" +
                        "}" +

                        ".recipe-card {" +
                        "background:white;" +
                        "border-radius:22px;" +
                        "padding:28px;" +
                        "border:1px solid rgba(0,0,0,.04);" +
                        "box-shadow:0 10px 30px rgba(38,50,56,.07);" +
                        "transition:.3s;" +
                        "position:relative;" +
                        "overflow:hidden;" +
                        "}" +

                        ".recipe-card::before {" +
                        "content:'';" +
                        "position:absolute;" +
                        "top:0;" +
                        "left:0;" +
                        "width:100%;" +
                        "height:5px;" +
                        "background:linear-gradient(90deg,#ff7043,#ff4f81);" +
                        "}" +

                        ".recipe-card:hover {" +
                        "transform:translateY(-6px);" +
                        "box-shadow:0 18px 40px rgba(38,50,56,.12);" +
                        "}" +

                        ".recipe-top {" +
                        "display:flex;" +
                        "justify-content:space-between;" +
                        "align-items:flex-start;" +
                        "gap:15px;" +
                        "margin-bottom:15px;" +
                        "}" +

                        ".recipe-name {" +
                        "font-size:22px;" +
                        "font-weight:750;" +
                        "color:#263238;" +
                        "text-decoration:none;" +
                        "}" +

                        ".recipe-name:hover {" +
                        "color:#ff7043;" +
                        "}" +

                        ".recipe-emoji {" +
                        "width:55px;" +
                        "height:55px;" +
                        "border-radius:17px;" +
                        "background:#fff1eb;" +
                        "display:flex;" +
                        "align-items:center;" +
                        "justify-content:center;" +
                        "font-size:28px;" +
                        "flex-shrink:0;" +
                        "}" +

                        ".description {" +
                        "color:#707780;" +
                        "font-size:14px;" +
                        "line-height:1.6;" +
                        "margin-bottom:18px;" +
                        "}" +

                        ".badges {" +
                        "display:flex;" +
                        "flex-wrap:wrap;" +
                        "gap:8px;" +
                        "margin-bottom:20px;" +
                        "}" +

                        ".badge {" +
                        "padding:7px 11px;" +
                        "border-radius:20px;" +
                        "font-size:12px;" +
                        "font-weight:700;" +
                        "background:#fff5f1;" +
                        "color:#ff7043;" +
                        "}" +

                        ".badge.green {" +
                        "background:#eefaf1;" +
                        "color:#26934d;" +
                        "}" +

                        ".actions {" +
                        "display:flex;" +
                        "gap:10px;" +
                        "flex-wrap:wrap;" +
                        "}" +

                        ".details-btn," +
                        ".favorite-btn {" +
                        "flex:1;" +
                        "min-width:150px;" +
                        "padding:12px 15px;" +
                        "border-radius:10px;" +
                        "text-align:center;" +
                        "text-decoration:none;" +
                        "font-size:13px;" +
                        "font-weight:700;" +
                        "transition:.2s;" +
                        "}" +

                        ".details-btn {" +
                        "background:#ff7043;" +
                        "color:white;" +
                        "}" +

                        ".details-btn:hover {" +
                        "background:#f4511e;" +
                        "transform:translateY(-2px);" +
                        "}" +

                        ".favorite-btn {" +
                        "background:#fff0f3;" +
                        "color:#e91e63;" +
                        "border:1px solid #ffd5df;" +
                        "}" +

                        ".favorite-btn:hover {" +
                        "background:#ffe3ea;" +
                        "transform:translateY(-2px);" +
                        "}" +

                        ".empty {" +
                        "background:white;" +
                        "border-radius:22px;" +
                        "padding:55px 30px;" +
                        "text-align:center;" +
                        "box-shadow:0 10px 30px rgba(38,50,56,.07);" +
                        "}" +

                        ".empty-icon {" +
                        "font-size:55px;" +
                        "margin-bottom:15px;" +
                        "}" +

                        ".empty h2 {" +
                        "margin-bottom:10px;" +
                        "}" +

                        ".empty p {" +
                        "color:#777;" +
                        "margin-bottom:25px;" +
                        "}" +

                        ".back-btn {" +
                        "display:inline-block;" +
                        "padding:12px 22px;" +
                        "border-radius:10px;" +
                        "background:#ff7043;" +
                        "color:white;" +
                        "text-decoration:none;" +
                        "font-weight:700;" +
                        "font-size:14px;" +
                        "}" +

                        "footer {" +
                        "text-align:center;" +
                        "color:#92969c;" +
                        "font-size:13px;" +
                        "padding:10px 20px 30px;" +
                        "}" +

                        "footer span {" +
                        "color:#ff7043;" +
                        "font-weight:600;" +
                        "}" +

                        "@media(max-width:750px) {" +
                        ".recipe-grid{grid-template-columns:1fr;}" +
                        "header{padding:0 20px;}" +
                        ".header-links a:nth-child(n+3){display:none;}" +
                        ".hero h1{font-size:28px;}" +
                        "}" +

                        "</style>" +

                        "</head>" +

                        "<body>" +

                        "<header>" +

                        "<div class='logo'>" +
                        "<div class='logo-icon'>🍳</div>" +
                        "PocketChef" +
                        "</div>" +

                        "<div class='header-links'>" +

                        "<a href='dashboard.html'>🏠 Dashboard</a>" +

                        "<a href='ingredients.html'>🔎 New Search</a>" +

                        "<a href='favorites'>❤️ Favorites</a>" +

                        "<a href='profile'>👤 Profile</a>" +

                        "<a href='logout'>Logout</a>" +

                        "</div>" +

                        "</header>" +

                        "<div class='container'>" +

                        "<div class='hero'>" +

                        "<div class='hero-icon'>🍽️</div>" +

                        "<h1>" +
                        "Your <span>Recipe Recommendations</span>" +
                        "</h1>" +

                        "<p>" +
                        "Here are recipes that match your ingredients and preferences." +
                        "</p>" +

                        "<div class='summary'>" +

                        "<span>⏱️ Up to " +
                        cookingTime +
                        " minutes</span>" +

                        "<span>🍽️ " +
                        escapeHtml(foodPreference) +
                        "</span>" +

                        "</div>" +

                        "</div>"
        );

        if (recipes.isEmpty()) {

            response.getWriter().println(

                    "<div class='empty'>" +

                            "<div class='empty-icon'>😕</div>" +

                            "<h2>No Recipes Found</h2>" +

                            "<p>" +
                            "Try selecting more ingredients, " +
                            "increasing the cooking time, " +
                            "or changing your food preference." +
                            "</p>" +

                            "<a class='back-btn' " +
                            "href='ingredients.html'>" +
                            "← Try Again" +
                            "</a>" +

                            "</div>"
            );

        } else {

            response.getWriter().println(
                    "<div class='recipe-grid'>"
            );

            for (Recipe recipe : recipes) {

                String emoji = "🍽️";

                String recipeName =
                        recipe.getRecipeName();

                if (recipeName != null) {

                    String lower =
                            recipeName.toLowerCase();

                    if (lower.contains("chicken")) {
                        emoji = "🍗";
                    } else if (lower.contains("paneer")) {
                        emoji = "🧀";
                    } else if (lower.contains("egg")) {
                        emoji = "🥚";
                    } else if (lower.contains("rice")) {
                        emoji = "🍚";
                    } else if (lower.contains("sandwich")) {
                        emoji = "🥪";
                    } else if (lower.contains("omelette")) {
                        emoji = "🍳";
                    } else if (lower.contains("potato") ||
                            lower.contains("aloo")) {
                        emoji = "🥔";
                    }
                }

                response.getWriter().println(

                        "<div class='recipe-card'>" +

                                "<div class='recipe-top'>" +

                                "<a class='recipe-name' " +
                                "href='recipe-details?id=" +
                                recipe.getRecipeId() +
                                "'>" +

                                escapeHtml(
                                        recipe.getRecipeName()
                                ) +

                                "</a>" +

                                "<div class='recipe-emoji'>" +
                                emoji +
                                "</div>" +

                                "</div>" +

                                "<p class='description'>" +

                                escapeHtml(
                                        recipe.getDescription()
                                ) +

                                "</p>" +

                                "<div class='badges'>" +

                                "<span class='badge'>" +
                                "⏱️ " +
                                recipe.getCookingTime() +
                                " min" +
                                "</span>" +

                                "<span class='badge green'>" +
                                "🍽️ " +
                                escapeHtml(
                                        recipe.getFoodPreference()
                                ) +
                                "</span>" +

                                "</div>" +

                                "<div class='actions'>" +

                                "<a class='details-btn' " +
                                "href='recipe-details?id=" +
                                recipe.getRecipeId() +
                                "'>" +
                                "View Recipe Details" +
                                "</a>" +

                                "<a class='favorite-btn' " +
                                "href='favorite?recipeId=" +
                                recipe.getRecipeId() +
                                "'>" +
                                "❤️ Add to Favorite" +
                                "</a>" +

                                "</div>" +

                                "</div>"
                );
            }

            response.getWriter().println(
                    "</div>"
            );
        }

        response.getWriter().println(

                "</div>" +

                        "<footer>" +

                        "© 2026 " +
                        "<span>PocketChef</span>" +
                        " | Smart Recipe Recommendation System" +

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