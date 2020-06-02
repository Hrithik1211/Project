package com.social.projectapp;

public class recipe {

    private String id;
    private String ingredients;




    public recipe(String id, String ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
