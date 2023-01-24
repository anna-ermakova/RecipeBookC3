package pro.sky.recipebookc3.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.RecipeService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer idRec = 0;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(idRec++, recipe);
        return recipe;
    }

    @Override
    public Recipe getRecipe(Integer idRec) {
        return recipeMap.get(idRec);
    }
}
