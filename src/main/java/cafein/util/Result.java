package cafein.util;

public class Result {
	private boolean success;
	private String errorMessage;

	private Result(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}
	
	public static Result success() {
		return new Result(true, null);
	}
	
	public static Result failed(String errorMessage) {
		return new Result(false, errorMessage);
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
