package codesquad;

public class CannotDeleteException extends RuntimeException{

    public CannotDeleteException(String message){
        super(message);
    }
}
