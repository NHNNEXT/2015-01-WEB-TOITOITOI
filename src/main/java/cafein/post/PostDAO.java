package cafein.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String sql = "INSERT INTO post (place_id, dear, content) VALUES(?, ?, ?)";
		// String query = "SELECT pid FROM post ORDER BY postingtime desc limit
		// 1";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			System.out.println("connection:" + conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, post.getPlaceId());
			pstmt.setString(2, post.getDear());
			pstmt.setString(3, post.getContent());
			pstmt.executeUpdate();
			logger.debug(pstmt.toString());

			ResultSet re = pstmt.getGeneratedKeys();
			int last_insert_pid = 0;
			if (re.next()) {
				last_insert_pid = re.getInt(1);
			}
			return getPostByPostId(last_insert_pid);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Post getPostByPostId(int postId) throws SQLException {
		String sql = "SELECT * FROM post WHERE id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			logger.debug(sql+postId);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			ResultSet rs = pstmt.executeQuery();

			Post post = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				int placeId = rs.getInt("place_id");
				String dear = rs.getString("dear");
				String contents = rs.getString("content");
				String createdtime = rs.getString("createdtime");
				int likes = rs.getInt("likes");
				post = new Post(id, placeId, dear, contents, createdtime, likes);
			}
			logger.debug(post.toString());
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

	public Map<String, List<Post>> getDearsWithPreviews(Integer placeId, Integer nPage) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT * FROM ("
					+ "SELECT p.dear AS dear, p.id AS id, LEFT(p.content, 50) AS content, p.createdtime AS createdtime, p.likes AS likes, COUNT(r.id) AS replies "
					+ "FROM post AS p LEFT JOIN reply AS r "
					+ "ON p.id = r.post_id "
					+ "WHERE place_id=? "
					+ "GROUP BY p.id "
					+ "ORDER BY COUNT(r.id) "
					+ "DESC) AS dears LIMIT ?, 20";
		logger.debug(sql);
		Map<String, List<Post>> result = new HashMap<String, List<Post>>(); 
		int startRow = (nPage-1)*10;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			logger.debug("here");
			pstmt.setInt(1, placeId);
			pstmt.setInt(2, startRow);
			ResultSet rs = pstmt.executeQuery();
			
			result = getDearsWithPreviewsByResultSet(rs, result, placeId);

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			logger.error("Can not get dear list from DB");
			e.printStackTrace();
		}
		return result;
	}	
	
	private Map<String, List<Post>> getDearsWithPreviewsByResultSet(ResultSet rs, Map<String, List<Post>> result, int placeId) throws SQLException {
		ArrayList<Post> previews = new ArrayList<Post>();
		String currentDearGroup = null; 
		int inputCount = 3;
		
		while (rs.next()){
			String dearName = rs.getString("dear");
			int id = rs.getInt("id");
			String content = rs.getString("content");
			String createdtime = rs.getString("createdtime");
			int likes = rs.getInt("likes");
			Post post = new Post(id, placeId, dearName, content, createdtime, likes);
			logger.debug(post.toString());
			if(inputCount<=0 || dearName.equals(currentDearGroup)==false){
				if(currentDearGroup != null){
					result.put(currentDearGroup, previews);
				}
				currentDearGroup = dearName;
				inputCount=3;
				for(int i=0; i<previews.size() ; i++){
					previews.remove(i);
				}
			}
			previews.add(post);
			inputCount--;
		}
		return result;
	}

	public ArrayList<Post> getPreviews(int placeid, String dearName, int nPage) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT p.id, p.dear, LEFT(p.content, 50), p.createdtime, p.likes " + "FROM post p LEFT JOIN reply r ON p.id = r.post_id "
				+ "WHERE p.place_id=? AND p.dear=? group by p.id ORDER BY COUNT(r.id) DESC LIMIT ?, 20";
		ArrayList<Post> result = new ArrayList<Post>();
		int startingRow = (nPage - 1) * 20;

		try {
			logger.debug("placeId:" + placeid);
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, placeid);
			pstmt.setString(2, dearName);
			pstmt.setInt(3, startingRow);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int postid = rs.getInt("p.id");
				String contents = rs.getString("LEFT(p.content, 50)");
				String createdtime = rs.getString("p.createdtime");
				int likes = rs.getInt("p.likes");
				result.add(new Post(postid, placeid, dearName, contents, createdtime, likes));
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
