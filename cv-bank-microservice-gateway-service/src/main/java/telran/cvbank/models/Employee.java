package telran.cvbank.models;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Employee {

    String email;
    String password;
    String firstName;
    String lastName;
    Set<String> cv_id = new HashSet<>();
    Set<String> roles = new HashSet<>();
}
