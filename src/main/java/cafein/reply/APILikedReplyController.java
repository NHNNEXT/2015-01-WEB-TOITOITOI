package cafein.reply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Result;

@RestController
public class APILikedReplyController {
	private static final Logger logger = LoggerFactory.getLogger(APILikedReplyController.class);

	@Autowired
	private ReplyDAO replydao;

	@RequestMapping(value = "/likedOnReply", method = RequestMethod.GET)
	protected Result like(@RequestParam(value = "reid") int replyId, @RequestParam String status) {

		replydao.plusLike(replyId);
		return Result.success();
	}
}
