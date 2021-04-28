/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Models;

import csc545groupproject.Controllers.FridgeManager;
import java.util.HashMap;

/**
 *
 * @author Jadon
 */
public class Fridge {
    public HashMap contents = new HashMap();
    
    public boolean add(Food food, int quantity) {
        if (this.get(food.getName()) == null) {
            contents.put(food, quantity);
            return true;
        }
        return false;
    }
    
    public void update(Food food, int quantity) {
        contents.put(food, quantity);
        FridgeManager.updateFridgeQuantity(food, quantity);
    }
    
    public void remove(Food food) {
        contents.remove(food);
        FridgeManager.deleteFoodFromDb(food);
    }
    
    public int getQuantity(Food food) {
        return (int) contents.get(food);
    }
    
    public Food get(String foodName) {
        Food[] foods = (Food[]) contents.keySet().toArray(new Food[0]);
        for(Food food: foods) {
            if (food.getName().equals(foodName)) {
                return food;
            }
        }
        
        return null;
    }
    
    public Food[] getFoods() {
        return (Food[]) contents.keySet().toArray(new Food[0]);
    }
    
    public void printContents() {
        contents.forEach((_food, quantity) -> {
            Food food = (Food) _food;
            System.out.printf("%s %s %n", food.getName(), quantity);
        });
    }
}
