package cafein.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cafein.reply.ReplyDAO;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Validation;

@RestController
public class APIPostController extends HttpServlet {
	@Autowired
	private ReplyDAO replydao;
	@Autowired
	private PostDAO postdao;

	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post/{postId}", method = RequestMethod.GET)
	public Post viewPost(@PathVariable Integer postId) {
		Post post = null;
		
		if(!Validation.isValidParameter(postId) || Validation.isValidParameterType(postId)){
			throw new IllegalAPIPathException();
		}
		try {
			post = postdao.getPostByPostId(postId);
			post.setReplyList(replydao.getReplys(postId));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}
}
