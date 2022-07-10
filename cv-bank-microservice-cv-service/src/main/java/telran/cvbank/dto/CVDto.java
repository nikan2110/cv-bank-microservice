package telran.cvbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CVDto {

    @JsonInclude(Include.NON_NULL)
    String cvId;
    @JsonInclude(Include.NON_NULL)
    String firstName;
    @JsonInclude(Include.NON_NULL)
    String lastName;
    String email;
    @JsonInclude(Include.NON_NULL)
    String phone;
    int verificationLevel;
    boolean isRelevant;
    @JsonInclude(Include.NON_NULL)
    String isRelocated;
    int salary;
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
