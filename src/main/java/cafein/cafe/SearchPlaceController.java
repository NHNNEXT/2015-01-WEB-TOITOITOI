package cafein.cafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//spring from tag적용 대상이지만 카페검색 기능은 없을 것이니 적용않겠음.
@RestController
public class SearchPlaceController {
	@Autowired
	private PlaceDAO cafedao;
	
	/*@RequestMapping("/searchcafe")
	protected List<Place> searchingCafe(@RequestParam(value="keyword",required=false) String keyword)
			throws ServletException, IOException {
		return (cafedao.searchPlace(keyword));
		
	}*/

}
