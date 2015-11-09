package cafein;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CafeListController {  
	@RequestMapping("/")
	public String home() {
		return "index";
	}
}
