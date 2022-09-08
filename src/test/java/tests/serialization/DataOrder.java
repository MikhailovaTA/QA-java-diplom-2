package tests.serialization;

import tests.deserialization.Ingredient;

import java.util.List;

public class DataOrder {

    private List<String> ingredients;

    public DataOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public DataOrder(){}

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
