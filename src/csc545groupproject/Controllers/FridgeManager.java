/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Controllers;

import csc545groupproject.Models.Food;
import csc545groupproject.Models.Fridge;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Jadon
 */

//MARK - 
public class FridgeManager {
    
    //================================================================================
    // Food
    //================================================================================
    
    public static ArrayList<Food> getFoodsFromDb() {
        ArrayList<Food> foods = new ArrayList<>();
        
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "SELECT * FROM Food WHERE rownum <= 100";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("name");
                int calories = rs.getInt("calories");
                int sugar = rs.getInt("sugar");
                int protein = rs.getInt("protein");
                int sodium = rs.getInt("sodium");
                int fat = rs.getInt("fat");
                
                Food food = new Food(name, calories, protein, sugar, sodium, fat);
                foods.add(food);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return foods;
    }
    
    public static boolean addFoodToDb(Food food, int quantity) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "INSERT INTO Food (name, calories, protein, sugar, sodium, fat) VALUES (?, ?, ?, ?, ?, ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, food.getName());
            pst.setInt(2, food.getCalories());
            pst.setInt(3, food.getProtein());
            pst.setInt(4, food.getSugar());
            pst.setInt(5, food.getSodium());
            pst.setInt(6, food.getFat());

            pst.executeUpdate();
            
            updateFridgeQuantity(food, quantity);
            return true;
            
            
        } catch (SQLIntegrityConstraintViolationException e) {
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        return false;
    }
    
    public static void deleteFoodFromDb(Food food) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "DELETE FROM Food WHERE name=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, food.getName());

            pst.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
    }
    
    public static void updateFoodInDb(Food food) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "UPDATE Food SET calories=?, protein=?, sugar=?, sodium=?, fat=? WHERE name=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(6, food.getName());
            pst.setInt(1, food.getCalories());
            pst.setInt(2, food.getProtein());
            pst.setInt(3, food.getSugar());
            pst.setInt(4, food.getSodium());
            pst.setInt(5, food.getFat());

            pst.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
    }
    
    //================================================================================
    // Fridge
    //================================================================================
    
    public static Fridge getFridgeQuantitiesFromDb() {
        return getFridgeQuantitiesFromDb(getFoodsFromDb());
    }
    
    public static Fridge getFridgeQuantitiesFromDb(ArrayList<Food> foods) {
        Fridge fridge = new Fridge();
        
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "SELECT * FROM Fridge WHERE rownum <= 100";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                String foodName = rs.getString("foodName");
                Optional<Food> _food = foods.stream().filter(food -> food.getName().equals(foodName)).findFirst();
                
                if (_food.isPresent()) {
                    Food safeFood = _food.get();
                    int quantity = rs.getInt("quantity");
                    fridge.add(safeFood, quantity);
                } else {
                    throw new Exception("Tried to add food '" + foodName + "' to the fridge, but it does not exist.");
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return fridge;
    }
    
    public static void updateFridgeQuantity(Food food, int quantity) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "UPDATE Fridge SET quantity=? WHERE foodName=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(2, food.getName());
            pst.setInt(1, quantity);
            
            pst.executeUpdate();
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
    }
}
