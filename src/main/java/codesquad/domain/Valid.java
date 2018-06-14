package codesquad.domain;

public class Valid {

    private boolean valid;
    private String errorMessage;

    public Valid(boolean valid, String errorMessage){
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Valid error(String errorMessage){
        return new Valid(false, errorMessage);
    }

    public static Valid valid(){
        return new Valid(true, null);
    }
}
