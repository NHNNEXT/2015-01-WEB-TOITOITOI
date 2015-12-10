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
		Reply testreply = new Reply(5, "newREE", null, 0);
		testreply.setPid(2);
		Reply result = replydao.addReply(testreply);
		logger.debug(result.toString());
	}
	
	@Test 
	public void getPost() throws SQLException {
		ArrayList<Post> renew  = postdao.getPosts(1);
		System.out.println(renew.toString());
	}
	
	
	
//	@Test
//	public void findByPid() {
//		Post post = cafeindao.findByPid();
//		logger.debug("Post: {}", post);
//	}
//	
//	@Test
//	public void addPost() throws Exception {
//		Post post = new Post(2,"spring카페,포스트");
//		cafeindao.addPost(post);
//		logger.debug(post.toString());
//	}

}
