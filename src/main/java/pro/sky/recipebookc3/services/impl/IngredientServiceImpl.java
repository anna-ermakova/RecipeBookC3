package pro.sky.recipebookc3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.exception.FileProcessingException;
import pro.sky.recipebookc3.exception.ExistsException;
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.FilesService;
import pro.sky.recipebookc3.services.IngredientService;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service

public class IngredientServiceImpl implements IngredientService {
    private final FilesService filesService;

    public IngredientServiceImpl(@Qualifier("ingredientFileService") FilesService filesService) {
        this.filesService = filesService;
    }

    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer idIngr = 0;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientMap.containsValue(ingredient)) {
            throw new ExistsException("такой ингредиент уже есть");
        }
        ingredientMap.put(idIngr++, ingredient);
        saveToFileIngredient();
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer idIngr) {
        if (!ingredientMap.containsKey(idIngr)) {
            throw new NotFoundException("Нет ингредиента с указанным id");
        }
        return ingredientMap.get(idIngr);
    }

    @Override
    public Collection<Ingredient> getAllIngredient() {
        return ingredientMap.values();
    }

    @Override
    public Ingredient removeIngredient(Integer idIngr) {
        if (!ingredientMap.containsKey(idIngr)) {
            throw new NotFoundException("Нет ингредиента с указанным id");
        }
        saveToFileIngredient();
        return ingredientMap.remove(idIngr);
    }

    @Override
    public Ingredient updateIngredient(Integer idIngr, Ingredient ingredient) {
        if (!ingredientMap.containsKey(idIngr)) {
            throw new NotFoundException("Нет ингредиента с указанным id");
        }
        ingredientMap.put(idIngr, ingredient);
        saveToFileIngredient();
        return ingredient;
    }

    @Override
    public void saveToFileIngredient() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не сохранен");
        }
    }

    @Override
    public void readFromFileIngredient() throws FileProcessingException {
        try {
            String json = filesService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json,
                    new TypeReference<HashMap<Integer, Ingredient>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    @PostConstruct
    private void initIngredient() throws FileProcessingException {
        readFromFileIngredient();
    }

}
