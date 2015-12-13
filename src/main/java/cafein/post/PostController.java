package cafein.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController extends HttpServlet {
	@Autowired
	private PostDAO postDao;
	
	@RequestMapping(value="/place/{placeId}/dear/{dearName}/post/{postId}", method=RequestMethod.GET)
	public ModelAndView viewPost(@PathVariable Integer postId) throws SQLException {
		return new ModelAndView("main").addObject("post", postDao.getPostByPostId(postId));
	}	
}
