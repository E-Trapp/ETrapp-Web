package cat.udl.etrapp.server.api.exceptions;

public class UnauthorizedResourceAccessException extends Exception {

    public UnauthorizedResourceAccessException() {
        super();
    }

    public UnauthorizedResourceAccessException(String message) {
        super(message);
    }

    public UnauthorizedResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
