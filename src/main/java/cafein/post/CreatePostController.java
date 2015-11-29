package cafein.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cafein.util.Validation;

@RestController("/createpost")
public class CreatePostController {

	@Autowired
	private PostDAO postdao;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Post createPost(@ModelAttribute("newpost") Post post, Model model) {
		if (!Validation.isValidParameter(post.getContents())) {
			// error message
			// return "redirect:/cafe";
		}

		try {
			model.addAttribute("newpost", new Post());
			return postdao.addPost(post);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
