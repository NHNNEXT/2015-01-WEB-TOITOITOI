package cafein.post;

import java.sql.SQLException;

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
	@Autowired
	private PostDAO postDao;
	
	//post/{postId}부터 시작해도 되지 않을까?
	@RequestMapping(value="/place/{placeId}/dear/{dearName}/post/{postId}", method=RequestMethod.GET)
	public String viewPost(@PathVariable Integer placeId, @PathVariable Integer postId, @PathVariable String dearName, Model model) throws SQLException {
		if(!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)){
			throw new IllegalAPIPathException();
		}
		if(!Validation.isValidParameter(postId) || !Validation.isValidParameterType(postId)){
			throw new IllegalAPIPathException();
		}
		model.addAttribute("post", postDao.getPostByPostId(postId));
		model.addAttribute("placeId", placeId);
		model.addAttribute("dearName", dearName);
		return "main";
	}	
}
