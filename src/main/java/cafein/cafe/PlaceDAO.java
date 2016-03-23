package cafein.cafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cafein.file.AbstractDao;

public class PlaceDAO extends AbstractDao {
	private static final Logger logger = LoggerFactory.getLogger(PlaceDAO.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Place getPlaceById(int placeId) {
		String sql = "SELECT * FROM place WHERE id=?";
		try {
			Place place = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Place>(Place.class), placeId);
			return place;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
