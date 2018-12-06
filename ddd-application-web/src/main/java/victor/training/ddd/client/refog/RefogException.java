package victor.training.ddd.client.refog;

@SuppressWarnings("serial")
public class RefogException extends RuntimeException {
	public RefogException(Throwable cause) {
        super(cause);
    }

    public RefogException(String message) {
        super(message);
    }
	
}
