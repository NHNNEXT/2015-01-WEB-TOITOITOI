package cafein.post;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cafein.util.Validation;

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
			postdao.addPost(post);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.sendRedirect("/cafe?cid="+cid);
	}
}
