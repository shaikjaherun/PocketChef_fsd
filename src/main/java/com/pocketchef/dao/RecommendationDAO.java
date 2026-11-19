package com.pocketchef.dao;

import com.pocketchef.model.Recipe;
import com.pocketchef.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {

    // Original method
    // Keeps existing tests working
    public List<Recipe> getRecommendedRecipes(
            List<Integer> ingredientIds) {

        return getRecommendedRecipes(
                ingredientIds,
                60,
                "Any"
        );
    }

    // Method with cooking time
    // Keeps the current recommendation flow working
    public List<Recipe> getRecommendedRecipes(
            List<Integer> ingredientIds,
            int maxCookingTime) {

        return getRecommendedRecipes(
                ingredientIds,
                maxCookingTime,
                "Any"
        );
    }

    // Main recommendation method
    // Filters by ingredients, cooking time and food preference
    public List<Recipe> getRecommendedRecipes(
            List<Integer> ingredientIds,
            int maxCookingTime,
            String foodPreference) {

        List<Recipe> recipes =
                new ArrayList<>();

        if (ingredientIds == null ||
                ingredientIds.isEmpty()) {

            return recipes;
        }

        StringBuilder placeholders =
                new StringBuilder();

        for (int i = 0;
             i < ingredientIds.size();
             i++) {

            if (i > 0) {
                placeholders.append(", ");
            }

            placeholders.append("?");
        }

        StringBuilder sql =
                new StringBuilder();

        sql.append(
                "SELECT r.recipe_id, r.recipe_name, " +
                        "r.description, r.cooking_time, " +
                        "r.food_preference, r.instructions, " +
                        "COUNT(DISTINCT ri.ingredient_id) " +
                        "AS matched_ingredients "
        );

        sql.append(
                "FROM recipes r "
        );

        sql.append(
                "JOIN recipe_ingredients ri " +
                        "ON r.recipe_id = ri.recipe_id "
        );

        sql.append(
                "WHERE ri.ingredient_id IN (" +
                        placeholders +
                        ") "
        );

        sql.append(
                "AND r.cooking_time <= ? "
        );

        if (foodPreference != null &&
                !foodPreference.equals("Any")) {

            sql.append(
                    "AND r.food_preference = ? "
            );
        }

        sql.append(
                "GROUP BY r.recipe_id, " +
                        "r.recipe_name, r.description, " +
                        "r.cooking_time, r.food_preference, " +
                        "r.instructions "
        );

        sql.append(
                "ORDER BY matched_ingredients DESC, " +
                        "r.recipe_name"
        );

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            sql.toString()
                    );

            int parameterIndex = 1;

            // Ingredient IDs
            for (Integer ingredientId :
                    ingredientIds) {

                preparedStatement.setInt(
                        parameterIndex,
                        ingredientId
                );

                parameterIndex++;
            }

            // Cooking time
            preparedStatement.setInt(
                    parameterIndex,
                    maxCookingTime
            );

            parameterIndex++;

            // Food preference
            if (foodPreference != null &&
                    !foodPreference.equals("Any")) {

                preparedStatement.setString(
                        parameterIndex,
                        foodPreference
                );
            }

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Recipe recipe =
                        new Recipe(
                                resultSet.getInt(
                                        "recipe_id"
                                ),

                                resultSet.getString(
                                        "recipe_name"
                                ),

                                resultSet.getString(
                                        "description"
                                ),

                                resultSet.getInt(
                                        "cooking_time"
                                ),

                                resultSet.getString(
                                        "food_preference"
                                ),

                                resultSet.getString(
                                        "instructions"
                                )
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