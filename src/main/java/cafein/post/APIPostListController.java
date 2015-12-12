package cafein.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIPostListController {
	@Autowired
	private PostDAO postdao;
	
	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post", method = RequestMethod.GET)
	public List<Post> getPostList(@PathVariable("placeId")int placeId, @PathVariable("dearName")String dear,
			@RequestParam("page")int nPage) {
		return (postdao.getPosts (placeId,dear,nPage));
	}
}
