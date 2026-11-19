package com.pocketchef.test;

import com.pocketchef.dao.IngredientDAO;
import com.pocketchef.model.Ingredient;

import java.util.List;

public class IngredientDAOTest {

    public static void main(String[] args) {

        IngredientDAO ingredientDAO = new IngredientDAO();

        System.out.println("===== TEST 1: GET ALL INGREDIENTS =====");

        List<Ingredient> ingredients =
                ingredientDAO.getAllIngredients();

        if (ingredients.isEmpty()) {

            System.out.println("No ingredients found.");

        } else {

            System.out.println(
                    "Total ingredients found: " +
                            ingredients.size()
            );

            for (Ingredient ingredient : ingredients) {

                System.out.println(
                        ingredient.getIngredientId() +
                                " | " +
                                ingredient.getIngredientName()
                );
            }
        }

        System.out.println();
        System.out.println(
                "===== INGREDIENT DAO TEST COMPLETED ====="
        );
    }
}