package com.pocketchef.test;

import com.pocketchef.dao.RecipeDAO;
import com.pocketchef.model.Recipe;

import java.util.List;

public class RecipeDAOTest {

    public static void main(String[] args) {

        RecipeDAO recipeDAO = new RecipeDAO();

        System.out.println("===== TEST 1: GET ALL RECIPES =====");

        List<Recipe> recipes = recipeDAO.getAllRecipes();

        if (recipes.isEmpty()) {

            System.out.println("No recipes found.");

        } else {

            System.out.println("Total recipes found: " + recipes.size());

            for (Recipe recipe : recipes) {

                System.out.println(
                        recipe.getRecipeId() + " | " +
                                recipe.getRecipeName() + " | " +
                                recipe.getCookingTime() + " minutes | " +
                                recipe.getFoodPreference()
                );
            }
        }


        System.out.println();
        System.out.println("===== TEST 2: COOKING TIME <= 30 MINUTES =====");

        List<Recipe> quickRecipes =
                recipeDAO.getRecipesByCookingTime(30);

        for (Recipe recipe : quickRecipes) {

            System.out.println(
                    recipe.getRecipeName() +
                            " - " +
                            recipe.getCookingTime() +
                            " minutes"
            );
        }


        System.out.println();
        System.out.println("===== TEST 3: VEGETARIAN RECIPES =====");

        List<Recipe> vegRecipes =
                recipeDAO.getRecipesByPreference("Veg");

        for (Recipe recipe : vegRecipes) {

            System.out.println(
                    recipe.getRecipeName() +
                            " - " +
                            recipe.getFoodPreference()
            );
        }

        System.out.println();
        System.out.println("===== RECIPE DAO TEST COMPLETED =====");
    }
}