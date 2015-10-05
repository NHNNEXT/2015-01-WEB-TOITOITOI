package cafein.reply;

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

import cafein.post.Post;
import cafein.post.PostDAO;


@WebServlet("/api/replylist")
public class APIReplyListServlet extends HttpServlet{
	private ReplyDAO replyDao = new ReplyDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pid = Integer.parseInt(req.getParameter("pid"));
		final Gson gson = new Gson();
		Map<String, Object> model = new HashMap<String, Object>();
		
		ArrayList<Reply> replies = replyDao.getReplys(pid);
		model.put("replies", replies);
		String jsonData = gson.toJson(model);
		resp.setContentType("application/json;charset=UTF-8");	
		
		PrintWriter out = resp.getWriter();
		out.print(jsonData);
	}
}
