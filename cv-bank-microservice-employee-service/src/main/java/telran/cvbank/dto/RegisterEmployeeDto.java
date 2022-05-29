package telran.cvbank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterEmployeeDto {
	String email;
	String firstName;
	String lastName;
	String password;
}
