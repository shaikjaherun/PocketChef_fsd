package com.pocketchef.test;

import com.pocketchef.dao.RecommendationDAO;
import com.pocketchef.model.Recipe;

import java.util.Arrays;
import java.util.List;

public class RecommendationDAOTest {
    public static void main(String[] args) {

        RecommendationDAO recommendationDAO =
                new RecommendationDAO();

        /*
         * Test ingredients:
         *
         * 1  = Rice
         * 2  = Chicken
         * 4  = Onion
         *
         * These IDs are from your ingredients table.
         */

        List<Integer> selectedIngredientIds =
                Arrays.asList(1, 2, 4);

        System.out.println(
                "===== POCKETCHEF RECOMMENDATION TEST ====="
        );

        System.out.println(
                "Selected Ingredient IDs: " +
                        selectedIngredientIds
        );

        List<Recipe> recommendations =
                recommendationDAO.getRecommendedRecipes(
                        selectedIngredientIds
                );

        System.out.println();

        if (recommendations.isEmpty()) {

            System.out.println(
                    "No matching recipes found."
            );

        } else {

            System.out.println(
                    "Recommended Recipes:"
            );

            System.out.println();

            for (Recipe recipe : recommendations) {

                System.out.println(
                        recipe.getRecipeId() +
                                " | " +
                                recipe.getRecipeName() +
                                " | " +
                                recipe.getCookingTime() +
                                " minutes | " +
                                recipe.getFoodPreference()
                );
            }
        }

        System.out.println();
        System.out.println(
                "===== RECOMMENDATION TEST COMPLETED ====="
        );
    }
}