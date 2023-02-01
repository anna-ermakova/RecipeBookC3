package pro.sky.recipebookc3.services;

import pro.sky.recipebookc3.model.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Integer idIngr);

    Collection<Ingredient> getAllIngredient();

    Ingredient removeIngredient(Integer idIngr);

    Ingredient updateIngredient(Integer idIngr, Ingredient ingredient);

    void saveToFileIngr();

    void readFromFileIngr();
}
