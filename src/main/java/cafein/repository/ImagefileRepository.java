package cafein.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cafein.file.ImageFile;

public interface ImagefileRepository extends MongoRepository<ImageFile, Integer> {
	// 아직 되는지 모름.
//	default void addFileInfo(ImageFile imagefile) {
//		save(imagefile);
//	}
	
	ImageFile findByPostId(Integer postId);
}
