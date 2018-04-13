package easter.exceptions;

public class KidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609245012149688911L;

	public KidException() {
		super();
	}

	public KidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KidException(String message, Throwable cause) {
		super(message, cause);
	}

	public KidException(String message) {
		super(message);
	}

	public KidException(Throwable cause) {
		super(cause);
	}

}
