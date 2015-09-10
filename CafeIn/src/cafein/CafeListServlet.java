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
		CafeDAO cafedao = new CafeDAO();
		ArrayList<Cafe> cafeList = cafedao.getCafeList();
		
		request.setAttribute("cafeList", cafeList);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}


}
