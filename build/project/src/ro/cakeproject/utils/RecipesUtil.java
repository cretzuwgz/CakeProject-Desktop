package ro.cakeproject.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.cakeproject.model.IngredientVO;
import ro.cakeproject.model.RecipeVO;

public class RecipesUtil implements RecipesUtilInterface<RecipeVO> {
    
    @Override
    public ObservableList<RecipeVO> getRecipesFrom(String response) {
    
        JSONArray recipesJson;
        ObservableList<RecipeVO> recipeList = FXCollections.observableArrayList();
        
        try {
            recipesJson = new JSONObject(response).getJSONArray("recipes");
        } catch (JSONException e) {
            System.out.println("Eroare la parsare 1");
            return null;
        }
        try {
            for (int i = 0; i < recipesJson.length(); i++) {
                JSONObject recipe = recipesJson.getJSONObject(i);
                int difficulty = recipe.isNull("difficulty") ? 0 : Integer.valueOf(recipe.getString("difficulty"));
                String pLink = recipe.isNull("p_link") ? "" : recipe.getString("p_link");
                String reqTime = recipe.isNull("req_time") ? "" : recipe.getString("req_time");
                RecipeVO recipeVO = new RecipeVO(recipe.getString("id"), recipe.getString("title"), recipe.getString("date"), recipe.getString("uploader"), recipe.getString("description"), pLink, String.valueOf(recipe.getInt("rating")), difficulty, reqTime);
                
                JSONArray array = recipe.getJSONArray("tags");
                for (int j = 0; j < array.length(); j++)
                    recipeVO.addTag(array.getString(j));
                
                array = recipe.getJSONArray("ingredients");
                for (int j = 0; j < array.length(); j++) {
                    IngredientVO currentIngredient;
                    JSONObject jsonIngredient = array.getJSONObject(j);
                    currentIngredient = new IngredientVO(jsonIngredient.getString("name"), jsonIngredient.getString("quantity"), jsonIngredient.getString("unit"));
                    recipeVO.addIngredient(currentIngredient);
                }
                recipeList.add(recipeVO);
                
            }
            
        } catch (JSONException e) {
            System.out.println("Eroare la parsare 2");
            return null;
        }
        
        if (recipeList.size() == 0) {
            System.out.println("Lista goala de retete.");
            return null;
        }
        return recipeList;
    }
    
}
