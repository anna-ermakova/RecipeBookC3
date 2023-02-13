package pro.sky.recipebookc3.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipebookc3.services.FilesService;

import java.io.File;

@Service("recipeFileService")
@RequiredArgsConstructor
public class RecipeFileServiceImpl implements FilesService {
    @Value("${name.of.recipe.data.file}")
    private String dataFileNameRec;

    @Override
    public boolean saveToFile(String json) {
        return false;
    }

    @Override
    public String readFromFile() {
        return null;
    }

    @Override
    public boolean cleanDataFile() {
        return false;
    }

    @Override
    public File getDataFileTxt() {
        return null;
    }
}
