<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
       <link rel="stylesheet" href="style.css">

    <title>PocketChef | Search History</title>

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

        .main-container {
            width: 92%;
            max-width: 900px;

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

        .history-box {
            background: white;

            padding: 24px;

            border-radius: 22px;

            box-shadow:
                0 12px 35px
                rgba(38,50,56,0.08);
        }

        .history-item {
            border: 1px solid #f0ddd6;

            border-radius: 15px;

            padding: 20px;

            margin-bottom: 15px;

            background: #fffdfc;

            transition: 0.2s;
        }

        .history-item:hover {
            border-color: #ffb39a;
            transform: translateY(-2px);
        }

        .history-item:last-child {
            margin-bottom: 0;
        }

        .search-number {
            color: #ff7043;

            font-size: 18px;
            font-weight: 800;

            margin-bottom: 12px;
        }

        .tags {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;

            margin-bottom: 12px;
        }

        .tag {
            padding: 8px 13px;

            background: #fff0eb;
            color: #ff7043;

            border-radius: 20px;

            font-size: 13px;
            font-weight: 700;
        }

        .searched-date {
            color: #777;
            font-size: 14px;
        }

        .empty {
            text-align: center;
            padding: 45px 20px;
            color: #777;
        }

        .empty-icon {
            font-size: 50px;
            margin-bottom: 12px;
        }

        .empty h3 {
            color: #ff7043;
            margin-bottom: 10px;
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

            .main-container {
                margin-top: 30px;
            }

            .history-box {
                padding: 15px;
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

        <a href="favorites">
            ❤️ Favorites
        </a>

        <a href="profile">
            👤 Profile
        </a>

        <a href="logout">
            🚪 Logout
        </a>

    </div>

</header>

<div class="main-container">

    <div class="heading">

        <div class="heading-icon">
            📋
        </div>

        <h1>
            Search History
        </h1>

        <p>
            View your previous recipe searches
        </p>

    </div>

    <div class="history-box">

        <%
            List<String[]> history =
                    (List<String[]>)
                    request.getAttribute("history");

            if (history == null || history.isEmpty()) {
        %>

            <div class="empty">

                <div class="empty-icon">
                    📋
                </div>

                <h3>
                    No Search History
                </h3>

                <p>
                    Your previous recipe searches
                    will appear here.
                </p>

            </div>

        <%
            } else {

                int count = 1;

                for (String[] item : history) {

                    String cookingTime = item[0];
                    String foodPreference = item[1];
                    String searchedAt = item[2];
        %>

            <div class="history-item">

                <div class="search-number">
                    Search <%= count %>
                </div>

                <div class="tags">

                    <span class="tag">
                        ⏱️ Up to
                        <%= cookingTime %>
                        minutes
                    </span>

                    <span class="tag">
                        🍽️
                        <%= foodPreference %>
                    </span>

                </div>

                <div class="searched-date">
                    🕒 Searched on:
                    <%= searchedAt %>
                </div>

            </div>

        <%
                    count++;
                }
            }
        %>

    </div>

</div>

<footer>
    © 2026
    <span>PocketChef</span>
    | Smart Recipe Recommendation System
</footer>

</body>

</html>