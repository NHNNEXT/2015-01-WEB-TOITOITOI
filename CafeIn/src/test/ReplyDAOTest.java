package test;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import cafein.post.Post;
import cafein.reply.Reply;
import cafein.reply.ReplyDAO;

public class ReplyDAOTest {
	private Post TEST_POST;
	private Reply TEST_REPLY;
	private ReplyDAO replyDAO;

	@Before
	public void setup() {
		TEST_POST = new Post(10, "testing post");
		TEST_REPLY = new Reply("This is a test reply.");
		replyDAO = new ReplyDAO();
	}

	@Test
	public void testAddReply() throws SQLException {
		Post testingPost = TEST_POST;
		Reply testingRe = TEST_REPLY;
		assertNotNull(testingPost);
		assertNotNull(testingRe);
		replyDAO.addReply(testingRe, testingPost);

	}

}
