package cafein.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.file.Image;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {
	// 아직 되는지 모름.
//	default void addFileInfo(ImageFile imagefile) {
//		save(imagefile);
//	}
	
//	Image findByPostId(ObjectId postId);
}
