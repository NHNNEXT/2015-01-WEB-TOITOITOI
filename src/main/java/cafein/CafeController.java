package cafein;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CafeController extends HttpServlet {
	
	@RequestMapping("/cafe")
	protected void getCafeView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// Map<String,Object> model = new HashMap<String,Object>();
		// model.put("message", message);
		//
		// View view = news InternalResourceView("/WEB-INF/view/main.jsp");
		//
		// return new ModelAndView(view,model);
		// cid에 맞는 카페개별 페이지를 보여줘야 하는데, 
		// 아직 springd에서 view class를 만들지 않아서 일반적인 방식을 쓸수없었다.
		
		req.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, res);
	}

}
