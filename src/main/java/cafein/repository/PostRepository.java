package cafein.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cafein.post.Post;

public interface PostRepository extends MongoRepository<Post, Integer> {
	int pageSize = 20;
	@Query(value="{ 'dearId' : ?0 }", fields="{ '_id' : 1, 'content' : 1, 'likes' : 1 }")
	Page<Post> findByDearId(Integer dearId, Pageable pageable);

	default Post getPostByPostId(Integer postId) {
		return findOne(postId);
	}
	
	default List<Post> getPreviews(Integer placeId, Integer dearId, Integer nPage) {
		// TODO : placeId 필요없음!
		return findByDearId(dearId, new PageRequest(nPage-1, pageSize, Direction.DESC, "likes")).getContent();
	}
}
