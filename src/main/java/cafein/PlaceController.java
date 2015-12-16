package cafein;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cafein.cafe.Place;
import cafein.cafe.PlaceDAO;
import cafein.util.IllegalAPIPathException;
import cafein.util.Validation;


@Controller
public class PlaceController {  
	
	@Autowired
	private PlaceDAO placeDao;
	
	@RequestMapping(value="/place/{placeId}", method=RequestMethod.GET)
	public ModelAndView viewPlace(@PathVariable Integer placeId) {
		if(!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)){
			throw new IllegalAPIPathException();
		}
		Place place = placeDao.getPlaceById(placeId);
		return new ModelAndView("index").addObject("place", place);
	}
	
}
