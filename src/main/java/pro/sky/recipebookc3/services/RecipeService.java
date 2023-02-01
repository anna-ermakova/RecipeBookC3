package pro.sky.recipebookc3.services;

import pro.sky.recipebookc3.model.Recipe;

import java.util.Collection;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Integer idRec);

    Recipe updateRecipe(Integer idRec, Recipe recipe);

    Collection<Recipe> getAllRecipes();

    Recipe removeRecipe(Integer idRec);

    void saveToFileRec();

    void readFromFileRec();
}
