package com.pocketchef.dao;

import com.pocketchef.model.Recipe;
import com.pocketchef.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {

    // Add a new recipe
    public boolean addRecipe(Recipe recipe) {

        String sql = "INSERT INTO recipes " +
                "(recipe_name, description, cooking_time, food_preference, instructions) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, recipe.getRecipeName());
            preparedStatement.setString(2, recipe.getDescription());
            preparedStatement.setInt(3, recipe.getCookingTime());
            preparedStatement.setString(4, recipe.getFoodPreference());
            preparedStatement.setString(5, recipe.getInstructions());

            int rows = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Get all recipes
    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        String sql = "SELECT * FROM recipes";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Recipe recipe = new Recipe(
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
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }


    // Find recipes by cooking time
    public List<Recipe> getRecipesByCookingTime(int maxTime) {

        List<Recipe> recipes = new ArrayList<>();

        String sql = "SELECT * FROM recipes WHERE cooking_time <= ?";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, maxTime);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Recipe recipe = new Recipe(
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
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }


    // Find recipes by food preference
    public List<Recipe> getRecipesByPreference(String preference) {

        List<Recipe> recipes = new ArrayList<>();

        String sql = "SELECT * FROM recipes WHERE food_preference = ?";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, preference);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Recipe recipe = new Recipe(
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
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }
}