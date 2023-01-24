package pro.sky.recipebookc3.services;

import pro.sky.recipebookc3.model.Recipe;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Integer idRec);
}
