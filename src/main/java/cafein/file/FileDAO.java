package cafein.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class FileDAO extends JdbcDaoSupport{
	private static final Logger logger = LoggerFactory.getLogger(FileDAO.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String addFileInfo(ImageFile imageFile){
		String sql = "INSERT INTO imagefile (original_filename, stored_filename) VALUES (?, ?)";
        jdbcTemplate.update(sql, new Object[] {imageFile.getOriginal_filename(), imageFile.getStored_filename()});  
		
		return imageFile.getStored_filename();
	}

	public void updatePostId(String storedFileName) {
		
	}
}
