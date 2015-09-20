package cafein.cafe;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet({"/nudge"})
public class GetNudgeServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Nudge> nudgeList = (new NudgeDAO()).getNudgeList(Integer.parseInt(request.getParameter("cid")));
		String nudgeJson = (new Gson()).toJson(nudgeList);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(nudgeJson);
	}
}
