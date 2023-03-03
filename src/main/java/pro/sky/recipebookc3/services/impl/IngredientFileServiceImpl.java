package pro.sky.recipebookc3.services.impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipebookc3.exception.FileProcessingException;
import pro.sky.recipebookc3.model.Recipe;
import pro.sky.recipebookc3.services.FilesService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service("ingredientFileService")
public class IngredientFileServiceImpl implements FilesService {
    @Value("${path.to.files}")
    private String dataFilePathIngredient;
    @Value("${name.of.ingredient.file}")
    private String dataFileNameIngredient;
    public Path path;

    @PostConstruct
    private void init() {
        path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
    }

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameIngredient), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    @Override
    public String readFromFile() {
        if (Files.exists(Path.of(dataFilePathIngredient, dataFileNameIngredient))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataFileNameIngredient));
            } catch (IOException e) {
                throw new FileProcessingException("не удалось прочитать файл");
            }
        } else {
            return "{ }";
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePathIngredient + "/" + dataFileNameIngredient);
    }

    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
        File file = getDataFile();
        return new InputStreamResource(new FileInputStream(file));
    }



    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
        cleanDataFile();
        FileOutputStream fos = new FileOutputStream(getDataFile());
        try {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new FileProcessingException("Проблема сохранения файла");
        }
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Map<Integer, Recipe> getRecipeMap() {
        return null;
    }

    @Override
    public InputStreamResource exportTxtFile(Map<Integer, Recipe> recipeMap) throws IOException {
        return null;
    }

}



