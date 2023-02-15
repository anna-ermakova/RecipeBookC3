package pro.sky.recipebookc3.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipebookc3.exception.FileProcessingException;
import pro.sky.recipebookc3.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("recipeFileService")

public class RecipeFileServiceImpl implements FilesService {
    @Value("src/main/resources")
    private String dataFilePathIngredient;
    @Value("recipe.json")
    private String dataFileNameRecipe;

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameRecipe), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile() {
        if (Files.exists(Path.of(dataFilePathIngredient, dataFileNameRecipe))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataFileNameRecipe));
            } catch (IOException e) {
                throw new FileProcessingException("не удалось прочитать файл");
            }
        } else {
            return "{}";
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameRecipe);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFileTxt() {
        return new File(dataFilePathIngredient + "/" + dataFileNameRecipe);
    }
}
