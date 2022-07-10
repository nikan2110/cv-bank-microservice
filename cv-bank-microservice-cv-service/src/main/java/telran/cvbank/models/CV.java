package telran.cvbank.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CVs")
@EqualsAndHashCode(of = "cvId")
@ToString
public class CV {

    @Id
    ObjectId cvId;
    String firstName;
    String lastName;
    String email;
    String phone;
    int verificationLevel;
    boolean isRelevant;
    String isRelocated;
    int salary;
    String address;
    String position;
    String preambule;
    Set<String> skills;
    List<Education> educations;
    List<Experience> experience;
    Other other;
    Set<String> links;
    Integer template;


    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    Point coordinates;
    LocalDate dateCreated = LocalDate.now();
    LocalDate datePublished;
    boolean isPublished;
    Set<String> hideFields = new HashSet<>();
}


