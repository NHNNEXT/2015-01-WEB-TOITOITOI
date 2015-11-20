package cafein.reply;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIReplyListController extends HttpServlet{

	@RequestMapping(value ="/api/replylist", method = RequestMethod.POST)
	public List<Reply> getReplyList(@RequestParam(value = "pid",required = false)int pid){
			return new ReplyDAO().getReplys(pid);
		
	}
}
