package cafein.util;

import java.util.List;
import java.util.Map;

public class Result {
	private boolean success;
	private String errorMessage;
	private Object result;
	private List<Map<String,Object>> resultList;
	
	private Result(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}
	
	private Result(boolean success, String errorMessage, Object result) {
		this.success = success;
		this.errorMessage = errorMessage;
		this.result = result;
	}
	
	public Result(boolean success, String errorMessage, List<Map<String, Object>> resultList) {
		super();
		this.success = success;
		this.errorMessage = errorMessage;
		this.resultList = resultList;
	}
	
	public static Result success() {
		return new Result(true, null);
	}
	public static Result success(Object result) {
		return new Result(true, null,result);
	}
	public static Result success(Map<String, Object> resultlist){
		return new Result(true,null,resultlist);
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
