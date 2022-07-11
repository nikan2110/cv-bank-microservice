package telran.cvbank.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "Employees")
public class Employee {
    @Id
    String email;
    String password;
    String firstName;
    String lastName;
    Set<String> cv_id = new HashSet<>();
    Set<String> roles = new HashSet<>();
}
