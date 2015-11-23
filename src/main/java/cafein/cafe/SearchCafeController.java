package cafein.cafe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchCafeController {
	private static final long serialVersionUID = 1L;
	
	@RequestMapping("/searchcafe")
	protected List<Cafe> searchingCafe(@RequestParam(value="keyword",required=false) String keyword)
			throws ServletException, IOException {
		return (new CafeDAO().searchCafe(keyword));
		
	}

}
