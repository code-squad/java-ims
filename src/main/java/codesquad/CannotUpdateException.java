package codesquad;

public class CannotUpdateException extends RuntimeException{
    private static final long serialVersionUID = 1L; //얘 모임

    public CannotUpdateException(String message) {
        super(message);
    }
}
