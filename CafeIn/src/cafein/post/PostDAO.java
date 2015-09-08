package cafein.post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostDAO {

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3307/cafein";
		String id = "root";
		String pw = "1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,id,pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addPost(Post post) throws SQLException {
		String sql = "INSERT INTO post (content) VALUES(?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			//logging framework를 적용해서 
			System.out.println("connection:"+conn);
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, post.getContents());
			pstmt.executeUpdate();
		}  finally {
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}
	
	public ArrayList<Post> getPosts(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT * FROM post WHERE cid=? ORDER BY postingtime";
		ArrayList<Post> result = new ArrayList<Post>();
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
}
