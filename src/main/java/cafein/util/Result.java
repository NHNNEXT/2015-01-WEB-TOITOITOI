package cafein.util;

public class Result {
	private boolean success;
	private String errorMessage;
	private Object result;
	
	private Result(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}
	private Result(boolean success, String errorMessage, Object result) {
		this.success = success;
		this.errorMessage = errorMessage;
		this.result = result;
	}
	
	public static Result success() {
		return new Result(true, null);
	}
	public static Result success(Object result) {
		return new Result(true, null,result);
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
