package pro.sky.recipebookc3.services;

import java.io.File;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFileTxt();

}
