package cafein;

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

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;
import cafein.reply.LikedOnReplyServlet;

@WebServlet("/api/cafelist")
public class APICafeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LikedOnReplyServlet.class);   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Cafe> cafeList;
		CafeDAO cafedao = new CafeDAO();
		cafeList = cafedao.getCafeList();
		
		response.getWriter().write(new Gson().toJson(cafeList));
		logger.debug(new Gson().toJson(cafeList));
	}



}
