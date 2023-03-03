package pro.sky.recipebookc3.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipebookc3.model.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();

    InputStreamResource exportFile() throws FileNotFoundException;

    InputStreamResource exportTxtFile(Map<Integer, Recipe> recipeMap) throws IOException;

    void importFile(MultipartFile file) throws FileNotFoundException;

    public Path getPath();

    Map<Integer, Recipe> getRecipeMap();
}
