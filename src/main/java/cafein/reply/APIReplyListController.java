package cafein.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIReplyListController {
	@Autowired
	private ReplyDAO replydao;
	
	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post/{postId}/reply", method = RequestMethod.GET)
	public List<Reply> getReplyList(@PathVariable int postId) {
		return replydao.getReplys(postId);
	}
}

