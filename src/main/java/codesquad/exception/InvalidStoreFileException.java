package codesquad.exception;

public class InvalidStoreFileException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidStoreFileException(String message) {
		super(message);
	}
}