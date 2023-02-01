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
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.IngredientService;

import javax.validation.Valid;
import java.util.Collection;
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
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    ResponseEntity<Ingredient> getIngredient(@PathVariable Integer idIngr) {
        Ingredient ingredient = ingredientService.getIngredient(idIngr);
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    @Operation(summary = "Добавление ингредиента.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    ResponseEntity<Ingredient> addIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }

    @PutMapping("/{idIngr}")
    @Operation(summary = "Изменение ингредиента по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Игредиент изменен.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    ResponseEntity<Ingredient> updateIngredient(@PathVariable Integer idIngr, @Valid @RequestBody Ingredient ingredient) {
        Ingredient ingrReturn = ingredientService.updateIngredient(idIngr, ingredient);
        ingredientService.saveToFileIngr();
        return ResponseEntity.ok(ingrReturn);
    }

    @DeleteMapping("/{idIngr}")
    @Operation(summary = "Удаление ингредиента по id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален."
            )})
    @Parameters(value = {@Parameter(name = "idIngr", example = "1")})
    ResponseEntity<Ingredient> removeIngredient(@PathVariable Integer idIngr) {
        return ResponseEntity.ok(ingredientService.removeIngredient(idIngr));
    }


    @GetMapping
    @Operation(summary = "Получение всех ингредиентов.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все ингредиенты получены.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    public Collection<Ingredient> getAllIngredients() {
        return this.ingredientService.getAllIngredient();
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
