/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Models;

import java.util.HashMap;

/**
 *
 * @author Jadon
 */
public class Recipe {
    
    private int id;
    private String name;
    private String category;
    private String instructions;
    public HashMap ingredients;
    
    public Recipe(int id, String name, String category, String instructions, HashMap ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions the instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setIngredients(HashMap recipeIngredients) {
        this.ingredients = recipeIngredients;
    }

    public void add(Food food, int quantity) {
        this.ingredients.put(food, quantity);
    }

    public HashMap getIngredients() {
        return this.ingredients;
    }
}