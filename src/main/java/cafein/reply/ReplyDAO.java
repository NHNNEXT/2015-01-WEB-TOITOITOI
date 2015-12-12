package cafein.reply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import cafein.post.Post;

public class ReplyDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(ReplyDAO.class);

	// public Connection getConnection() {
	// String url = "jdbc:mysql://localhost:3307/cafein";
	// String id = "root";
	// String pw = "db1004";
	// try {
	// Class.forName("com.mysql.jdbc.Driver");
	// return DriverManager.getConnection(url, id, pw);
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// return null;
	// }
	// }

	public Reply addReply(Reply reply) throws SQLException {

		String sql = "INSERT INTO reply (post_id,content) VALUES(?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, reply.getPostId());
			pstmt.setString(2, reply.getContent());
			pstmt.executeUpdate();

			ResultSet result = pstmt.getGeneratedKeys();
			int last_insert_id = 0;
			if (result.next()) {
				last_insert_id = result.getInt(1);
			}
			return getReplyJustInserted(last_insert_id);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Reply getReplyJustInserted(int replyId) throws SQLException {
		// 다른사람이 같은 post
		String sql = "SELECT * FROM reply WHERE id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Reply reply = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String content = rs.getString("content");
				String createdtime = rs.getString("createdtime");
				int likes = rs.getInt("likes");
				reply = new Reply(id, content, createdtime, likes);
			}
			return reply;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
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
				result.add(new Reply(id, content, createdtime, likes));
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void plusLike(int replyId) throws SQLException {

		String sql = "UPDATE reply SET liked = liked+1 WHERE reid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void minusLike(int replyId) throws SQLException {
		String sql = "UPDATE reply SET liked = liked-1 WHERE reid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
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
