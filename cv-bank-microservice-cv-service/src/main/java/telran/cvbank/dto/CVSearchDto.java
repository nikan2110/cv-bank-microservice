package telran.cvbank.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CVSearchDto {
	
	String position;
	Set<String> skills;
	Integer minSalary;
	Integer maxSalary;
	String location;
	Integer distance;
	@JsonProperty
	boolean relocated;
	int verifiedLevel;

}
