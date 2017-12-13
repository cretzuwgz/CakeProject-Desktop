package ro.cakeproject.utils;

import java.io.IOException;

import javafx.collections.ObservableList;

import javax.swing.SwingWorker;

import ro.cakeproject.MainApp;
import ro.cakeproject.model.RecipeVO;

public class GetRecipesTask extends SwingWorker<String, Void> {
    
    private ConnectionUtil connection = new ConnectionUtil();
    private RecipesUtil util = new RecipesUtil();
    private String response;
    
    private String url;
    private MainApp app;
    
    public GetRecipesTask(String url, MainApp app) {
    
        this.url = url;
        this.app = app;
    }
    
    @Override
    protected String doInBackground() throws Exception {
    
        try {
            response = connection.getResponseFromURL(url);
        } catch (IOException e) {
            System.out.println("A aparut o eroare la preluarea retetelor!");
            e.printStackTrace();
        }
        
        return response;
    }
    
    @Override
    protected void done() {
    
        ObservableList<RecipeVO> recipes = util.getRecipesFrom(response);
        
        for (RecipeVO recipe : recipes) {
            app.addRecipe(recipe);
        }
    }
}
