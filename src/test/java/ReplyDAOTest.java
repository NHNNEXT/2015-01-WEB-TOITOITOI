

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import cafein.post.Post;
import cafein.reply.Reply;
import cafein.reply.ReplyDAO;

public class ReplyDAOTest {
	private Reply TEST_REPLY;
	private ReplyDAO replyDAO;

	@Before
	public void setup() {
		TEST_REPLY = new Reply("testing reply");
		replyDAO = new ReplyDAO();
	}

	@Test
	public void testAddReply() throws SQLException {
		Reply testingRe = TEST_REPLY;
		replyDAO.addReply(testingRe, 2);
	}
	//client에서 정상적으로 newlike갯수가 넘어왔을 때, sql에 liked +1 test
	@Test
	public void testlikeOnReply() throws SQLException {
		Reply testingRe = TEST_REPLY;
		testingRe.setReId(1);
		assertNotNull(testingRe);
		replyDAO.plusLike(testingRe.getReId());
	}
 }
