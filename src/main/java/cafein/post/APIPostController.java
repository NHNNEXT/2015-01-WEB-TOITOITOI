package cafein.post;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cafein.cafe.Dear;
import cafein.file.APIFileController;
import cafein.file.FileDAO;
import cafein.repository.PostRepository;
import cafein.repository.ReplyRepository;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
@RequestMapping("/api/place/{placeId}")
public class APIPostController {
	private static final Logger logger = LoggerFactory.getLogger(APIPostController.class);
	@Autowired
	private PostRepository postDao;
	@Autowired
	private PostDAO postdao;
	@Autowired
	private FileDAO filedao;
	@Autowired
	private APIFileController apiFileController;

	private Integer defaultPageSize = 20;
	// dearID로 변경
	@RequestMapping(value = "/dear/{dearId}/post", method = RequestMethod.GET)
	public Result getPostList(@PathVariable("placeId") ObjectId placeId, @PathVariable("dearId") ObjectId dearId,
			@RequestParam("page") Integer nPage) {
		List<Post> result = postDao.findByDearId(dearId, new PageRequest(nPage, defaultPageSize, Direction.DESC, "likes")).getContent();
		if (result.isEmpty()) {
			return Result.failed("No more data.");
		}
		return Result.success(result);
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public Result createPost(@PathVariable ObjectId placeId, @RequestParam String content, @RequestParam String dear,
			MultipartHttpServletRequest request) {
		if (!Validation.isValidParameter(content) || !Validation.isValidParameter(dear)) {
			throw new IllegalArgumentException();
		}
		if (!Validation.isValidMaxLenPost(content)) {
			throw new IllegalArgumentLengthException();
		}
		logger.debug("dear??" + dear);
		Iterator<String> iterator = request.getFileNames();
		MultipartFile multipartFile = null;
		while (iterator.hasNext()) {
			multipartFile = request.getFile(iterator.next());
			logger.debug(multipartFile.getOriginalFilename());
		}
		Post newPost = new Post(content);
		logger.debug(newPost.toString());

		Map<String, Object> result = new HashMap<String, Object>();

		if (multipartFile.isEmpty() == false) {
			logger.debug("multipartFile  exist!");
			String storedFileName = apiFileController.insertFile(multipartFile);

//			if (storedFileName != null) {
//				Dear newDear = new Dear();
//				newDear.setId(postdao.getDearId(dear).toString());
//				newDear.setName(dear);
//				newDear.setPlaceId(placeId.toString());
//				result.put("dear", newDear);
//
//				newPost.setDearId(Integer.getInteger(newDear.getId()));
//				newPost = postdao.addPost(newPost, placeId);
//				Integer postId = newPost.getId();
//				filedao.updatePostId(postId, storedFileName);
//				result.put("post", newPost);
//				return Result.success(result);
//			}
			return Result.failed("Fail to save file in Server");
		}
		Dear newDear = new Dear();
		newDear.setId(postdao.getDearId(dear).toString());
		newDear.setName(dear);
		newDear.setPlaceId(placeId.toString());
		result.put("dear", newDear);

//		newPost.setDearId(Integer.getInteger(newDear.getId()));
//		newPost = postdao.addPost(newPost, placeId);
		result.put("post", newPost);
		return Result.success(result);
	}

}
