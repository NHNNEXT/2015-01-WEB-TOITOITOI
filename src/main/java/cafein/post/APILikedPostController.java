package cafein.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.IllegalAPIPathException;
import cafein.util.Result;
import cafein.util.Validation;

@RestController
public class APILikedPostController {
	@Autowired
	PostDAO postdao;

	@RequestMapping(value = "/api/post/{postid}/like", method = RequestMethod.POST)
	protected Result liked(@PathVariable int postid) {

		if (!Validation.isValidParameter(postid) || !Validation.isValidParameterType(postid)) {
			throw new IllegalAPIPathException();
		}

		postdao.plusLike(postid);
		return Result.success();
	}
}
