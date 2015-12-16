package cafein.post;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cafein.util.Result;

@RestController
public class APILikedPostController {
	@Autowired 
	PostDAO postdao;

	@RequestMapping(value = "/likedOnPost", method = RequestMethod.POST)
	protected @ResponseBody Result liked(@RequestParam int pid, @RequestParam String status) {

		// plusLike 메소드랑 minusLike 메소드를 따로 만들면 중복이 심할 것 같은데, 이 둘을 하나로 합쳐서
		// updateLike로 만드는 건 어떤지?
		try {
			if (status.equals("plus")) {
				postdao.plusLike(pid);
			} else {
				postdao.minusLike(pid);
			}
			return Result.success();
		} catch (SQLException e) {
			return Result.failed(e.getMessage());
		}
	}
}
