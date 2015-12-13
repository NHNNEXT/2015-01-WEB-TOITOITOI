package cafein.post;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cafein.util.Validation;

@Controller

public class CreatePostController {
	private static final Logger logger = LoggerFactory.getLogger(CreatePostController.class);
	@Autowired
	private PostDAO postdao;

	@RequestMapping(value = "/api/place/{placeId}/post", method = RequestMethod.POST)
	public @ResponseBody Post createPost(@PathVariable Integer placeId, @RequestParam String dear, @RequestParam String content) {
		if (!Validation.isValidParameter(content)) {
			// error message
			// return "redirect:/cafe";
		}
		try {
			Post newPost = new Post(dear, content, placeId);
			logger.debug(newPost.toString());
			return postdao.addPost(newPost);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
