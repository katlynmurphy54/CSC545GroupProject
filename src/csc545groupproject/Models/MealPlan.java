/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Models;

import java.time.LocalDate;


/**
 *
 * @author Jadon
 */
public class MealPlan {
    private int id;
    private String name;
    private LocalDate day;
    
    public MealPlan(int id, String name, LocalDate day) {
        this.id = id;
        this.name = name;
        this.day = day;
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
     * @return the day
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(LocalDate day) {
        this.day = day;
    }
    
}