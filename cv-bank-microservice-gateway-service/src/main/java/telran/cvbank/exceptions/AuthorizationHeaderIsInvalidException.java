package telran.cvbank.exceptions;

public class AuthorizationHeaderIsInvalidException extends RuntimeException {

	public AuthorizationHeaderIsInvalidException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6107372994418956411L;

}
