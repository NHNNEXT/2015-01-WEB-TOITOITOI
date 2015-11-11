package cafein.cafe;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNudgeServlet {
	
	@RequestMapping("/nudge")
	public List<Nudge> getNudge(@RequestParam Integer id) {
		return (new NudgeDAO().getNudgeList(id));
	}
}
