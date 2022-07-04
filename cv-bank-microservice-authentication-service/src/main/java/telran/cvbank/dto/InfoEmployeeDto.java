package telran.cvbank.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class InfoEmployeeDto {
	String email;
	String firstName;
	String lastName;
	Set<String> cv_id;
	Set<String> roles;
}
