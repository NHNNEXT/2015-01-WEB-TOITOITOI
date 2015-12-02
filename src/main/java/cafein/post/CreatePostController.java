package cafein.post;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value ="/createpost", method = RequestMethod.POST)
	public @ResponseBody Post createPost(@RequestParam(value="cid")int cid, @RequestParam(value="contents",required = false)String contents) {
		if (!Validation.isValidParameter(contents)) {
			// error message
			//return "redirect:/cafe";
		}
		try {
			Post post = new Post(cid, contents);
			  return postdao.addPost(post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
