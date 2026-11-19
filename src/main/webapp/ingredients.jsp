<%@ page import="java.util.List" %>
<%@ page import="com.pocketchef.model.Ingredient" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
          <link rel="stylesheet" href="style.css">

    <title>PocketChef - Select Ingredients</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f7f7f7;
            min-height: 100vh;
        }

        .header {
            background: white;
            padding: 20px 40px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            color: #ff7043;
            font-size: 28px;
            font-weight: bold;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .title {
            text-align: center;
            margin-bottom: 10px;
            color: #333;
            font-size: 30px;
        }

        .subtitle {
            text-align: center;
            color: #777;
            margin-bottom: 30px;
        }

        .ingredients-box {
            background: white;
            padding: 30px;
            border-radius: 15px;

            box-shadow:
                    0 5px 20px rgba(0, 0, 0, 0.08);
        }

        .ingredients-grid {
            display: grid;

            grid-template-columns:
                    repeat(auto-fit, minmax(180px, 1fr));

            gap: 15px;
        }

        .ingredient-card {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;

            cursor: pointer;

            transition:
                    0.2s ease;

            display: flex;
            align-items: center;
            gap: 10px;
        }

        .ingredient-card:hover {
            border-color: #ff7043;
            background: #fff7f3;
        }

        .ingredient-card input {
            width: 18px;
            height: 18px;

            accent-color: #ff7043;

            cursor: pointer;
        }

        .ingredient-name {
            font-size: 16px;
            color: #333;
            cursor: pointer;
        }

        .button-container {
            text-align: center;
            margin-top: 30px;
        }

        .continue-btn {
            background: #ff7043;
            color: white;

            border: none;
            border-radius: 8px;

            padding: 14px 35px;

            font-size: 16px;
            font-weight: bold;

            cursor: pointer;
        }

        .continue-btn:hover {
            background: #f4511e;
        }

        .selected-text {
            text-align: center;
            margin-top: 15px;
            color: #777;
        }

        @media (max-width: 600px) {

            .header {
                padding: 15px 20px;
            }

            .logo {
                font-size: 24px;
            }

            .container {
                margin-top: 25px;
            }

            .ingredients-box {
                padding: 20px;
            }

            .ingredients-grid {
                grid-template-columns: 1fr 1fr;
                gap: 10px;
            }

        }

    </style>

</head>

<body>

<!-- HEADER -->

<div class="header">

    <div class="logo">
        PocketChef
    </div>

</div>


<!-- MAIN CONTENT -->

<div class="container">

    <h1 class="title">
        Select Your Ingredients
    </h1>

    <p class="subtitle">
        Choose the ingredients you currently have
    </p>


    <div class="ingredients-box">

        <form action="recommend" method="post">

            <div class="ingredients-grid">

                <%
                    List<Ingredient> ingredients =
                            (List<Ingredient>)
                            request.getAttribute("ingredients");

                    if (ingredients != null) {

                        for (Ingredient ingredient : ingredients) {
                %>

                    <label class="ingredient-card">

                        <input
                                type="checkbox"
                                name="ingredientIds"
                                value="<%= ingredient.getIngredientId() %>"
                        >

                        <span class="ingredient-name">
                            <%= ingredient.getIngredientName() %>
                        </span>

                    </label>

                <%
                        }

                    } else {
                %>

                    <p>
                        No ingredients found.
                    </p>

                <%
                    }
                %>

            </div>


            <div class="button-container">

                <button
                        type="submit"
                        class="continue-btn">

                    Find Recipes

                </button>

            </div>

        </form>

    </div>

</div>

</body>

</html>