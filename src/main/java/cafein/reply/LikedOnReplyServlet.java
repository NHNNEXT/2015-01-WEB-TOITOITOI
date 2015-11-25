package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Result;

@RestController
public class LikedOnReplyServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LikedOnReplyServlet.class);
	private static final long serialVersionUID = 1L;

	@RequestMapping(value="/likedOnReply", method=RequestMethod.POST)
	protected Result doPost(@RequestBody int replyId, @RequestBody String status) throws ServletException, IOException {
		ReplyDAO replydao = new ReplyDAO();

		// plusLike 메소드랑 minusLike 메소드를 따로 만들면 중복이 심할 것 같은데, 이 둘을 하나로 합쳐서
		// updateLike로 만드는 건 어떤지?
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
		//서버와 디비에서 처리된 데이터를 클라이언트로 보내는 방식!
//		ServletOutputStream out = response.getOutputStream();
//		newliked = replydao.getLikedOnReply(replyId);
//		logger.debug("newliked:" + newliked);
//		out.print(newliked);
//		out.close();
	}
}
