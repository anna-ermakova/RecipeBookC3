package pro.sky.recipebookc3.services;

public interface FilesService {


    boolean saveToFileRec(String json);

    boolean saveToFileIngr(String json);

    String readFromFileRec();

    String readFromFileIngr();

    boolean cleanDataFileRec();

    boolean cleanDataFileIngr();

}
