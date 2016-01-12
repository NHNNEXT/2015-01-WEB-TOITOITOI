package cafein.post;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cafein.cafe.CandidateDAO;
import cafein.cafe.Dear;
import cafein.file.APIFileController;
import cafein.file.FileDAO;
import cafein.reply.ReplyDAO;
import cafein.repository.DearRepository;
import cafein.repository.PostRepository;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
@RequestMapping("/api/place/{placeId}")
public class APIPostController {
	private static final Logger logger = LoggerFactory.getLogger(APIPostController.class);
	@Autowired
	private DearRepository dearDao;
	@Autowired
	private PostRepository postDao;
	@Autowired
	private ReplyDAO replydao;
	@Autowired
	private PostDAO postdao;
	@Autowired
	private CandidateDAO candidatedao;
	@Autowired
	private FileDAO filedao;
	@Autowired
	private APIFileController apiFileController;

	@RequestMapping(value = "/dear", method = RequestMethod.GET)
	public Result getDearList(@PathVariable("placeId") Integer placeId, @RequestParam("page") Integer nPage) {
		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || !Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

		List<Dear> result = dearDao.getDearList(placeId, nPage);
		if (result.isEmpty()) {
			return Result.failed("No more data.");
		}
		return Result.success(result);
	}

	// dearID로 변경
	@RequestMapping(value = "/dear/{dearId}/post", method = RequestMethod.GET)
	public Result getPostList(@PathVariable("placeId") Integer placeId, @PathVariable("dearId") Integer dearId,
			@RequestParam("page") Integer nPage) {
		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(dearId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || !Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

//		List<Map<String,Object>> result = postdao.getPreviews(placeId, dearId, nPage);
		List<Post> result = postDao.getPreviews(placeId, dearId, nPage);
		if(result.isEmpty()){
			return Result.failed("No more data.");
		}
		return Result.success(result);
	}

	@RequestMapping(value = "/dear/{dearId}/post/{postId}", method = RequestMethod.GET)
	public Result viewPost(@PathVariable Integer postId) {
		Post post = null;

		if (!Validation.isValidParameter(postId) || !Validation.isValidParameterType(postId)) {
			throw new IllegalAPIPathException();
		}
		post = postDao.getPostByPostId(postId);
		post.setReplyList(replydao.getReplys(postId));
		return Result.success(post);
	}


	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public Result createPost(@PathVariable Integer placeId, @RequestParam String content, @RequestParam String dear,
			MultipartHttpServletRequest request) {
		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(content) || !Validation.isValidParameter(dear)) {
			throw new IllegalArgumentException();
		}
		if (!Validation.isValidMaxLenPost(content)) {
			throw new IllegalArgumentLengthException();
		}
		logger.debug("dear??"+dear);
		Iterator<String> iterator = request.getFileNames();
		MultipartFile multipartFile = null;
		while (iterator.hasNext()) {
			multipartFile = request.getFile(iterator.next());
			logger.debug(multipartFile.getOriginalFilename());
		}
		Post newPost = new Post(dear, content, placeId);
		logger.debug(newPost.toString());

		if (multipartFile.isEmpty() == false) {
			logger.debug("multipartFile  exist!");
			String storedFileName = apiFileController.insertFile(multipartFile);
			
			if (storedFileName != null) {
				newPost = postdao.addPost(newPost);
				Integer postId = newPost.getId();
				filedao.updatePostId(postId, storedFileName);
				newPost.setName(dear);
				return Result.success(newPost);
			}
		return Result.failed("Fail to save file in Server");
		}
		newPost = postdao.addPost(newPost);
		newPost.setName(dear);
		return Result.success(newPost);
	}

	@RequestMapping(value = "recommend", method = RequestMethod.GET)
	public Result getRecommendedDear(@PathVariable Integer placeId) {

		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		return Result.success(candidatedao.getRecommendedDears(placeId));
	}

}
