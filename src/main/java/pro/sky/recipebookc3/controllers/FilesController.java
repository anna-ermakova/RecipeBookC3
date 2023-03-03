package pro.sky.recipebookc3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipebookc3.services.FilesService;
import pro.sky.recipebookc3.services.RecipeService;
import pro.sky.recipebookc3.services.impl.IngredientFileServiceImpl;

import javax.print.attribute.ResolutionSyntax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
@Tag(name = "Files", description = "CRUD операции для работы с файлами")
public class FilesController {
    private final FilesService recipeFileService;
    private final FilesService ingredientFileService;
    private final RecipeService recipeService;

    public FilesController(@Qualifier("ingredientFileService") FilesService ingredientFileService,
                           @Qualifier("recipeFileService") FilesService recipeFileService,
                           RecipeService recipeService) {
        this.ingredientFileService = ingredientFileService;
        this.recipeFileService = recipeFileService;
        this.recipeService = recipeService;
    }

    @GetMapping("/ingredient/export")
    @Operation(description = "Экспорт файла ингредиентов")
    public ResponseEntity<InputStreamResource> downloadIngredientFile() throws IOException {
        InputStreamResource inputStreamResource = ingredientFileService.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(ingredientFileService.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Ingredients.json\"")
                .body(inputStreamResource);
    }

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Импорт файла ингредиентов")
    public ResponseEntity<Void> upLoadDataFileIngredient(@RequestParam MultipartFile file) throws FileNotFoundException {
        ingredientFileService.importFile(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/export")
    @Operation(description = "Экспорт файла рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws IOException {
        InputStreamResource inputStreamResource = recipeFileService.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(recipeFileService.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipe.json\"")
                .body(inputStreamResource);
    }

    @GetMapping("/recipe/exporttxt")
    @Operation(description = "Экспорт файла рецептов")
    public ResponseEntity<InputStreamResource> downloadTxtRecipeFile() throws IOException {
        InputStreamResource inputStreamResource = recipeFileService.exportTxtFile(recipeFileService.getRecipeMap());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(Files.size(recipeFileService.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"AllRecipes.txt\"")
                .body(inputStreamResource);
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Импорт файла рецептов")
    public ResponseEntity<Void> upLoadDataFileRecipe(@RequestParam MultipartFile file) throws FileNotFoundException {
        recipeFileService.importFile(file);
        return ResponseEntity.ok().build();
    }

}
