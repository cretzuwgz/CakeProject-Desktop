package ro.cakeproject.utils;

import javafx.collections.ObservableList;

public interface RecipesUtilInterface<RecipeVO> {
    
    ObservableList<RecipeVO> getRecipesFrom(String response);
    
}
