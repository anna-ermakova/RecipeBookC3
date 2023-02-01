package pro.sky.recipebookc3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.RecipeService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name = "Рецепты.", description = "CRUD-операции и другие эндпоинты для работы с рецептами.")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/{idRec}")
    @Operation(summary = "Поиск рецепта по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт найден.")
    })
    @Parameters(value = {@Parameter(name = "idRec", example = "1")})
    ResponseEntity<Recipe> getRecipe (@PathVariable Integer idRec) {
        Recipe recipe = recipeService.getRecipe(idRec);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    @Operation(summary = "Добавление рецепта.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Рецепт добавлен."
            )
    })
    ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    @PutMapping("/{idRec}")
    @Operation(summary = "Изменение рецепта по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Рецепт изменен.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })
    @Parameters(value = {
            @Parameter(name = "idRec", example = "1")
    })
    ResponseEntity<Recipe> updateRecipe(@PathVariable Integer idRec, @Valid @RequestBody Recipe recipe) {
        Recipe recReturn = recipeService.updateRecipe(idRec, recipe);
        recipeService.saveToFileRec();
        return ResponseEntity.ok(recReturn);
    }

    @DeleteMapping("/{idRec}")
    @Operation(summary = "Удаление рецепта по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален."
            )})
    @Parameters(value = {@Parameter(name = "idRec", example = "1")})
    ResponseEntity<Recipe> removeRecipe(@PathVariable Integer idRec) {
        return ResponseEntity.ok(recipeService.removeRecipe(idRec));
    }

    @GetMapping
    @Operation(summary = "Получение всех рецептов.", description = "Поиск без параметов.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рецепты получены."
            )
    })
    ResponseEntity<Collection<Recipe>> getRecipesByIngredientId() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }
}
