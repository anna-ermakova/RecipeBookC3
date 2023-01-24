package pro.sky.recipebookc3.services.impl;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.IngredientService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer idIngr = 0;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!ingredientMap.containsValue(ingredient)) {
            throw new IngredientExistsExceptoin();
        }
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
}
