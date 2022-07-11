package telran.cvbank.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {
	
	int statusCode;
	LocalDateTime timestamp;
	String message;
	String description;
	

}
