package ro.cakeproject.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.cakeproject.MainApp;
import ro.cakeproject.model.RecipeVO;
import ro.cakeproject.utils.ManageRecipeTask;

public class RecipeEditController {
    
    @FXML
    private TextField titleLabel;
    @FXML
    private TextArea descriereLabel;
    @FXML
    private TextField linkPozaLabel;
    @FXML
    private TextField dificultateLabel;
    @FXML
    private TextField timpLabel;
    @FXML
    private TextArea tagsLabel;
    @FXML
    private TextArea ingredienteLabel;
    
    private Stage dialogStage;
    private RecipeVO recipe;
    private MainApp mainApp;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    
        this.mainApp = mainApp;
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
    
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the recipe to be edited in the dialog.
     * 
     * @param recipe
     */
    public void setRecipeVO(RecipeVO recipe) {
    
        if (recipe != null) {
            this.recipe = recipe;
            titleLabel.setText(recipe.getTitle());
            descriereLabel.setText(recipe.getDescription());
            linkPozaLabel.setText(recipe.getPLink());
            dificultateLabel.setText(String.valueOf(recipe.getDifficulty()));
            timpLabel.setText(recipe.getReqTime());
            tagsLabel.setText(recipe.getTagsAsStringProperty());
            ingredienteLabel.setText(recipe.getIngredientsAsString());
            ingredienteLabel.setDisable(true);
            
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
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
    
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    
        if (isInputValid()) {
            String params = "title=" + titleLabel.getText() + "&uploader=1" + "&req_time=" + timpLabel.getText() + "&difficulty=" + dificultateLabel.getText() + "&description=" + descriereLabel.getText() + "&tags=" + tagsLabel.getText();
            params.replaceAll(" ", "%20");
            if (recipe != null) {
                
                params += "&recipe_id=" + recipe.getId();
                new ManageRecipeTask("https://cakeproject.000webhostapp.com/php/update_recipe.php", params, mainApp).execute();
                okClicked = true;
                dialogStage.close();
            } else {
                params += "&p_link=" + linkPozaLabel.getText();
                params += "&ingredients=" + ingredienteLabel.getText();
                new ManageRecipeTask("https://cakeproject.000webhostapp.com/php/add_recipe.php", params, mainApp).execute();
                okClicked = true;
                dialogStage.close();
            }
            
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
    
        String errorMessage = "";
        
        if (linkPozaLabel.getText() == null || linkPozaLabel.getText().length() == 0) {
            linkPozaLabel.setText("NULL");
        }
        
        if (titleLabel.getText() == null || titleLabel.getText().length() == 0) {
            errorMessage += "Titlu invalid!\n";
        }
        
        if (timpLabel.getText() == null || timpLabel.getText().length() == 0) {
            errorMessage += "Timp necesar invalid!\n";
        }

        if(!timpLabel.getText().matches("\\d+")){
            errorMessage += "Timp necesar invalid!\n";
        }

        if (recipe == null)
            if (ingredienteLabel.getText() == null || ingredienteLabel.getText().length() == 0) {
                errorMessage += "Ingrediente invalide!\n";
            }
        
        if (descriereLabel.getText() == null || descriereLabel.getText().length() == 0) {
            errorMessage += "Descriere invalida!\n";
        }
        
        if (tagsLabel.getText() == null || tagsLabel.getText().length() == 0) {
            errorMessage += "Tag-uri invalide!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Date invalide");
            alert.setHeaderText("Va rugam corectati datele invalide");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}