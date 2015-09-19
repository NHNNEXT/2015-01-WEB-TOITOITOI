package cafein;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;
import cafein.post.Post;
import cafein.post.PostDAO;
import cafein.reply.ReplyDAO;

@WebServlet({"/cafe"})
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CafeDAO cafedao = new CafeDAO();
		PostDAO postdao = new PostDAO();
		ReplyDAO replydao = new ReplyDAO();
		
		
		String cafeName = cafedao.getName(cid);
		ArrayList<Post> posts = postdao.getPosts(cid);
		for (Post post : posts) {
			int pid = post.getPid();
			post.setReplyList(replydao.getReplys(pid));
		}
		request.setAttribute("cafeName",cafeName);
		request.setAttribute("posts", posts);
		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
	}

}
