package pro.sky.recipebookc3.exception;

public class ExistsException extends RuntimeException {
    public ExistsException() {
        super("The problem during reading the file.");
    }

    public ExistsException(Throwable cause) {
        super(cause);
    }

    public ExistsException(String massage, Throwable cause) {
        super(massage, cause);
    }

    public ExistsException(String massage) {
        super(massage);
    }
}
