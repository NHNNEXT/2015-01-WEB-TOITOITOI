package cafein.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;

public class PostDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Post addPost(Post post) {
		// 기존에 있던 dear인지 체크 ->가져 온 dearId값을 적용해 post에 insert place_id, dear_id,
		// content해야함.
		Integer dearId = getDearId(post.getName());
		String sql = "INSERT INTO post (place_id, dear_id, content) VALUES(?, ?, ?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, post.getPlaceId());
				ps.setInt(2, dearId);
				ps.setString(3, post.getContent());
				return ps;
			}
		};

		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);
		Integer key = holder.getKey().intValue();
		logger.debug("key:" + key);
		return getPostByPostId(key);
	}

	public Integer getDearId(String dear) {
		String sql = "SELECT id from dear where name = ?";
		Integer id = null;

		try {
			id = jdbcTemplate.queryForObject(sql, new Object[] { dear }, Integer.class);
			return id;
		} catch (EmptyResultDataAccessException e) {
			return addDear(dear);
		}
	}

	// 새로Dear에게 쓸때만
	public Integer addDear(String dear) {
		String sql = "INSERT INTO dear (name) VALUES(?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, dear);
				return ps;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(psc, holder);
			Integer key = holder.getKey().intValue();
			return key;
		} catch (DuplicateKeyException DuplicateE) {
			return getDearId(dear);
		}
	}

	// Query바꾼 후 Test완료
	public Post getPostByPostId(Integer id) {
		String sql = "SELECT * FROM post WHERE post.id=?";
		Post post = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Post>(Post.class), id);
		return post;
	}

	// dear테이블과 join필요 place는 join 불필
	public List<Map<String, Object>> getDearList(Integer placeId, Integer nPage) {

		String sql = "SELECT post.dear_id AS id, dear.name AS name, COUNT(post.id) AS totalPostNum "
				+ "FROM post LEFT JOIN dear ON post.dear_id = dear.id "
				+ "WHERE post.place_id = ? "
				+ "GROUP BY post.dear_id ORDER BY totalPostNum DESC LIMIT ?, 10";

		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql, new Object[] { placeId, (nPage - 1) * 10 });
		return result;
	}

	// dearId를 parameter로 받는다면 dear join없이 가능.
	public List<Map<String, Object>> getPreviews(Integer placeid, Integer dearId, int nPage) {
		int startingRow = (nPage - 1) * 20;
		String sql = "SELECT id, LEFT(content, 80) AS preview, likes FROM post "
				+ "WHERE place_id = ? AND dear_id = ? "
				+ "ORDER BY likes DESC LIMIT ?, 20";
		
		return jdbcTemplate.queryForList(sql, new Object[] { placeid, dearId, startingRow });
	}

	public void plusLike(int postid) {
		String sql = "UPDATE post SET likes = likes+1 WHERE id = ?";
		jdbcTemplate.update(sql, postid);
	}
}
