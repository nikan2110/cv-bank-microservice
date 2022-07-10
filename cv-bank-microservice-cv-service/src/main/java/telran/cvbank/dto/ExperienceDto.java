package telran.cvbank.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExperienceDto {
	
	String date;
	String company;
	String website;
	String address;
	Set<ProjectDto> projects;

}
