package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/likedOnReply")
public class LikedOnReplyServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LikedOnReplyServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int replyId = Integer.parseInt(request.getParameter("reid"));
		ReplyDAO replydao = new ReplyDAO();
		int newliked;
		logger.debug("replyId:"+replyId);
		//replydId는 정상적으로 잡음.
		try {
			replydao.likedOnReply(replyId);
			response.setStatus(HttpServletResponse.SC_OK);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		//문제 코드 
		newliked = replydao.getLikedOnReply(replyId);
		logger.debug("newliked:"+newliked);
		//newliked값이 아니라 replyId값이 넘어옴. 
		out.print(newliked);
		out.close();
	}
}
