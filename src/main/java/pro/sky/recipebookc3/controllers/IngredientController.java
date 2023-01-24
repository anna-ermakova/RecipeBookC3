package pro.sky.recipebookc3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.IngredientService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Tag(name = "Ингредиенты.", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами.")

public class IngredientController {
    private final IngredientService ingredientService;


    @GetMapping("/{idIngr}")
    @Operation(summary = "Получение ингредиента по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Игредиент получен.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    Ingredient getIngredient(@PathVariable Integer idIngr) {
        return ingredientService.getIngredient(idIngr);
    }

    @PostMapping
    @Operation(summary = "Добавление ингредиента в список ингредиентов.")
    Ingredient addIngredient(@Valid @RequestBody Ingredient ingredient) {
            return ingredientService.addIngredient(ingredient);
    }

    @PutMapping("/{idIngr}")
    @Operation(summary = "Изменение ингредиента по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Игредиент изменен.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    public ResponseEntity<Ingredient> editIngredient(@Valid @RequestBody Ingredient ingredients, @PathVariable long idIngr) {
        if (!ingredientService.existById(idIngr)) {
            return ResponseEntity.notFound().build();
        }
        ingredientService.editIngredient(idIngr, ingredients);
        return ResponseEntity.ok(ingredients);
    }

    @DeleteMapping("/{idIngr}")
    @Operation(summary = "Удаление ингредиента по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален."
            )})
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    public ResponseEntity<Void> deleteIngredient(@PathVariable long idIngr) {
        if (ingredientService.deleteIngredient(idIngr)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Получение всех ингредиентов.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все ингредиенты получены.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
