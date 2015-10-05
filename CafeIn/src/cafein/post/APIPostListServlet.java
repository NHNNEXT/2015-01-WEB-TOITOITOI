package cafein.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/api/postlist")
public class APIPostListServlet extends HttpServlet{
	private PostDAO postDao = new PostDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int cid = Integer.parseInt(req.getParameter("cid"));
		final Gson gson = new Gson();
		Map<String, Object> model = new HashMap<String, Object>();
		
		ArrayList<Post> posts = postDao.getPosts(cid);
		model.put("posts", posts);
		String jsonData = gson.toJson(model);
		resp.setContentType("application/json;charset=UTF-8");	
		
		PrintWriter out = resp.getWriter();
		out.print(jsonData);
	}
}
