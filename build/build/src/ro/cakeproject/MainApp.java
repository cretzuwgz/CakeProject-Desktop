package ro.cakeproject;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.cakeproject.model.RecipeVO;
import ro.cakeproject.utils.GetRecipesTask;
import ro.cakeproject.view.RecipeEditController;
import ro.cakeproject.view.RecipeOverviewController;

public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    RecipeOverviewController controller;
    
    private ObservableList<RecipeVO> recipeData = FXCollections.observableArrayList();
    
    @Override
    public void start(Stage primaryStage) {
    
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CakeProject");
        
        initRootLayout();
        
        showRecipeOverview();
    }
    
    public MainApp() {
    
    }
    
    public void addRecipe(RecipeVO recipe) {
    
        recipeData.add(recipe);
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
    
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the recipe overview inside the root layout.
     */
    public void showRecipeOverview() {
    
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RecipeView.fxml"));
            AnchorPane recipeOverview = (AnchorPane) loader.load();
            
            new GetRecipesTask("https://cakeproject.000webhostapp.com/php/all_recipes.php", this).execute();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(recipeOverview);
            
            // Give the controller access to the main app.
            controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param recipe the recipe object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showRecipeEditDialog(RecipeVO recipe) {
    
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditRecipeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the recipe into the controller.
            RecipeEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRecipeVO(recipe);
            controller.setMainApp(this);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<RecipeVO> getRecipeData() {
    
        return recipeData;
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
    
        return primaryStage;
    }
    
    public static void main(String[] args) {
    
        launch(args);
    }
    
    public void updateData() {
    
        recipeData.clear();
        new GetRecipesTask("https://cakeproject.000webhostapp.com/php/all_recipes.php", this).execute();
    }
}