package codesquad;

public class CannotShowException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotShowException() {
        super();
    }

    public CannotShowException(String message) {
        super(message);
    }
}