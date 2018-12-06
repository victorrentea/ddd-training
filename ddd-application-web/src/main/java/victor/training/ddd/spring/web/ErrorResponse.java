package victor.training.ddd.spring.web;

public class ErrorResponse {

	public final String errorCode;

	public final String userMessage;

	public ErrorResponse(String errorCode, String userMessage) {
		this.errorCode = errorCode;
		this.userMessage = userMessage;
	}
}
