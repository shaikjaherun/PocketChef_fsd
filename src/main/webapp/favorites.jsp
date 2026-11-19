<%@ page import="java.util.List" %>
<%@ page import="com.pocketchef.model.Recipe" %>

<%
    String userName =
            (String) session.getAttribute("userName");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
          <link rel="stylesheet" href="style.css">

    <title>PocketChef | My Favorites</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;

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
            max-width: 1050px;

            width: 92%;

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
            color: #777;
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
        }

        .recipe-card p {
            line-height: 1.6;
            color: #737b84;
            margin-bottom: 12px;
        }

        .tag {
            display: inline-block;

            padding: 8px 13px;

            background: #fff0eb;
            color: #ff7043;

            border-radius: 20px;

            font-size: 13px;
            font-weight: 700;

            margin-right: 6px;
        }

        .instructions {
            background: #fffaf7;

            margin-top: 15px;

            padding: 18px;

            border-radius: 12px;

            color: #707780;

            line-height: 1.7;
        }

        .remove-btn {
            display: block;

            width: 100%;

            margin-top: 18px;

            padding: 12px;

            background: #fff0eb;
            color: #ff7043;

            border: none;

            border-radius: 10px;

            font-size: 14px;
            font-weight: bold;

            cursor: pointer;
        }

        .remove-btn:hover {
            background: #ffe0d6;
        }

        .no-favorites {
            background: white;

            padding: 55px 30px;

            border-radius: 22px;

            text-align: center;

            box-shadow:
                0 12px 35px
                rgba(38,50,56,0.08);
        }

        .no-favorites-icon {
            font-size: 55px;
            margin-bottom: 15px;
        }

        .no-favorites h2 {
            color: #ff7043;
            margin-bottom: 10px;
        }

        .no-favorites p {
            color: #777;
        }

        .button {
            display: inline-block;

            margin-top: 25px;

            padding: 13px 25px;

            background: #ff7043;
            color: white;

            text-decoration: none;

            border-radius: 10px;

            font-weight: bold;
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

        @media(max-width:700px) {

            header {
                padding: 0 20px;
            }

            .nav a:nth-child(n+3) {
                display: none;
            }

            .container {
                margin-top: 30px;
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

    <%

        List<Recipe> favoriteRecipes =
                (List<Recipe>)
                request.getAttribute(
                        "favoriteRecipes"
                );

        if (favoriteRecipes == null ||
                favoriteRecipes.isEmpty()) {

    %>

        <div class="no-favorites">

            <div class="no-favorites-icon">
                ❤️
            </div>

            <h2>
                No Favorite Recipes Yet
            </h2>

            <p>
                Add recipes to your favorites
                and they will appear here.
            </p>

            <a href="ingredients.html"
               class="button">

                🔎 Find More Recipes

            </a>

        </div>

    <%

        } else {

            for (Recipe recipe :
                    favoriteRecipes) {

    %>

        <div class="recipe-card">

            <h2>
                <%= recipe.getRecipeName() %>
            </h2>

            <p>
                <%= recipe.getDescription() %>
            </p>

            <span class="tag">
                ⏱️
                <%= recipe.getCookingTime() %>
                minutes
            </span>

            <span class="tag">
                🍽️
                <%= recipe.getFoodPreference() %>
            </span>

            <div class="instructions">

                <strong>
                    👨‍🍳 Instructions
                </strong>

                <p>
                    <%= recipe.getInstructions() %>
                </p>

            </div>

            <form action="favorite"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="remove">

                <input type="hidden"
                       name="recipeId"
                       value="<%= recipe.getRecipeId() %>">

                <button type="submit"
                        class="remove-btn">

                    💔 Remove from Favorites

                </button>

            </form>

        </div>

    <%

            }

        }

    %>

    <div style="text-align:center;">

        <a href="ingredients.html"
           class="button">

            ← Find More Recipes

        </a>

    </div>

</div>

<footer>

    © 2026
    <span>PocketChef</span>
    | Smart Recipe Recommendation System

</footer>

</body>

</html>