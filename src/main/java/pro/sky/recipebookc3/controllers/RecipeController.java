package pro.sky.recipebookc3.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.RecipeService;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/idRec")
    Recipe getRecipe(@PathVariable Integer idRec) {
        return recipeService.getRecipe(idRec);
    }

    @PostMapping
    Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }
}
