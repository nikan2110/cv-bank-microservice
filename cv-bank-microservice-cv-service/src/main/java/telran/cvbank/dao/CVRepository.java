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

//	@Aggregation(pipeline = {
//			"{$geoNear: {near : {type: 'Point', coordinates: [ ?0, ?1 ]}, distanceField: 'dist.calculated',\r\n"
//			+ "  maxDistance: ?2,\r\n"
//			+ "  query: {},\r\n"
//			+ "  spherical: true}}",
//			"{ $match : { $or: [{position : ?3}, {position : {$ne: null}} ] }}",  })
//	AggregationResults<CV> find(Double x, Double y, Integer i, String position);

}
