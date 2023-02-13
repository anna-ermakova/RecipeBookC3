package pro.sky.recipebookc3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.FilesService;
import pro.sky.recipebookc3.services.RecipeService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    final private FilesService filesServiceRec;
    private Map<Integer, Recipe> recipeMap = new HashMap<>();
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
        if (recipeMap.containsKey(idRec)) {
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
        if (recipeMap.containsKey(idRec)) {
            throw new NotFoundException("Рецепт с заданным id не найден.");
        }
        return recipeMap.remove(idRec);
    }

    @Override
    public void saveToFileRec() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesServiceRec.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void readFromFileRec() {
        String json = filesServiceRec.readFromFile();
        try {
            recipeMap = new ObjectMapper().readValue(json,
                    new TypeReference<Map<Integer, Recipe>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        readFromFileRec();
    }

}
