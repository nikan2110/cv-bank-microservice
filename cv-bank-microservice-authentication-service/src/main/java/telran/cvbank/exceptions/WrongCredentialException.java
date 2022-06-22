package telran.cvbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongCredentialException extends RuntimeException {

	public WrongCredentialException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4011235189946596415L;

}
