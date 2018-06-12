package codesquad.exception;

public class LoginRequiredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginRequiredException() {
        super();
    }

    public LoginRequiredException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LoginRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginRequiredException(String message) {
        super(message);
    }

    public LoginRequiredException(Throwable cause) {
        super(cause);
    }

}
