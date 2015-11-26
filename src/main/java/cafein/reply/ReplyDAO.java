package cafein.reply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cafein.post.Post;

public class ReplyDAO {
	private static final Logger logger = LoggerFactory.getLogger(ReplyDAO.class);

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3307/cafein";
		String id = "root";
		String pw = "db1004";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Reply addReply(String content, int pid) throws SQLException {

		String sql = "INSERT INTO reply (pid,content) VALUES(?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			pstmt.setString(2, content);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return getReplyJustInserted(pid);
	}

	public Reply getReplyJustInserted(int pid) throws SQLException {
		//다른사람이 같은 post
		String sql = "SELECT * FROM reply WHERE pid=? ORDER BY postingtime DESC limit 1";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			ResultSet rs = pstmt.executeQuery();
			
			Reply newReply=null;
			while(rs.next()){
				int Pid = rs.getInt("pid");
				String contents = rs.getString("content");
				String creattime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				newReply = new Reply(Pid,contents,creattime,liked); 
			}
			return newReply;
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

		String sql = "SELECT * FROM reply WHERE pid=? ORDER BY postingtime DESC";
		ArrayList<Reply> result = new ArrayList<Reply>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int reid = rs.getInt("reid");
				String content = rs.getString("content");
				String postingtime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				result.add(new Reply(reid, content, postingtime, liked));
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
