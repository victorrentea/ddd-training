package victor.training.ddd.spring.web;

public class ErrorResponseWrapper {

	private ErrorResponse error;

	public ErrorResponseWrapper(ErrorResponse error) {
		this.error = error;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}
}
