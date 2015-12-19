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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;

public class PostDAO extends JdbcDaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Post addPost(Post post) {
		//기존에 있던 dear인지 체크 ->가져 온 dearId값을 적용해 post에 insert place_id, dear_id, content해야함.
		Integer dearId;
		dearId = getDearId(post.getName());
		String sql = "INSERT INTO post (place_id, dear, content) VALUES(?, ?, ?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, post.getPlaceId());
				ps.setString(2, post.getName());
				ps.setString(3, post.getContent());
				return ps;
			}
		};
		
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);	
		Integer key = holder.getKey().intValue();
		logger.debug("key:"+key);
		return getPostByPostId(key);
	}
	public Integer getDearId(String dear) {
		String sql = "SELECT id from dear where name = ?";
		Integer id = null;
		id = jdbcTemplate.queryForObject(sql, new Object[] {dear}, Integer.class);
		if(id != null){
			return id;
		}
		//null인경우 56번째 줄에서 EmptyResultDataAccessException발생후 프로그램 종료됨...
		return addDear(dear);
	}
	//새로Dear에게 쓸때만 
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
		jdbcTemplate.update(psc, holder);	
		Integer key = holder.getKey().intValue();
		return key;
	}
	//Query바꾼 후 Test완료
	public Post getPostByPostId(Integer id) {
		String sql = "SELECT * FROM post RIGHT JOIN dear ON post.dear_id = dear.id WHERE post.id=?";
		Post test = null;
		test = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Post>(Post.class),id);
		return test;
	}

	public List<Map<String, Object>> getDearList(Integer placeId, Integer nPage) {
		
		String sql = "SELECT dear, count(post.id) FROM post RIGHT JOIN place ON post.place_id = ?"
				+" group by dear ORDER BY COUNT(post.id) DESC LIMIT ?, 10";
		
		return jdbcTemplate.queryForList(sql, new Object[] {placeId, (nPage-1)*10});
	}	
	

	public List<Post> getPreviews(Integer placeid, String dearName, int nPage) {
		int startingRow = (nPage - 1) * 20;
		String sql = "SELECT p.id, p.dear, LEFT(p.content, 50), p.createdtime, p.likes " + "FROM post p LEFT JOIN reply r ON p.id = r.post_id "
				+ "WHERE p.place_id=? AND p.dear=? group by p.id ORDER BY COUNT(r.id) DESC LIMIT ?, 20";
		List<Post> result;
		result = jdbcTemplate.query(sql,new Object[]{placeid, dearName, startingRow},
			new RowMapper<Post>(){
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException{
				int postid = rs.getInt("p.id");
				String contents = rs.getString("LEFT(p.content, 50)");
				String createdtime = rs.getString("p.createdtime");
				int likes = rs.getInt("p.likes");
				return new Post(postid, placeid, dearName, contents, createdtime, likes);
			}
		});
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
