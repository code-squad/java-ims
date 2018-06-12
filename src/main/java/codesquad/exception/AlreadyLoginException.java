package codesquad.exception;

public class AlreadyLoginException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AlreadyLoginException() {
        super();
    }

    public AlreadyLoginException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlreadyLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyLoginException(String message) {
        super(message);
    }

    public AlreadyLoginException(Throwable cause) {
        super(cause);
    }


}
