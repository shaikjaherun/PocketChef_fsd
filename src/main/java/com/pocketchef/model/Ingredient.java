package com.pocketchef.model;

public class Ingredient {

    private int ingredientId;
    private String ingredientName;

    // Default constructor
    public Ingredient() {
    }

    // Constructor without ID
    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    // Constructor with ID
    public Ingredient(int ingredientId, String ingredientName) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
    }

    // Getter and Setter for ingredientId
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    // Getter and Setter for ingredientName
    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}