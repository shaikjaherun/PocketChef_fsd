package com.pocketchef.dao;

import com.pocketchef.model.Ingredient;
import com.pocketchef.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    // Get all ingredients
    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredients = new ArrayList<>();

        String sql = "SELECT * FROM ingredients ORDER BY ingredient_name";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Ingredient ingredient = new Ingredient(
                        resultSet.getInt("ingredient_id"),
                        resultSet.getString("ingredient_name")
                );

                ingredients.add(ingredient);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    // Find ingredient by name
    public Ingredient getIngredientByName(String ingredientName) {

        String sql =
                "SELECT * FROM ingredients WHERE ingredient_name = ?";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, ingredientName);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new Ingredient(
                        resultSet.getInt("ingredient_id"),
                        resultSet.getString("ingredient_name")
                );
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}