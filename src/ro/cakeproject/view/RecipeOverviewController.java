package ro.cakeproject.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ro.cakeproject.MainApp;
import ro.cakeproject.model.RecipeVO;
import ro.cakeproject.utils.ManageRecipeTask;

public class RecipeOverviewController {
    
    @FXML
    private TableView<RecipeVO> recipeTable;
    @FXML
    private TableColumn<RecipeVO, String> recipeTitleColumn;
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriereLabel;
    @FXML
    private Label linkPozaLabel;
    @FXML
    private Label dificultateLabel;
    @FXML
    private Label timpLabel;
    @FXML
    private Label tagsLabel;
    @FXML
    private Label ingredienteLabel;
    
    // Reference to the main application.
    private MainApp mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RecipeOverviewController() {
    
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    
        // Initialize the person table with the two columns.
        recipeTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        
        // Clear person details.
        showRecipeDetails(null);
        
        // Listen for selection changes and show the person details when changed.
        recipeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRecipeDetails(newValue));
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    
        this.mainApp = mainApp;
        
        // Add observable list data to the table
        recipeTable.setItems(mainApp.getRecipeData());
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param recipe the person or null
     */
    private void showRecipeDetails(RecipeVO recipe) {
    
        if (recipe != null) {
            // Fill the labels with info from the recipe object.
            titleLabel.setText(recipe.getTitle());
            descriereLabel.setText(recipe.getDescription());
            linkPozaLabel.setText(recipe.getPLink());
            dificultateLabel.setText(String.valueOf(recipe.getDifficulty()));
            timpLabel.setText(recipe.getReqTime());
            tagsLabel.setText(recipe.getTagsAsStringProperty());
            ingredienteLabel.setText(recipe.getIngredientsAsString());
            
        } else {
            // Recipe is null, remove all the text.
            titleLabel.setText("");
            descriereLabel.setText("");
            linkPozaLabel.setText("");
            dificultateLabel.setText("");
            timpLabel.setText("");
            tagsLabel.setText("");
            ingredienteLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteRecipe() {
    
        int selectedIndex = recipeTable.getSelectionModel().getSelectedIndex();
        
        if (selectedIndex >= 0) {
            RecipeVO selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();
            
            new ManageRecipeTask("https://cakeproject.000webhostapp.com/php/delete_recipe.php", "recipe_id=" + selectedRecipe.getId(), mainApp).execute();
            
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Recipe Selected");
            alert.setContentText("Please select a recipe in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewRecipe() {
    
        mainApp.showRecipeEditDialog(null);
    }
    
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditRecipe() {
    
        RecipeVO selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();
        if (selectedRecipe != null) {
            mainApp.showRecipeEditDialog(selectedRecipe);
            
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Recipe Selected");
            alert.setContentText("Please select a recipe in the table.");
            
            alert.showAndWait();
        }
    }
}
