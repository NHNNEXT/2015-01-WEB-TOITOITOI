package cafein.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.cafe.Place;

public interface PlaceRepository extends MongoRepository<Place, ObjectId> {
	Place findOneByUri(String uri);
}
