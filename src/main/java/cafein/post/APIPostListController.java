package cafein.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.IllegalAPIPathException;
import cafein.util.IllegalArgumentLengthException;
import cafein.util.Validation;


@RestController
public class APIPostListController {
	@Autowired
	private PostDAO postdao;
	
	@RequestMapping(value = "/api/place/{placeId}/dear/{dearName}/post", method = RequestMethod.GET)
	public List<Post> getPostList(@PathVariable("placeId")Integer placeId, @PathVariable("dearName")String dear,
			@RequestParam("page")Integer nPage) {
		if(!Validation.isValidParameter(placeId) || Validation.isValidParameterType(placeId)){
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(dear)) {
			throw new IllegalAPIPathException();
		}
		if(!Validation.isValidParameter(nPage) || Validation.isValidParameterType(nPage)){
			throw new IllegalArgumentException();
		}
		
		return (postdao.getPreviews(placeId,dear,nPage));
	}
}
