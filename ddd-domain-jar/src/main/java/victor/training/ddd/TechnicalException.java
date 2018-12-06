package victor.training.ddd;

@SuppressWarnings("serial")
public class TechnicalException extends RuntimeException {
	
	private final Object[] parameters;

	public TechnicalException(Throwable throwable) {
		this(throwable.getMessage(), null, new Object[] {});
	}
	public TechnicalException(String message) {
		this(message, null, new Object[] {});
	}

	public TechnicalException(String message, Throwable throwable) {
		this(message, throwable, new Object[] {});
	}

	public TechnicalException(String message, Object[] parameters) {
		this(message, null, parameters);
	}

	public TechnicalException(String message, Throwable throwable, Object[] parameters) {
		super(message, throwable);
		this.parameters = parameters;
	}
	
	public Object[] getParametersObject() {
		return parameters;
	}
}
