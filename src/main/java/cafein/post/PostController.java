package cafein.post;

import java.sql.SQLException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cafein.repository.PostRepository;

@Controller
public class PostController {
	@Autowired
	private PostRepository postDao;

	//post/{postId}부터 시작해도 되지 않을까?
	@RequestMapping(value="/place/{placeUri}/dear/{dearUri}/post/{postId}", method=RequestMethod.GET)
	public String viewPost(@PathVariable String placeUri, @PathVariable String dearUri, @PathVariable ObjectId postId, Model model) throws SQLException {
		model.addAttribute("post", postDao.findOne(postId));
		model.addAttribute("placeId", placeUri);
		model.addAttribute("dearName", dearUri);
		return "main";
	}	
}
