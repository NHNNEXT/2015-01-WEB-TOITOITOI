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

	@RequestMapping(value="/createReply", method=RequestMethod.POST)
	protected Reply doPost(@RequestBody String content, @RequestBody int cid, @RequestBody int pid, HttpServletResponse resp) throws ServletException, IOException {
		if (!Validation.isValidParameter(content)) {
			resp.sendRedirect("/cafe?cid=" + cid);
			// return "redirect:" + "/cafe?cid=" + cid;   // Reply를 JSON으로 반환하는 서블릿에서 리다이렉르트 하려면 어떻게 해야하나?
		}
		logger.debug("Re:" + content);
		Reply newReply = new Reply(content);
		try {
			return (new ReplyDAO().addReply(newReply, pid));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	// Reply를 JSON으로 반환하는 서블릿에서 SQLException은 어떻게 처리해야하나?
		}
	}
}
