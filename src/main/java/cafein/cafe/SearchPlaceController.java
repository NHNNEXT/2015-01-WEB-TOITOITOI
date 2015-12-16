package cafein.cafe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//spring from tag적용 대상이지만 카페검색 기능은 없을 것이니 적용않겠음.
@RestController
public class SearchPlaceController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private PlaceDAO cafedao;
	
	/*@RequestMapping("/searchcafe")
	protected List<Place> searchingCafe(@RequestParam(value="keyword",required=false) String keyword)
			throws ServletException, IOException {
		return (cafedao.searchPlace(keyword));
		
	}*/

}
