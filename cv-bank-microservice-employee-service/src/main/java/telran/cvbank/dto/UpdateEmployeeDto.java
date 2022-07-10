package telran.cvbank.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeDto {
	
	@NotNull(message = "You didn't specify a firstName")
	@NotEmpty(message = "FirstName cannot be empty")
	String firstName;
	@NotNull(message = "You didn't specify a lastName")
	@NotEmpty(message = "LastName cannot be empty")
	String lastName;
}
