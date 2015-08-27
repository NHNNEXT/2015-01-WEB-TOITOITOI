package cafein;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.post.Post;
import cafein.post.PostDAO;
import cafein.reply.ReplyDAO;

@WebServlet({"/cafe"})
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		PostDAO postdao = new PostDAO();
		ReplyDAO replydao = new ReplyDAO();
		ArrayList<Post> posts = postdao.getPosts(cid);
		for (Post post : posts) {
			int pid = post.getPid();
			post.setReplyList(replydao.getReplys(pid));
		}
		
		request.setAttribute("posts", posts);
		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
	}

}
