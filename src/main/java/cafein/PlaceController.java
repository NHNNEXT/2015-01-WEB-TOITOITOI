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
	
	/*@RequestMapping("/api/cafelist")
	public List<Place> getCafeList(@RequestParam(value = "lat", required = false) String latitude,
			@RequestParam(value = "long", required = false) String longtitude,
			@RequestParam(value = "sort", required = true, defaultValue = "postNum") String filter) {
		if (Validation.isValidParameter(latitude) && Validation.isValidParameter(longtitude)) {
			//return placeDao.getCafeList(latitude, longtitude); // 위도 경도를 인자로 받는 getCafeList를 아직 안만듦..
			return null;
		} else {
			boolean sortByPostNum = "postNum".equals(filter);
			return placeDao.getPlaceList(sortByPostNum);
		}
	}*/
	
	
}
