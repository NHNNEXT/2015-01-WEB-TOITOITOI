package cafein.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.cafe.Place;

public interface PlaceRepository extends MongoRepository<Place, Integer> {
	default Place getPlaceById(Integer id) {
		return findOne(id);
	}
}
