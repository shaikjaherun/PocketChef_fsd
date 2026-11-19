package com.pocketchef.model;

public class Recipe {

    private int recipeId;
    private String recipeName;
    private String description;
    private int cookingTime;
    private String foodPreference;
    private String instructions;

    public Recipe() {
    }

    public Recipe(int recipeId, String recipeName, String description,
                  int cookingTime, String foodPreference, String instructions) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.cookingTime = cookingTime;
        this.foodPreference = foodPreference;
        this.instructions = instructions;
    }

    public Recipe(String recipeName, String description,
                  int cookingTime, String foodPreference, String instructions) {
        this.recipeName = recipeName;
        this.description = description;
        this.cookingTime = cookingTime;
        this.foodPreference = foodPreference;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", description='" + description + '\'' +
                ", cookingTime=" + cookingTime +
                ", foodPreference='" + foodPreference + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}