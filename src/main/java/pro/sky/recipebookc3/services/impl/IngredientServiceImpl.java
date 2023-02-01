package pro.sky.recipebookc3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.FilesService;
import pro.sky.recipebookc3.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    final private FilesService filesServiceIngr;
    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer idIngr = 0;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(idIngr++, ingredient);
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
        return ingredientMap.remove(idIngr);
    }

    @Override
    public Ingredient updateIngredient(Integer idIngr, Ingredient ingredient) {
        if (!ingredientMap.containsKey(idIngr)) {
            throw new NotFoundException("Нет ингредиента с указанным id");
        }
        ingredientMap.put(idIngr, ingredient);
        return ingredient;
    }
    @Override
    public void saveToFileIngr() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesServiceIngr.saveToFileIngr(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFileIngr() {
        String json = filesServiceIngr.readFromFileIngr();
        try {
            ingredientMap = new ObjectMapper().readValue(json,
                    new TypeReference<Map<Integer, Ingredient>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        readFromFileIngr();
    }
}
