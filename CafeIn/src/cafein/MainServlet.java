package cafein;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.cafe.CafeDAO;
import cafein.post.Post;

@WebServlet({"/cafe"})
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		CafeDAO cafedao = new CafeDAO();
		ArrayList<Post> posts = cafedao.getPosts(cid);
		request.setAttribute("posts", posts);
		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
	}

}
