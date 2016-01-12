package cafein.cafe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CandidateDAO extends JdbcDaoSupport{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<CandidateDear> getRecommendedDears(Integer placeId) {
		String sql = "SELECT * FROM candidate WHERE place_id=?";
		List<CandidateDear> result;
		result = jdbcTemplate.query(sql,new Object[] {placeId}, new BeanPropertyRowMapper<CandidateDear>(CandidateDear.class));
		return result;
	}

}
