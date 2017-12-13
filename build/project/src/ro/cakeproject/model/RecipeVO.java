package ro.cakeproject.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecipeVO {
    
    private StringProperty id;
    private StringProperty title;
    private StringProperty date;
    private StringProperty uploader;
    private StringProperty description;
    private StringProperty pLink;
    private StringProperty rating;
    private IntegerProperty difficulty;
    private StringProperty reqTime;
    private ObservableList<IngredientVO> ingredients;
    private ObservableList<StringProperty> tags;
    
    public RecipeVO(String id, String title, String date, String uploader, String description, String pLink, String rating, int difficulty, String reqTime) {
    
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleStringProperty(date);
        this.uploader = new SimpleStringProperty(uploader);
        this.description = new SimpleStringProperty(description);
        this.pLink = new SimpleStringProperty(pLink);
        this.rating = new SimpleStringProperty(rating);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.reqTime = new SimpleStringProperty(reqTime);
        ingredients = FXCollections.observableArrayList();
        tags = FXCollections.observableArrayList();
    }
    
    public String getId() {
    
        return id.get();
    }
    
    public String getTitle() {
    
        return title.get();
    }
    
    public String getDescription() {
    
        return description.get();
    }
    
    public String getPLink() {
    
        return pLink.get();
    }
    
    public String getRating() {
    
        return rating.get();
    }
    
    public void setRating(StringProperty rating) {
    
        this.rating = rating;
    }
    
    public int getDifficulty() {
    
        return difficulty.get();
    }
    
    public String getReqTime() {
    
        return reqTime.get();
    }
    
    public ObservableList<StringProperty> getTags() {
    
        return tags;
    }
    
    public String getDate() {
    
        return date.get();
    }
    
    public String getUploader() {
    
        return uploader.get();
    }
    
    public ObservableList<IngredientVO> getIngredients() {
    
        return ingredients;
    }
    
    public void addTag(String tag) {
    
        tags.add(new SimpleStringProperty(tag));
    }
    
    public void addIngredient(IngredientVO ingredient) {
    
        ingredients.add(ingredient);
    }
    
    public String getTagsAsStringProperty() {
    
        if (tags.isEmpty())
            return "";
        
        StringBuilder tagsString = new StringBuilder();
        
        for (StringProperty tag : tags)
            tagsString.append(tag.get()).append(",");
        
        tagsString.deleteCharAt(tagsString.length() - 1);
        return tagsString.toString();
    }
    
    public String getIngredientsAsString() {
    
        if (ingredients.isEmpty())
            return "";
        
        StringBuilder ingredientsString = new StringBuilder();
        
        for (IngredientVO ingredient : ingredients)
            ingredientsString.append(ingredient.getName()).append(":").append(ingredient.getQuantity()).append(":").append(ingredient.getMeasurement()).append(",");
        
        ingredientsString.deleteCharAt(ingredientsString.length() - 1);
        return ingredientsString.toString();
    }
}
