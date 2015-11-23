package cafein.post;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cafein.post.PostDAO;

public class PostDAO {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);

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

	public Post addPost(Post post) throws SQLException {
		String sql = "INSERT INTO post (cid, content) VALUES(?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, post.getCid());
			pstmt.setString(2, post.getContents());
			pstmt.executeUpdate();
			return getPostJustInserted(post.getCid());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Post getPostJustInserted(int cid) throws SQLException {
		String sql = "SELECT * FROM post WHERE cid=? ORDER BY postingtime DESC limit 1";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			
			Post post=null;
			while(rs.next()){
				int pid = rs.getInt("pid");
				String contents = rs.getString("content");
				String creattime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				post = new Post(pid,contents,creattime,liked);
			}
			return post;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public ArrayList<Post> getPosts(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM post WHERE cid=? ORDER BY postingtime DESC";
		ArrayList<Post> result = new ArrayList<Post>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int pid = rs.getInt("pid");
				String contents = rs.getString("content");
				String creattime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				result.add(new Post(pid,contents,creattime,liked));
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void plusLike(int pid) throws SQLException {
		String sql = "UPDATE post SET liked = liked+1 WHERE pid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
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

	public void minusLike(int pid) throws SQLException {
		String sql = "UPDATE post SET liked = liked-1 WHERE pid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
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
}
