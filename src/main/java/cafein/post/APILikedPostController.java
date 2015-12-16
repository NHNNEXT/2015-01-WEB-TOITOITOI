package cafein.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Result;

@RestController
public class APILikedPostController {
	@Autowired 
	PostDAO postdao;

	@RequestMapping(value = "/likedOnPost", method = RequestMethod.POST)
	protected Result liked(@RequestParam int pid, @RequestParam String status) {

			if (status.equals("plus")) {
				postdao.plusLike(pid);
			} 
			return Result.success();
	}
}
