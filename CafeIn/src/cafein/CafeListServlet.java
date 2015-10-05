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


/**
 * Servlet implementation class CafeListServlet
 */

@WebServlet({""})
public class CafeListServlet extends HttpServlet {  
	private static final Logger logger = LoggerFactory.getLogger(LikedOnReplyServlet.class);
	
	// Util 빼면 좋을텐데.
	public boolean isValid(String parameter) {
		// {latitude, "".equals(latitude) , latitude == null, latitude.isEmpty()}
		// "?lat=" {, true, false, true}
		// "?" {null, false, true, ERROR}
		// "?lat=37.51" {37.51, false, false, false}
		return (parameter!=null) && !(parameter.isEmpty());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter = request.getParameter("sort");
		String latitude = request.getParameter("lat");
		String longitude = request.getParameter("long");
		
		ArrayList<Cafe> cafeList = null;
		CafeDAO cafedao = new CafeDAO();
		
		if (isValid(latitude) && isValid(longitude)) {
			System.out.println("오지마");
			cafeList = cafedao.getCafeList(latitude, longitude);
		} else {
			// http://stackoverflow.com/questions/3321526/should-i-use-string-isempty-or-equalsstring
			boolean sortByPostNum = "postNum".equals(filter);
			cafeList = cafedao.getCafeList(sortByPostNum);			
		}
		
		//request.setAttribute("cafeList", cafeList);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		
		
	}


}
