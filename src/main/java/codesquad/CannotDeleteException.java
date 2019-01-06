package codesquad;

public class CannotDeleteException extends RuntimeException {
    private static final long serialVersionUID = 1L; //얘 모임

    public CannotDeleteException(String message) {
        super(message);
    }
}
