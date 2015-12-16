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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.mysql.jdbc.Statement;

public class PostDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);
	@Autowired JdbcTemplate jdbcTmeplate;
	
	public Post addPost(Post post) {
		String sql = "INSERT INTO post (place_id, dear, content) VALUES(?, ?, ?)";
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
		} catch (SQLException e) {
			e.printStackTrace();
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

	public Post getPostByPostId(int postId) {
		String sql = "SELECT * FROM post WHERE id=?";
		
		return jdbcTmeplate.queryForObject(sql, new BeanPropertyRowMapper<Post>(Post.class),postId);
	}

	public List<Map<String, Object>> getDearList(Integer placeId, Integer nPage) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT dear, count(post.id) FROM post RIGHT JOIN place ON post.place_id = ?"
				+" group by dear ORDER BY COUNT(post.id) DESC LIMIT ?, 10";
		
		return jdbcTmeplate.queryForList(sql, placeId, (nPage-1)*10);
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
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}

	public void plusLike(int pid) {
		String sql = "UPDATE post SET liked = liked+1 WHERE pid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			logger.debug("connection:" + conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
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
}
