package pro.sky.recipebookc3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.exception.ExistsException;
import pro.sky.recipebookc3.exception.FileProcessingException;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.FilesService;
import pro.sky.recipebookc3.services.RecipeService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service

public class RecipeServiceImpl implements RecipeService {
    private final FilesService filesService;

    public RecipeServiceImpl(@Qualifier("recipeFileService") FilesService filesService) {
        this.filesService = filesService;
    }

    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer idRec = 0;

    public Map<Integer, Recipe> getRecipeMap() {
        return recipeMap;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipeMap.containsValue(recipe)) {
            throw new ExistsException("такой рецепт уже есть");
        }
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
    public void saveToFileRecipe() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не сохранен");
        }

    }

    @Override
    public void readFromFileRecipe() throws FileProcessingException {
        String json = filesService.readFromFile();
        try {
            recipeMap = new ObjectMapper().readValue(json,
                    new TypeReference<Map<Integer, Recipe>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    @PostConstruct
    private void initRecipe() throws FileProcessingException {
        readFromFileRecipe();
    }
}
