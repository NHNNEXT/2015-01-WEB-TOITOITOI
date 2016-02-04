package cafein.post;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cafein.util.IllegalAPIPathException;
import cafein.util.Validation;

@Controller
public class PostController {
private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	@Autowired
	private PostDAO postDao;
	
	//post/{postId}부터 시작해도 되지 않을까?
	@RequestMapping(value="/place/{placeId}/dear/{dearName}/post/{postId}", method=RequestMethod.GET)
	public String viewPost(@PathVariable Integer postId, @PathVariable String dearName, Model model) throws SQLException {
		if(!Validation.isValidParameter(postId) || !Validation.isValidParameterType(postId)){
			throw new IllegalAPIPathException();
		}
		Post post = postDao.getPostByPostId(postId);
		String[] createdtime = post.getCreatedtime().split("\\s");
		String createdDate = createdtime[0];
		logger.debug(createdDate);
		model.addAttribute("post", post);
		model.addAttribute("createdDate", createdDate);
		model.addAttribute("dearName", dearName);
		return "main";
	}	
}
