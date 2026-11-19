package com.pocketchef.dao;

import com.pocketchef.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FavoriteDAO {

    // Add a recipe to favorites
    public boolean addFavorite(int userId, int recipeId) {

        String sql =
                "INSERT INTO favorites (user_id, recipe_id) " +
                        "VALUES (?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);

            int rows =
                    preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Remove a recipe from favorites
    public boolean removeFavorite(int userId, int recipeId) {

        String sql =
                "DELETE FROM favorites " +
                        "WHERE user_id = ? AND recipe_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);

            int rows =
                    preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Check whether a recipe is already favorited
    public boolean isFavorite(int userId, int recipeId) {

        String sql =
                "SELECT favorite_id " +
                        "FROM favorites " +
                        "WHERE user_id = ? AND recipe_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);

            var resultSet =
                    preparedStatement.executeQuery();

            boolean favorite =
                    resultSet.next();

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return favorite;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}