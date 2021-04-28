package csc545groupproject.Controllers;

import csc545groupproject.Models.Food;
import csc545groupproject.Models.Recipe;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RecipeManager {
    
    //================================================================================
    // Recipe
    //================================================================================
    
    public static ArrayList<Recipe> getRecipesFromDb() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "SELECT * FROM Recipe WHERE rownum<=100";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String instructions = rs.getString("instructions");
                
                HashMap ingredients = getRecipeIngredientsFromDb(id);
                
                Recipe recipe = new Recipe(id, name, category, instructions, ingredients);
                recipes.add(recipe);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return recipes;
    }
    
    public static boolean updateRecipeInDb(Recipe recipe) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        System.out.println(recipe.ingredients.toString());
        
        try {
            // ADD RECIPE TO DB
            String sqlStatement = "UPDATE Recipe SET name=?, category=?, instructions=? WHERE id=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            
            pst.setString(1, recipe.getName());
            pst.setString(2, recipe.getCategory());
            pst.setString(3, recipe.getInstructions());
            pst.setInt(4, recipe.getId());

            pst.executeUpdate();
            
            // REMOVE OLD INGREDIENTS FROM DB
            sqlStatement = "DELETE FROM RecipeFood WHERE recipeid=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, recipe.getId());
            
            pst.executeUpdate();
            
            // ADD INGREDIENTS TO DB
            String[] foodNames = (String[]) recipe.ingredients.keySet().toArray(new String[0]);
            Integer[] quantities = (Integer[]) recipe.ingredients.values().toArray(new Integer[0]);
            
            for (int i = 0; i < recipe.ingredients.size(); i++) {
                // recipeid, foodname, quantity
                sqlStatement = "INSERT INTO RecipeFood VALUES (?, ?, ?)";
                
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                pst.setInt(1, recipe.getId());
                pst.setString(2, foodNames[i]);
                pst.setInt(3, quantities[i]);;
                
                pst.executeUpdate();
            }
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return false;
    }
    
    public static ArrayList<Recipe> searchRecipesByNameAndCategory(String fuzzyRecipeNameOrCategory) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "select * from recipe where upper(name) like ? or upper(category) like ?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            
            String queryString = "%" + fuzzyRecipeNameOrCategory.toUpperCase() + "%";
            
            System.out.println(queryString);
            
            pst.setString(1, queryString);
            pst.setString(2, queryString);
            
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String instructions = rs.getString("instructions");
                
                HashMap ingredients = getRecipeIngredientsFromDb(id);
                
                Recipe recipe = new Recipe(id, name, category, instructions, ingredients);
                recipes.add(recipe);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return recipes;
    }
    
    public static Recipe addRecipeToDb(String name, String category, String instructions, HashMap ingredients) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            // GET ID FOR NEW RECIPE
            String sqlStatement = "select nvl(max(id), 0)+1 as id from recipe";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            
            rs = (OracleResultSet) pst.executeQuery();
            
            int id;
            if (rs.next()) {
                id = rs.getInt("id");
            } else {
                return null;
            }
            
            // ADD RECIPE TO DB
            sqlStatement = "INSERT INTO Recipe (id, name, instructions, category) VALUES (?, ?, ?, ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, instructions);
            pst.setString(4, category);

            pst.executeUpdate();
            
            // ADD INGREDIENTS TO DB
            String[] foodNames = (String[]) ingredients.keySet().toArray(new String[0]);
            Integer[] quantities = (Integer[]) ingredients.values().toArray(new Integer[0]);
            
            for (int i = 0; i < ingredients.size(); i++) {
                // recipeid, foodname, quantity
                sqlStatement = "INSERT INTO RecipeFood VALUES (?, ?, ?)";
                
                pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
                pst.setInt(1, id);
                pst.setString(2, foodNames[i]);
                pst.setInt(3, quantities[i]);;
                
                pst.executeUpdate();
            }
            
            return new Recipe(id, name, instructions, category, ingredients);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        return null;
    }
    
     public static void deleteRecipeFromDb(Recipe recipe) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "DELETE FROM Recipe WHERE id=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, recipe.getId());

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
    // RecipeFood
    //================================================================================
    
   
    
    public static HashMap getRecipeIngredientsFromDb(int recipeId) { //get this for specific recipe id to create a recipeFood for that         
        HashMap recipeIngredients = new HashMap();
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "select rf.foodname as \"foodName\", rf.quantity as \"quantity\" from recipefood rf join recipe r on r.id=rf.recipeid where r.id=?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, recipeId);
            
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                String foodname = rs.getString("foodname");
                int quantity = rs.getInt("quantity");
                
                recipeIngredients.put(foodname, quantity);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        
        return recipeIngredients;
    }

    public static void addFoodToRecipe(Recipe recipe, ArrayList<Food> foods) throws Exception { //only add food
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        
        try {
            String sqlStatement = "//select rf.foodname as \"foodName\", rf.quantity as \"quantity\" from recipefood rf join recipe r on r.id=rf.recipeid where r.id=1;";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();
           
            
            while (rs.next()) { //this is getting the ingredients from recipeFood
                String foodName = rs.getString("foodName");
                Optional<Food> _food = foods.stream().filter(food -> food.getName().equals(foodName)).findFirst();
                
                if (_food.isPresent()) {
                    Food safeFood = _food.get();
                    int quantity = rs.getInt("quantity");
                    recipe.add(safeFood, quantity);
                    
                    pst.executeUpdate();
                   
                } else {
                    throw new Exception("Tried to add food '" + foodName + "' to the recipe, but it does not exist.");
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectDb.close(conn);
            ConnectDb.close(rs);
            ConnectDb.close(pst);
        }
        //return recipe;
    }
    
    public static void addRecipeFoodToDb(Recipe recipe) {
        Connection conn = new ConnectDb().setupConnection();
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        Object ofoodname = recipe.ingredients.values().toArray()[0];
        Object oquantity = recipe.ingredients.get(recipe.ingredients.keySet().toArray()[0]);
        
        String foodname = ofoodname.toString();
        int quantity = (int) oquantity;
        
        try {
            String sqlStatement = "INSERT INTO RecipeFood (recipeID, foodname, quantity) VALUES (?, ?, ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, recipe.getId());
            pst.setString(2, foodname);
            pst.setInt(3, quantity);

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