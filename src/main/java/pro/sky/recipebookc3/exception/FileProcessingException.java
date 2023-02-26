package pro.sky.recipebookc3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.transform.sax.SAXResult;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
    }

    protected FileProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}