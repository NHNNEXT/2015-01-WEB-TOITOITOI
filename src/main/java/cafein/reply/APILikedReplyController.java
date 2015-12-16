package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Result;

@RestController
public class APILikedReplyController extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(APILikedReplyController.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private ReplyDAO replydao;

	@RequestMapping(value="/likedOnReply", method=RequestMethod.POST)
	protected @ResponseBody Result doPost(@RequestParam(value="reid") int replyId, @RequestParam String status) throws ServletException, IOException {

		try {
			if (status.equals("plus")) {
				replydao.plusLike(replyId);
			} else {
				replydao.minusLike(replyId);
			}
			return Result.success();
		} catch (SQLException e) {
			return Result.failed(e.getMessage());
		}
	}
}
