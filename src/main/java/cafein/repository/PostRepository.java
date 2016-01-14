package cafein.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.post.Post;

public interface PostRepository extends MongoRepository<Post, ObjectId> {
	Post findOne(ObjectId id);
	Page<Post> findByDearId(ObjectId dearId, Pageable pageable);

//	default List<Post> getPreviews(Integer placeId, Integer dearId, Integer nPage) {
//		// TODO : placeId 필요없음!
//		return findByDearId(dearId, new PageRequest(nPage-1, pageSize, Direction.DESC, "likes")).getContent();
//	}
}
