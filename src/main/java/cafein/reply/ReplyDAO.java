package cafein.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.mysql.jdbc.Statement;

public class ReplyDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(ReplyDAO.class);
	@Autowired JdbcTemplate jdbcTemplate;
	
	public Reply addReply(Reply reply) {

		String sql = "INSERT INTO reply (post_id,content) VALUES(?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			try {
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, reply.getPostId());
				pstmt.setString(2, reply.getContent());
				pstmt.executeUpdate();
				
				ResultSet result = pstmt.getGeneratedKeys();
				int last_insert_id = 0;
				if (result.next()) {
					last_insert_id = result.getInt(1);
					logger.debug("gernerated key"+last_insert_id);
				}
				return getReplyJustInserted(last_insert_id);
			} catch (SQLException e) {
				logger.debug("!!!!!");

				throw new RuntimeException();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Reply getReplyJustInserted(int id)  {
		logger.debug("in");
		String sql = "SELECT * FROM reply WHERE id=?";
		Reply test;
		test = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Reply>(Reply.class),new Object[]{id});
		logger.debug(test.toString());
		return test;
	}

	public ArrayList<Reply> getReplys(int pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM reply WHERE post_id=? ORDER BY createdtime DESC";
		ArrayList<Reply> result = new ArrayList<Reply>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String content = rs.getString("content");
				String createdtime = rs.getString("createdtime");
				int likes = rs.getInt("likes");
				int postId = rs.getInt("post_id");
				result.add(new Reply(id, content, createdtime, likes, postId));
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return result;
	}
	
	public void plusLike(int replyId)  {

		String sql = "UPDATE reply SET liked = liked+1 WHERE reid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void minusLike(int replyId) {
		String sql = "UPDATE reply SET liked = liked-1 WHERE reid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
