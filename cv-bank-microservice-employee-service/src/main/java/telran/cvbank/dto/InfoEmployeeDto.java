package telran.cvbank.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoEmployeeDto {
	String email;
	String firstName;
	String lastName;
	Set<String> cv_id;
}
