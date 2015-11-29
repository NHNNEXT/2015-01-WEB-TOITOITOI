package cafein.cafe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNudgeController {
	@Autowired
	private NudgeDAO nudgedao;
	
	@RequestMapping(value="/nudge/{id}", method=RequestMethod.GET)
	public List<Nudge> getNudge(@PathVariable Integer id) {
		return (nudgedao.getNudgeList(id));
	}
}
