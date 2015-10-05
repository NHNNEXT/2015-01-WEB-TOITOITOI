package cafein.cafe;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@WebServlet("/searchcafe")
public class SearchCafeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SearchCafeServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String keyword = request.getParameter("keyword");
		CafeDAO cafedao = new CafeDAO();
		logger.debug("keyword:"+keyword);
		ArrayList<Cafe> cafeList = cafedao.searchCafe(keyword);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(cafeList));
		//logger.debug(new Gson().toJson(cafeList));
	}

}
