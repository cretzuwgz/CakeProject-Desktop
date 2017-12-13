package ro.cakeproject.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IngredientVO {
    
    private StringProperty name;
    private StringProperty quantity;
    private StringProperty measurement;
    
    public IngredientVO(String name, String quantity, String measurement) {
    
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleStringProperty(quantity);
        this.measurement = new SimpleStringProperty(measurement);
    }
    
    public String getName() {
    
        return name.get();
    }
    
    public String getQuantity() {
    
        return quantity.get();
    }
    
    public String getMeasurement() {
    
        return measurement.get();
    }
    
}
