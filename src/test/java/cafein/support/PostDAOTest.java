package cafein.support;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cafein.post.Post;
import cafein.post.PostDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class PostDAOTest {
	private static final Logger logger = LoggerFactory.getLogger(PostDAOTest.class);
	@Autowired
	private PostDAO postdao;
	
	@Test
	public void getPostlist() {
		List<Post> postlist = postdao.getPosts(1,"뷰티토이", 1);
		logger.debug(postlist.toString());
	}

}
