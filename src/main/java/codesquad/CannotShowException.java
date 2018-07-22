package codesquad;

public class CannotShowException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotShowException() {
        super();
    }

    public CannotShowException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CannotShowException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotShowException(String message) {
        super(message);
    }

    public CannotShowException(Throwable cause) {
        super(cause);
    }
}
