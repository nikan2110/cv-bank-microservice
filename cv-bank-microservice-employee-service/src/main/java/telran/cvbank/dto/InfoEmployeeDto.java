package telran.cvbank.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InfoEmployeeDto {
	@JsonProperty("email")
	String email;
	String firstName;
	String lastName;
	Set<String> cv_id;
}
