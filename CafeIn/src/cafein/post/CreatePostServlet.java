package cafein.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.util.Validation;
import com.google.gson.Gson;
import cafein.reply.Reply;

@WebServlet("/createpost")
public class CreatePostServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contents = req.getParameter("contents");
		int cid = Integer.parseInt(req.getParameter("cid"));
		if (!Validation.isValidParameter(contents)) {
			// error message
			resp.sendRedirect("/cafe?cid="+cid);
			return;
		}
		System.out.println("contents : "+contents+", cid : "+cid);
		Post post = new Post(cid, contents);
		PostDAO postdao = new PostDAO();
		
		try {
			Post newPost = postdao.addPost(post);
			String jsonData = postToJson(req, resp, newPost); 
			resp.setContentType("application/json;charset=UTF-8");	
			PrintWriter out = resp.getWriter();
			out.print(jsonData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String postToJson(HttpServletRequest req, HttpServletResponse resp, Post post){
		final Gson gson = new Gson();
		return gson.toJson(post);
	}
}
