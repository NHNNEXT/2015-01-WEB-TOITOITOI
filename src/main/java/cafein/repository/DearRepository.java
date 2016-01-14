package cafein.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.cafe.Dear;

public interface DearRepository extends MongoRepository<Dear, ObjectId> {
	Page<Dear> findByPlaceId(ObjectId placeId, Pageable pageable);
}
