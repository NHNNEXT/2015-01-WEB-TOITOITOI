package cafein.reply;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import cafein.post.Post;
import cafein.util.Validation;

@WebServlet("/createReply")
public class CreateReplyServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(CreateReplyServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("content");
		String cid = req.getParameter("cid");
		if (!Validation.isValidParameter(content)) {
			resp.sendRedirect("/cafe?cid="+cid);		//프론트에서도 에러메시지 출력해줘야함.
			return;
		}
		logger.debug("Re:" + content);
		Reply reply = new Reply(content);
		ReplyDAO replydao = new ReplyDAO();
		try {
			Reply newReply = replydao.addReply(reply, Integer.parseInt(req.getParameter("pid")));
			String jsonData = replyToJson(req, resp, newReply); 
			resp.setContentType("application/json;charset=UTF-8");	
			PrintWriter out = resp.getWriter();
			out.print(jsonData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String replyToJson(HttpServletRequest req, HttpServletResponse resp, Reply reply){
		final Gson gson = new Gson();
		return gson.toJson(reply);
	}
}
