package cafein;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;
import cafein.util.Validation;

@RestController
public class APICafeListController {

	@RequestMapping("/api/cafelist")
	protected List<Cafe> getCafeList(@RequestParam(value="lat", required = false) String latitude, @RequestParam(value="long",required = false) String longtitude, @RequestParam(value="sort",required = true,defaultValue="postNum") String filter){
		
		CafeDAO cafedao = new CafeDAO();

		if (Validation.isValidParameter(latitude) && Validation.isValidParameter(longtitude)) {
			return cafedao.getCafeList(latitude, longtitude);
		} else {
			boolean sortByPostNum = "postNum".equals(filter);
			return cafedao.getCafeList(sortByPostNum);
		}

	}
}
