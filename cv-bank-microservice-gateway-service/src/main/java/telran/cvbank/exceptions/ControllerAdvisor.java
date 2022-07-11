package telran.cvbank.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import telran.cvbank.models.ErrorMessage;

@ControllerAdvice
public class ControllerAdvisor {
	
	@ExceptionHandler(AuthorizationHeaderIsInvalidException.class)
	public ResponseEntity<ErrorMessage> handleAuthorizationHeaderIsInvalidException(AuthorizationHeaderIsInvalidException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(AuthorizationHeaderIsMissingException.class)
	public ResponseEntity<ErrorMessage> handleAuthorizationHeaderIsMissingException(AuthorizationHeaderIsMissingException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(WrongCredentialException.class)
	public ResponseEntity<ErrorMessage> handleWrongCredentialException(WrongCredentialException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.FORBIDDEN.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorMessage> handleTokenException(JwtException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.FORBIDDEN.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorMessage> handleExpiredJwtException(ExpiredJwtException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.FORBIDDEN.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
	}

	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest webRequest) {
//		ErrorMessage errorMessage = ErrorMessage.builder()
//				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//				.timestamp(LocalDateTime.now())
//				.message(ex.getMessage())
//				.description(webRequest.getDescription(false))
//				.build();
//		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	

}
