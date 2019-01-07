package support.domain;

public class Result {
    private boolean valid;
    private String errorMessage;

    public static Result ok(){
        return new Result(true, null);
    }

    public static Result error(String errorMessage){
        return new Result(false, errorMessage);
    }

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
