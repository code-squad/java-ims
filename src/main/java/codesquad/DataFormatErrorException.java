package codesquad;

public class DataFormatErrorException extends RuntimeException {

    public DataFormatErrorException() {
        super();
    }

    public DataFormatErrorException(String message) {
        super(message);
    }
}
