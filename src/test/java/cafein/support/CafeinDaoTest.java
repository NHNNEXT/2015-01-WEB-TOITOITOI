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
	
//	@Test
//	public void addReply() throws SQLException {
//		Reply result = null;
//		Reply testreply = new Reply(1, "우리는회의를해!");
//		result = replydao.addReply(testreply);
//		logger.debug(result.toString());
//	}

	@Test
	public void getReplys() {
		Integer postId = 1;
		logger.debug(replydao.getReplys(postId).toString());

	}
	@Test
	public void getReplyById() {
		int id = 1;
		Reply test = replydao.getReplyJustInserted(id);
		logger.debug(test.toString());
		logger.debug("id"+test.getId());
	}
//	@Test 
//	public void liked() {
//		replydao.plusLike(1);
//		logger.debug(replydao.getReplyJustInserted(1).toString());
//	}
	//PostDAO test
	
//	@Test
//	public void addPost() {
//		Post test;
//		logger.debug(postdao.addPost(new Post("toitoi","shine",1)).toString());
//	}
	
	@Test
	public void getPostbyId() {
		Post test;
		Integer pid = 1;
		test = postdao.getPostByPostId(pid);
		logger.debug("ID:"+test.toString());
	}
	
	@Test
	public void getDearList(){
		logger.debug(postdao.getDearList(1, 1).toString());
	}
	
	@Test
	public void getPrivews() {
		logger.debug(postdao.getPreviews(1, 1, 1).toString());
	}
//	@Test
//	public void hasDear() {
//		logger.debug(postdao.getDearId("김기범").toString());
//	}
//	@Test
//	public void addDear() {
//		logger.debug(postdao.addDear("김기범").toString());
//	}
}
