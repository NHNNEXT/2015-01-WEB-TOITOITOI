package cafein.post;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.cafe.CandidateDAO;
import cafein.file.FileDAO;
import cafein.reply.ReplyDAO;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
@RequestMapping("/api/place/{placeId}")
public class APIPostController {
	private static final Logger logger = LoggerFactory.getLogger(APIPostController.class);
	@Autowired
	private ReplyDAO replydao;
	@Autowired
	private PostDAO postdao;
	@Autowired
	private CandidateDAO candidatedao;
	@Autowired
	private FileDAO filedao;

	@RequestMapping(value = "/dear", method = RequestMethod.GET)
	public Result getDearList(@PathVariable("placeId") Integer placeId, @RequestParam("page") Integer nPage) {

		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || !Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

		List<Map<String, Object>> result = postdao.getDearList(placeId, nPage);
		if (result.isEmpty()) {
			logger.debug(result.toString());
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

		List<Map<String,Object>> result = postdao.getPreviews(placeId, dearId, nPage); 
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
		post = postdao.getPostByPostId(postId);
		post.setReplyList(replydao.getReplys(postId));
		return Result.success(post);
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public Result createPost(@PathVariable Integer placeId, @RequestParam String content, @RequestParam String dear,
			@RequestParam(required = false) String storedFileName) {
		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(content) || !Validation.isValidParameter(dear)) {
			throw new IllegalArgumentException();
		}
		if (!Validation.isValidMaxLenPost(content)) {
			throw new IllegalArgumentLengthException();
		}

		Post newPost = new Post(dear, content, placeId);
		logger.debug(newPost.toString());
		newPost = postdao.addPost(newPost);
		Integer postId = newPost.getId();

		if (storedFileName != null) {
			filedao.updatePostId(postId, storedFileName);
		}

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
