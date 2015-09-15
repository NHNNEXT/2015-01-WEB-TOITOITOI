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


/**
 * Servlet implementation class CafeListServlet
 */

@WebServlet({""})
public class CafeListServlet extends HttpServlet {  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter = request.getParameter("sort");
		ArrayList<Cafe> cafeList;
		CafeDAO cafedao = new CafeDAO();
		
		// http://stackoverflow.com/questions/3321526/should-i-use-string-isempty-or-equalsstring
		boolean sortByPostNum = "postNum".equals(filter);
		cafeList = cafedao.getCafeList(sortByPostNum);
		
		request.setAttribute("cafeList", cafeList);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}


}
