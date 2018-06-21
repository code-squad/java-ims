package codesquad;

public class InvalidLoginInfoException extends Exception {

    public InvalidLoginInfoException() {
    }

    public InvalidLoginInfoException(String message) {
        super(message);
    }
}
