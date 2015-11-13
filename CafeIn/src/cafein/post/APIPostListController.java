package cafein.post;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIPostListController {

	@RequestMapping("/api/postlist")
	protected List<Post> getPostList(@RequestParam(value="cid",required=false)int cid) {
		
		return (new PostDAO().getPosts(cid));
		
	}
}
