package cafein.cafe;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNudgeServlet {
	@RequestMapping("/nudge")
	public List<Nudge> getNudge(Integer cid) {
		return (new NudgeDAO()).getNudgeList(cid);
	}
}
