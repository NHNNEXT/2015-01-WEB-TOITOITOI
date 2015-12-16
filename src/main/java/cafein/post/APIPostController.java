package cafein.post;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cafein.reply.ReplyDAO;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Validation;

@RestController
@RequestMapping("/api/place/{placeId}")
public class APIPostController {
	private static final Logger logger = LoggerFactory.getLogger(APIPostController.class);
	@Autowired
	private ReplyDAO replydao;
	@Autowired
	private PostDAO postdao;

	@RequestMapping(value = "/dear", method = RequestMethod.GET)
	public Map<String, List<Post>> getDearList(@PathVariable("placeId") Integer placeId,
			@RequestParam("page") Integer nPage) {

		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || !Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

		Map<String, List<Post>> dearsWithPosts = new HashMap<String, List<Post>>();

		dearsWithPosts = postdao.getDearsWithPreviews(placeId, nPage);
		logger.debug(dearsWithPosts.toString());

		return dearsWithPosts;
	}

	@RequestMapping(value = "/dear/{dearName}/post", method = RequestMethod.GET)
	public List<Post> getPostList(@PathVariable("placeId") Integer placeId, @PathVariable("dearName") String dear,
			@RequestParam("page") Integer nPage) {
		if (!Validation.isValidParameter(placeId) || Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(dear)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

		return (postdao.getPreviews(placeId, dear, nPage));
	}

	@RequestMapping(value = "/dear/{dearName}/post/{postId}", method = RequestMethod.GET)
	public Post viewPost(@PathVariable Integer postId) {
		Post post = null;

		if (!Validation.isValidParameter(postId) || Validation.isValidParameterType(postId)) {
			throw new IllegalAPIPathException();
		}
		try {
			post = postdao.getPostByPostId(postId);
			post.setReplyList(replydao.getReplys(postId));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 사용자에게 줄 error "시스템장애"
		}
		return post;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public @ResponseBody Post createPost(@PathVariable Integer placeId, @RequestParam String content,
			@RequestParam String dear) throws SQLException {

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
		return postdao.addPost(newPost);
	}
}
