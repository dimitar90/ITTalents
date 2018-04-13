package easter.exceptions;

public class EggException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7272199532095418154L;

	public EggException() {
		super();
	}

	public EggException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EggException(String message, Throwable cause) {
		super(message, cause);
	}

	public EggException(String message) {
		super(message);
	}

	public EggException(Throwable cause) {
		super(cause);
	}

}
