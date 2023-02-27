package pro.sky.recipebookc3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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

    protected ExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}