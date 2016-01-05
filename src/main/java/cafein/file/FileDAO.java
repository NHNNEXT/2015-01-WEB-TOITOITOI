package cafein.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class FileDAO extends JdbcDaoSupport{
	@Autowired
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FileDAO.class);
	
	
	public String addFileInfo(ImageFile imagefile){
		logger.debug("add in");
		String sql = "INSERT INTO imagefile (original_filename, stored_filename) VALUES (?, ?)";
		System.out.println(jdbcTemplate);
		jdbcTemplate.update(sql, new Object[] {imagefile.getOriginal_filename(), imagefile.getStored_filename()});  
		return imagefile.getStored_filename();
	}

	public void updatePostId(Integer postId, String storedFileName) {
		String sql = "UPDATE imagefile SET post_id=? WHERE stored_filename=?;";
		jdbcTemplate.update(sql, postId, storedFileName);
	}

	public String getStroedFileNameByPostId(Integer postid) throws EmptyResultDataAccessException {
		String sql = "SELECT stored_filename FROM imagefile WHERE post_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[] {postid}, String.class);  
	}

	public String getOriginalFileNameByPostId(Integer postid) throws EmptyResultDataAccessException {
		String sql = "SELECT original_filename FROM imagefile WHERE post_id=?";
		return jdbcTemplate.queryForObject(sql, new Object[] {postid}, String.class);  
	}
}
