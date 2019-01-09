package codesquad;

public class CannotApplyException extends RuntimeException {
    private static final long serialVersionUID = 1L; //얘 모임

    public CannotApplyException(String message) {
        super(message);
    }

}
