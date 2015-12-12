package cafein.support;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cafein.post.Post;
import cafein.post.PostDAO;
import cafein.reply.Reply;
import cafein.reply.ReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)@ContextConfiguration("classpath:/applicationContext.xml")
public class CafeinDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(CafeinDaoTest.class);
	@Autowired
	private PostDAO postdao;
	
	@Autowired
	private ReplyDAO replydao;
	
	@Test
	public void addReply() throws SQLException {
		Reply result = null;
		Reply testreply = new Reply(1, "토이토이가 뭡니까?");
		result = replydao.addReply(testreply);
		logger.debug(result.toString());
	}

	@Test
	public void getPostById() throws SQLException {
		int postId = 2;
		Post test = postdao.getPostByPostId(postId);
		logger.debug(test.toString());
	}
	
	@Test
	public void getReplys() {
		int postId = 2;
		logger.debug(replydao.getReplys(postId).toString());
	}
}
