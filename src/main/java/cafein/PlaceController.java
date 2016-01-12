package cafein;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cafein.cafe.CandidateDAO;
import cafein.cafe.CandidateDear;
import cafein.cafe.Place;
import cafein.cafe.PlaceDAO;
import cafein.util.IllegalAPIPathException;
import cafein.util.Validation;

@Controller
public class PlaceController {
	private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
	@Autowired
	private PlaceDAO placeDao;
	@Autowired
	private CandidateDAO candidatedao;
	
	@RequestMapping(value = "/place/{placeId}", method = RequestMethod.GET)
	public String viewPlace(@PathVariable Integer placeId, Model model) {
		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		Place place = placeDao.getPlaceById(placeId);
		List<CandidateDear> candidates = candidatedao.getRecommendedDears(placeId);
		//CandidateDear test = candidates.get(0);
		model.addAttribute("place", place);
		model.addAttribute("candidates",candidates);
		if (place != null) {
			logger.debug(place.toString());
			return "index";
		}
		return"placeidException";

	}

}
