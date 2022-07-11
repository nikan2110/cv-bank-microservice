package telran.cvbank.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.cvbank.models.CV;

public interface CVRepository extends MongoRepository<CV, String> {

    Stream<CV> findBycvIdIn(Collection<String> cvsId);

    Stream<CV> findByisPublishedTrue();

    Stream<CV> findBydatePublished(LocalDate date);

}
