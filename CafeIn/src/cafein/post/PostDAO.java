package cafein.post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		String sql = "INSERT INTO post (contents) VALUES(?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			System.out.println("connection:"+conn);
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
}
