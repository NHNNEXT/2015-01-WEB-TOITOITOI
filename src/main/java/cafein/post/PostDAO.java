package cafein.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.mysql.jdbc.Statement;

public class PostDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);

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

	public Post addPost(Post post) throws SQLException {
		String sql = "INSERT INTO post (cid, content) VALUES(?, ?)";
		//String query = "SELECT pid FROM post ORDER BY postingtime desc limit 1";
		Connection conn = null;
		PreparedStatement pstmt = null;
	//	PreparedStatement pre = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, post.getCid());
			pstmt.setString(2, post.getContents());
			pstmt.executeUpdate();
			logger.debug(pstmt.toString());
			
			//pre = conn.prepareStatement(query);
			//pre.setInt(1, post.getCid());
			ResultSet re = pstmt.getGeneratedKeys();
			int last_insert_pid = 0;
			if(re.next()){
				last_insert_pid = re.getInt(1);
			}
			return getPostJustInserted(last_insert_pid);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Post getPostJustInserted(int pid) throws SQLException {
		String sql = "SELECT * FROM post WHERE pid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql+pid);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			ResultSet rs = pstmt.executeQuery();

			Post post = null;
			while (rs.next()) {
				int postId = rs.getInt("pid");
				String contents = rs.getString("content");
				String creattime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				post = new Post(postId, contents, creattime, liked);
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
			logger.debug("CID:"+cid);
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int pid = rs.getInt("pid");
				String contents = rs.getString("content");
				String creattime = rs.getString("postingtime");
				int liked = rs.getInt("liked");
				result.add(new Post(pid, contents, creattime, liked));
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
