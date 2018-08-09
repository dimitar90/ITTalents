package hospital.human;

public class HumanException extends Exception {

	public HumanException() {
		super();
	}

	public HumanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HumanException(String message, Throwable cause) {
		super(message, cause);
	}

	public HumanException(String message) {
		super(message);
	}

	public HumanException(Throwable cause) {
		super(cause);
	}
	
}
