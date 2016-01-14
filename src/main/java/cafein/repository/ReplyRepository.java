package cafein.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.reply.Reply;

public interface ReplyRepository extends MongoRepository<Reply, Integer> {
	List<Reply> findByPostId(ObjectId postId, Sort sort);

	default List<Reply> getReplys(ObjectId postId) {
		return findByPostId(postId, new Sort(Sort.Direction.DESC, "createdtime"));
	}
}
