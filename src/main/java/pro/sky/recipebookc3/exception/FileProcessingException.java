package pro.sky.recipebookc3.exception;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException() {
        super("The problem during reading the file.");
    }

    public FileProcessingException(Throwable cause) {
        super(cause);
    }

    public FileProcessingException(String massage, Throwable cause) {
        super(massage, cause);
    }

    public FileProcessingException(String massage) {
        super(massage);

// protected FileProcessingException(String massage, Throwable cause, boolean enableSuppression,
        //   boolean writableStackTrace){
        //       super(massage, cause, enableSuppression, writableStackTrace);
        // }
    }
}