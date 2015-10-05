package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cafein.post.Post;
import cafein.util.Validation;

@WebServlet("/createReply")
public class CreateReplyServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(CreateReplyServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reply = request.getParameter("reply");
		if (!Validation.isValidParameter(reply)) {
			// error message
			response.sendRedirect("/cafe?cid=1");
			return;
		}
		logger.debug("Re:" + reply);
		Reply re = new Reply(reply);
		ReplyDAO replydao = new ReplyDAO();
		try {
			replydao.addReply(re, Integer.parseInt(request.getParameter("pid")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/cafe?cid=1");
	}
}
