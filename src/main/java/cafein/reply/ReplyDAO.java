package cafein.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;

import cafein.file.AbstractDao;
import cafein.post.Post;

public class ReplyDAO extends AbstractDao {
	private static final Logger logger = LoggerFactory.getLogger(ReplyDAO.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Reply addReply(Reply reply) {

		String sql = "INSERT INTO reply (post_id,content) VALUES(?,?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, reply.getPostId());
				ps.setString(2, reply.getContent());
				return ps;
			}
		};
		final KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);

		return getReplyJustInserted(holder.getKey().intValue());
	}

	public Reply getReplyJustInserted(Integer id) {
		String sql = "SELECT * FROM reply WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Reply>(Reply.class), id);
	}

	public List<Reply> getReplys(Integer pid) {

		String sql = "SELECT * FROM reply WHERE post_id=? ORDER BY createdtime DESC";
		List<Reply> result;
		result = jdbcTemplate.query(sql, new Object[] { pid }, new BeanPropertyRowMapper<Reply>(Reply.class));
		return result;
	}

	public Reply plusLike(int replyId) {
		String updateSql = "UPDATE reply SET likes = likes+1 WHERE id = ?";
		String selectSql = "SELECT id, likes FROM reply WHERE id = ?";
		jdbcTemplate.update(updateSql, replyId);
		return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<Reply>(Reply.class), replyId);
	}


	public int getLikedOnReply(int replyId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM reply WHERE reid=?";
		int liked = 0;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				liked = rs.getInt("liked");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liked;
	}

}
