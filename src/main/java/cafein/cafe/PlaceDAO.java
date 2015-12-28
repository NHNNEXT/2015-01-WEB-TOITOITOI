package cafein.cafe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import cafein.post.PostDAO;

public class PlaceDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);
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
