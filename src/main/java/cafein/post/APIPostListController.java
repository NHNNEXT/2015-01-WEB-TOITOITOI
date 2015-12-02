package cafein.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIPostListController {
	@Autowired
	private PostDAO postdao;
	
	@RequestMapping("/api/postlist")
	public List<Post> getPostList(@RequestParam(value="cid",required=false)int cid) {
		return (postdao.getPosts(cid));
		
	}
}
