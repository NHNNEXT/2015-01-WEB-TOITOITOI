package cafein.reply;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.post.Post;

@WebServlet("/createReply")
public class CreateReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reply = request.getParameter("reply");
		System.out.println("Re:"+reply);
		Reply re = new Reply(reply);
		ReplyDAO replydao = new ReplyDAO();
		
		try {
			replydao.addReply(re,Integer.parseInt(request.getParameter("pid")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/");
	}

}
