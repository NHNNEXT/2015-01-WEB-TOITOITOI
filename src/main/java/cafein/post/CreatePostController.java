package cafein.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cafein.util.Validation;

@RestController
public class CreatePostController {

//	@RequestMapping(value = "/createpost", method = RequestMethod.POST)
//	protected Post createPost(Post post) {
//		if (!Validation.isValidParameter(post.getContents())) {
//			// error message
//			//return "redirect:/cafe";
//		}
//
//		try {
//			return new PostDAO().addPost(post.getCid(), post.getContents());
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	@RequestMapping(value = "/createpost", method = RequestMethod.POST)
	protected Post createPost(Post post) {
		if (!Validation.isValidParameter(post.getContents())) {
			// error message
			//return "redirect:/cafe";
		}

		try {
			return new PostDAO().addPost(post);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
