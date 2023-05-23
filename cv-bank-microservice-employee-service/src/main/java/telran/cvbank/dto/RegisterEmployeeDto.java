package telran.cvbank.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterEmployeeDto {
	
	@NotNull(message = "You didn't specify an email address")
	@NotEmpty(message = "Email cannot be empty")
	String email;
	@NotNull(message = "You didn't specify a firstName")
	@NotEmpty(message = "FirstName cannot be empty")
	String firstName;
	@NotNull(message = "You didn't specify a lastName")
	@NotEmpty(message = "LastName cannot be empty")
	String lastName;
	@Size(min = 6, max = 16, message = "Password must be from 6 to 16 symbols")
	@NotNull(message = "You didn't specify a password")
	@NotEmpty(message = "Password cannot be empty")
	String password;
}
