package cafein.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class FileDAO extends JdbcDaoSupport{
	@Autowired
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FileDAO.class);
	
	
	public void addFileInfo(Image imagefile){
		logger.debug("add in");
		String sql = "INSERT INTO imagefile (original_filename, stored_filename) VALUES (?, ?)";
		jdbcTemplate.update(sql, new Object[] {imagefile.getOriginalName(), imagefile.getStoredName()});
	}

	public void updatePostId(Integer postId, String storedFileName) {
		String sql = "UPDATE imagefile SET post_id=? WHERE stored_filename=?;";
		jdbcTemplate.update(sql, postId, storedFileName);
	}
	
	public Image getImagefileByPostId(Integer postId) throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM imagefile WHERE post_id=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Image>(Image.class), postId);
	}
}
