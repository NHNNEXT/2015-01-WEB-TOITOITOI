package cafein.post;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/likedOnReply")
public class LikedOnPostServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LikedOnPostServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostDAO postdao = new PostDAO();

		int pid = Integer.parseInt(request.getParameter("pid"));
		String status = request.getParameter("status");

		// plusLike 메소드랑 minusLike 메소드를 따로 만들면 중복이 심할 것 같은데, 이 둘을 하나로 합쳐서
		// updateLike로 만드는 건 어떤지?
		try {
			if (status.equals("plus")) {
				postdao.plusLike(pid);
			} else {
				postdao.minusLike(pid);
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//서버와 디비에서 처리된 데이터를 클라이언트로 보내는 방식!
//		ServletOutputStream out = response.getOutputStream();
//		newliked = replydao.getLikedOnReply(replyId);
//		logger.debug("newliked:" + newliked);
//		out.print(newliked);
//		out.close();
	}
}
