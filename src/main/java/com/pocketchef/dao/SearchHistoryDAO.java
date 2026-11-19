package com.pocketchef.dao;

import com.pocketchef.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDAO {

    // ==============================
    // SAVE SEARCH HISTORY
    // ==============================

    public boolean saveSearchHistory(
            int userId,
            String ingredientsText,
            int cookingTime,
            String foodPreference) {

        String sql =
                "INSERT INTO search_history " +
                        "(user_id, ingredients_text, cooking_time, food_preference) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(
                    1,
                    userId
            );

            preparedStatement.setString(
                    2,
                    ingredientsText
            );

            preparedStatement.setInt(
                    3,
                    cookingTime
            );

            preparedStatement.setString(
                    4,
                    foodPreference
            );

            int result =
                    preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==============================
    // GET SEARCH HISTORY
    // ==============================

    public List<String[]> getSearchHistory(
            int userId) {

        List<String[]> history =
                new ArrayList<>();

        String sql =
                "SELECT cooking_time, " +
                        "food_preference, searched_at " +
                        "FROM search_history " +
                        "WHERE user_id = ? " +
                        "ORDER BY searched_at DESC";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                String[] record = {

                        String.valueOf(
                                resultSet.getInt(
                                        "cooking_time"
                                )
                        ),

                        resultSet.getString(
                                "food_preference"
                        ),

                        resultSet.getString(
                                "searched_at"
                        )
                };

                history.add(record);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return history;
    }
}