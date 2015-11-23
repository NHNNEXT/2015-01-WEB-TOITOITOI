package cafein.cafe;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNudgeController {
	
	@RequestMapping(value="/nudge/{id}", method=RequestMethod.GET)
	public List<Nudge> getNudge(@PathVariable Integer id) {
		return (new NudgeDAO().getNudgeList(id));
	}
}
