package pro.sky.recipebookc3.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.RecipeService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
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
        if (!recipeMap.containsKey(idRec)) {
            throw new NotFoundException("Рецепт с заданным id не найден.");
        }
        return recipeMap.get(idRec);
    }

    @Override
    public Recipe updateRecipe(Integer idRec, Recipe recipe) {
        if (!recipeMap.containsKey(idRec)) {
            throw new NotFoundException("Рецепт с заданным id не найден.");
        }
        return recipeMap.put(idRec, recipe);
    }

    @Override
    public Collection<Recipe> getAllRecipes() {
        return recipeMap.values();
    }

    @Override
    public Recipe removeRecipe(Integer idRec) {
        if (!recipeMap.containsKey(idRec)) {
            throw new NotFoundException("Рецепт с заданным id не найден.");
        }
        return recipeMap.remove(idRec);
    }
}
