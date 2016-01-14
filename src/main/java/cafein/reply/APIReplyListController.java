package cafein.reply;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.repository.ReplyRepository;
import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
public class APIReplyListController {
	private static final Logger logger = LoggerFactory.getLogger(APIReplyListController.class);

	@Autowired
	private ReplyDAO replydao;
	@Autowired
	private ReplyRepository replyDao;

	@RequestMapping(value = "/api/place/{placeId}/dear/{dearId}/post/{postId}/reply", method = RequestMethod.GET)
	public List<Reply> getReplyList(@PathVariable ObjectId postId) {
		logger.debug(postId + "");
		return replyDao.getReplys(postId);
	}

	@RequestMapping(value = "/api/place/{placeId}/dear/{dearId}/post/{postId}/reply", method = RequestMethod.POST)
	protected Result createReply(@PathVariable ObjectId postId,
			@RequestParam(required = false) String content) {
		if (!Validation.isValidParameter(content)) {
			throw new IllegalArgumentException();
		}
		if (!Validation.isValidMaxLenReply(content)) {
			throw new IllegalArgumentLengthException();
		}

		Reply reply = new Reply(postId.toString(), content);
		logger.debug("Reply:pid,content:" + reply.getPostId() + reply.getContent());
		return Result.success(replydao.addReply(reply));
	}

}
