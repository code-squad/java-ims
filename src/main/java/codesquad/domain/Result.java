package codesquad.domain;

public class Result<T> {
    private boolean valid;
    private String errMessage;
    private T result;

    private Result(boolean valid, T result) {
        this.valid = valid;
        this.result = result;
    }

    public static <T> Result ok(T result) {
        return new Result(true, result);
    }

    public static Result fail(String errMessage) {
        return new Result(false, errMessage);
    }

    public boolean isValid() {
        return valid;
    }

    public T get() {
        return result;
    }
}
