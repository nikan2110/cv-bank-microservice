package telran.cvbank.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewCVDto {
	
	String firstName;
	String lastName;
	String email;
	String phone;
	int verificationLevel;
	boolean isRelevant;
	String isRelocated;
	String salary;
	String address;
	String position;
	String preambule;
	Set<String> skills;
	@JsonInclude(Include.NON_NULL)
	List<ExperienceDto> experience;
	List<EducationDto> educations;
	OtherDto other;
	@JsonInclude(Include.NON_NULL)
	Set<String> links;
	Integer template;

}
