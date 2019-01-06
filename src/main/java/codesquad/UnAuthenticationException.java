package codesquad;

public class UnAuthenticationException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String NOT_LOGIN_MESSAGE = "아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.";
    public UnAuthenticationException() {
        super(NOT_LOGIN_MESSAGE);
    }

    public UnAuthenticationException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(Throwable cause) {
        super(cause);
    }
}
