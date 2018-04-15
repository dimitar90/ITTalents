package pharm.pharmacies;

public class PharmacyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4915029113622638291L;

	public PharmacyException() {
		super();
	}

	public PharmacyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PharmacyException(String message, Throwable cause) {
		super(message, cause);
	}

	public PharmacyException(String message) {
		super(message);
	}

	public PharmacyException(Throwable cause) {
		super(cause);
	}

}
