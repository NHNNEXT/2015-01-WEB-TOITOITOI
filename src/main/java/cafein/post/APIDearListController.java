package cafein.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.IllegalAPIPathException;
import cafein.util.Validation;

@RestController
public class APIDearListController {
	private static final Logger logger = LoggerFactory.getLogger(APIDearListController.class);

	@Autowired
	private PostDAO postdao;

	@RequestMapping(value = "/api/place/{placeId}/dear", method = RequestMethod.GET)
	public Map<String, List<Post>> getDearList(@PathVariable("placeId") Integer placeId,
			@RequestParam("page") Integer nPage) {

		if (!Validation.isValidParameter(placeId) || !Validation.isValidParameterType(placeId)) {
			throw new IllegalAPIPathException();
		}
		if (!Validation.isValidParameter(nPage) || !Validation.isValidParameterType(nPage)) {
			throw new IllegalArgumentException();
		}

		Map<String, List<Post>> dearsWithPosts = new HashMap<String, List<Post>>();

		dearsWithPosts = postdao.getDearsWithPreviews(placeId, nPage);
		logger.debug(dearsWithPosts.toString());

		return dearsWithPosts;
	}
}
