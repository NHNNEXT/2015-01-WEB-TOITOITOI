package cafein.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyhomeController {
	private static final Logger logger = LoggerFactory.getLogger(MyhomeController.class);

	
	@RequestMapping("/main")
	public String index2(){
		//home은 보여줄 view의 이름입니다.view이름이 home-servlet 의 prefix, suffix랑 결합되서 view를 찾습니다.
		return "main";
	}

	 
}
