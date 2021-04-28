/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Models;

/**
 *
 * @author Jadon
 */
public class Food {
    private String name;
    private int calories;
    private int protein;
    private int sugar;
    private int sodium;
    private int fat;
    
    public Food(
            String name,
            int calories,
            int protein,
            int sugar,
            int sodium,
            int fat
    ) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.sugar = sugar;
        this.sodium = sodium;
        this.fat = fat;
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
     * @return the calories
     */
    public int getCalories() {
        return calories;
    }

    /**
     * @param calories the calories to set
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * @return the protein
     */
    public int getProtein() {
        return protein;
    }

    /**
     * @param protein the protein to set
     */
    public void setProtein(int protein) {
        this.protein = protein;
    }

    /**
     * @return the sugar
     */
    public int getSugar() {
        return sugar;
    }

    /**
     * @param sugar the sugar to set
     */
    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    /**
     * @return the sodium
     */
    public int getSodium() {
        return sodium;
    }

    /**
     * @param sodium the sodium to set
     */
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    /**
     * @return the fat
     */
    public int getFat() {
        return fat;
    }

    /**
     * @param fat the fat to set
     */
    public void setFat(int fat) {
        this.fat = fat;
    }
}
