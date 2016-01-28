package cafein;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	//cafe 에시 페이지로 이동.
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "redirect:/3";
	}
	
}
