package cafein.support;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import cafein.cafe.CandidateDAO;
import cafein.cafe.Place;
import cafein.cafe.PlaceDAO;
import cafein.file.FileDAO;
import cafein.file.ImageFile;
import cafein.post.Post;
import cafein.post.PostDAO;
import cafein.reply.Reply;
import cafein.reply.ReplyDAO;
import cafein.util.Validation;

@RunWith(SpringJUnit4ClassRunner.class)@ContextConfiguration("classpath:/applicationContext.xml")
public class CafeinDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(CafeinDaoTest.class);
	@Autowired
	private PostDAO postdao;
	
	@Autowired
	private ReplyDAO replydao;
	
	@Autowired
	private PlaceDAO placedao;
	
	@Autowired
	private CandidateDAO candidatedao;
	
	@Autowired
	private FileDAO filedao;
	
	@Test
	public void isVaild(){
		Boolean test = true;
		test = Validation.isValidParameter(" ");
		logger.debug(test.toString());
	}
	
	@Test
	public void getPlaceById() {
		placedao.getPlaceById(1);	
		
	}
	@Test
	public void addReply() throws SQLException {
		Reply result = null;
		Reply testreply = new Reply(1, "우리는회의를해!");
		result = replydao.addReply(testreply);
		logger.debug(result.toString());
	}

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
	@Test 
	public void liked() {
		replydao.plusLike(1);
		logger.debug(replydao.getReplyJustInserted(1).toString());
	}
	
	@Test
	public void addPost() {
		Post test;
		logger.debug(postdao.addPost(new Post("toitoi","shine",1)).toString());
	}
	
	@Test
	public void getPostbyId() {
		Post test;
		Integer pid = 1;
		test = postdao.getPostByPostId(pid);
		logger.debug("ID:"+test.toString());
	}
	
	@Test
	public void getDearList(){
		List<Map<String,Object>> test;
		test = postdao.getDearList(1, 4);
		if(test.isEmpty()){
	 	logger.debug(test.toString());
	 	logger.debug("test");
		}
	}
	
	@Test
	public void getRecommend() {
		Integer test = 1;
		logger.debug(candidatedao.getRecommendedDears(test).toString());
	}
	
	@Test
	public void plusLike() {
		Integer postid = 3;
		Post before = postdao.getPostByPostId(postid);
		int beforePlus = before.getLikes();
		
		postdao.plusLike(postid);
		Post after = postdao.getPostByPostId(postid);
		int afterPlus = after.getLikes();
		
		assertEquals(new Integer(beforePlus+1), new Integer(afterPlus));
	}
	
	@Test
	public void addFileInfo() {
		String original_filename ="테스.jpg";
		String stored_filename = "UDDETYUHJIOKRFTGUDDETYUHJIOKR";
		String test;
		ImageFile imageFile = new ImageFile(original_filename, stored_filename);
		logger.debug(imageFile.getOriginal_filename());
		logger.debug(imageFile.getStored_filename());
		test =filedao.addFileInfo(imageFile);
		logger.debug(test);
	}
	
	@Test
	public void updatePostId(){
		String name = "UDDETYUHJIOKRFTGUDDETYUHJIOKR";
		Integer postid = 1;
		filedao.updatePostId(postid, name);
		
	}
	@Test
	public void getPrivews() {
		logger.debug(postdao.getPreviews(1, 1, 1).toString());
	}
	@Test
	public void hasDear() {
		logger.debug(postdao.getDearId("김기범").toString());
	}
	@Test
	public void addDear() {
		logger.debug(postdao.addDear("김기범").toString());
	}

}


