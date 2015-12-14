package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Validation;

@RestController
public class APIReplyListController {
	private static final Logger logger = LoggerFactory.getLogger(APIReplyListController.class);
	
	@Autowired
	private ReplyDAO replydao;
	
	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post/{postId}/reply", method = RequestMethod.GET)
	public List<Reply> getReplyList(@PathVariable int postId) {
		logger.debug(postId+"");
		return replydao.getReplys(postId);
	}

	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post/{postId}/reply", method = RequestMethod.POST)
	protected @ResponseBody Reply createReply(@PathVariable int postId, @RequestParam(required = false)String content) throws IOException {

		if(!Validation.isValidParameter(postId) || !Validation.isValidParameterType(postId)){
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(content)) {
			throw new IllegalArgumentException();
		}
		if(!Validation.isValidMaxLenReply(content)){
			throw new IllegalArgumentLengthException();
		}

		try {
			Reply reply = new Reply(postId, content);
			logger.debug("Reply:pid,content:"+reply.getPostId()+reply.getContent());
			return (replydao.addReply(reply));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	// Reply를 JSON으로 반환하는 서블릿에서 SQLException은 어떻게 처리해야하나?
		}
	}

}

