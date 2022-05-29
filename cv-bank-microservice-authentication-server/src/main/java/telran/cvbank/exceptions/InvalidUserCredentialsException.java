package telran.cvbank.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -743377149456611443L;

	public InvalidUserCredentialsException(String msg) {
		super(msg);
	}
}
