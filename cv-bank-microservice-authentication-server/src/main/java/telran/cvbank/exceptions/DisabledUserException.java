package telran.cvbank.exceptions;

public class DisabledUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7667927427895847641L;

	public DisabledUserException(String msg) {
		super(msg);
	}

}
