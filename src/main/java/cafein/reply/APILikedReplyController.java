package cafein.reply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.IllegalAPIPathException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
public class APILikedReplyController {
	private static final Logger logger = LoggerFactory.getLogger(APILikedReplyController.class);

	@Autowired
	private ReplyDAO replydao;

	@RequestMapping(value = "/api/reply/{replyid}/like", method = RequestMethod.GET)
	protected Result like(@PathVariable int replyid) {
		if (!Validation.isValidParameter(replyid) || !Validation.isValidParameterType(replyid)) {
			throw new IllegalAPIPathException();
		}

		return Result.success(replydao.plusLike(replyid));
	}
}
