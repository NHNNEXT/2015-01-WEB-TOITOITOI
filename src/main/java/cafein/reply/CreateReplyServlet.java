package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Validation;

@RestController
public class CreateReplyServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(CreateReplyServlet.class);
	private static final long serialVersionUID = 1L;
	
	//spring form tag를 적용할 수가 없다. js코드에서 render method로 html을 생성해내고 있기 때문.
	@RequestMapping(value="/createReply", method=RequestMethod.POST)
	protected Reply createReply(@RequestBody Reply reply) throws ServletException, IOException {
		if (!Validation.isValidParameter (reply.getReplyContent())) {
			//resp.sendRedirect("/cafe?cid=" + cid);
			//error상황일 때 어떻게 보여줄지. 클라이언트에 json으로 error메시지를 넘긴다.
			//json으로 에러상황을 알릴때 어떻게 처리?
			throw new IllegalArgumentException();
			// @ControllerAdivce
			// return "redirect:/cafe";   
			 // Reply를 JSON으로 반환하는 서블릿에서 리다이렉르트 하려면 어떻게 해야하나?
		}
		logger.debug("Reply:" + reply.getReplyContent());
		try {
			return (new ReplyDAO().addReply(reply));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	// Reply를 JSON으로 반환하는 서블릿에서 SQLException은 어떻게 처리해야하나?
		}
	}
}
