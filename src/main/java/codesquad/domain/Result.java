package codesquad.domain;

public class Result {
	private boolean valid;
	private String errorMessage;

	public Result() {
	}

	public Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}

	public static Result success() {
		return new Result(true, null);
	}

	public static Result fail() {
		return new Result(false, "자신의 글만 수정,삭제가능합니다");
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
