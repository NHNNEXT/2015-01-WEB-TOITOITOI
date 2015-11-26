package cafein.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cafein.util.Validation;

@RestController
public class CreatePostController {

	@RequestMapping(value = "/createpost", method = RequestMethod.POST)
	protected Post createPost(@RequestBody String content, @RequestBody int cid) {
		if (!Validation.isValidParameter(content)) {
			// error message
			//return "redirect:/cafe";
		}

		try {
			return new PostDAO().addPost(cid, content);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
