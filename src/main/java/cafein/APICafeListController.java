package cafein;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.cafe.Place;
import cafein.cafe.PlaceDAO;
import cafein.util.Validation;

@RestController
public class APICafeListController {
	
	@Autowired
	private PlaceDAO cafedao;

	/*@RequestMapping("/api/cafelist")
	public List<Place> getCafeList(@RequestParam(value = "lat", required = false) String latitude,
			@RequestParam(value = "long", required = false) String longtitude,
			@RequestParam(value = "sort", required = true, defaultValue = "postNum") String filter) {

		if (Validation.isValidParameter(latitude) && Validation.isValidParameter(longtitude)) {
			return cafedao.getCafeList(latitude, longtitude);
		} else {
			boolean sortByPostNum = "postNum".equals(filter);
			return cafedao.getCafeList(sortByPostNum);
		}

	}
*/
}
