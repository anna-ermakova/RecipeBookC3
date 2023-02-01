package pro.sky.recipebookc3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.internal.access.JavaUtilResourceBundleAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipebookc3.model.Ingredient;
import pro.sky.recipebookc3.services.FilesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.ingredient.data.file}")
    private String dataFileNameIngr;
    @Value("${name.of.recipe.data.file}")
    private String dataFileNameRec;

    /*@Override
    public boolean saveToFileRec(String json) {
        try {
            cleanDataFileRec();
            Files.writeString(Path.of(dataFilePath, dataFileNameRec), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }*/

    public void saveToJsonFile(Ingredient ingredient, String dataFileNameIngr) {
        Path path = Path.of(dataFileNameIngr + ".json");
        try {
            String json = new ObjectMapper().writeValueAsString(ingredient);
            Files.createDirectories(dataFilePath.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean saveToFileIngr(String json) {
        try {
            cleanDataFileIngr();
            Files.writeString(Path.of(dataFilePath, dataFileNameIngr), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    @Override
    public String readFromFileRec() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameRec));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public String readFromFileIngr() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileNameIngr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanDataFileRec() {
        try {
            Path path = Path.of(dataFilePath, dataFileNameRec);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cleanDataFileIngr() {
        try {
            Path path = Path.of(dataFilePath, dataFileNameIngr);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
