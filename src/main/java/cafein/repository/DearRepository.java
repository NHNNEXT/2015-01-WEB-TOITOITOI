package cafein.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.cafe.Dear;

public interface DearRepository extends MongoRepository<Dear, Integer> {
	int pageSize = 10;
	Page<Dear> findByPlaceId(Integer placeId, Pageable pageable);
	
	default List<Dear> getDearList(Integer placeId, Integer nPage) {
		return findByPlaceId(placeId, new PageRequest(nPage-1, pageSize, Sort.Direction.DESC, "totalPostNum")).getContent();
	}
}
