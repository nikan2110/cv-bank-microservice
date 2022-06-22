package telran.cvbank.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import telran.cvbank.model.ErrorMessage;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EmployeeAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> handleEmployeeAlreadyExistException(EmployeeAlreadyExistException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.CONFLICT.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest webRequest) {
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
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.description(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
