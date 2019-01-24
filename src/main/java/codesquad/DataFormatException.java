package codesquad;

public class DataFormatException extends RuntimeException {
    public DataFormatException() {
        super();
    }

    public DataFormatException(String error) {
        super(error);
    }
}
