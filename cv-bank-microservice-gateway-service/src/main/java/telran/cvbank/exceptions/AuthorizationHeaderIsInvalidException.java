package telran.cvbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
public class AuthorizationHeaderIsInvalidException extends RuntimeException {

	public AuthorizationHeaderIsInvalidException(String errorMessage) {
		super("Error: " + errorMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6107372994418956411L;

}
