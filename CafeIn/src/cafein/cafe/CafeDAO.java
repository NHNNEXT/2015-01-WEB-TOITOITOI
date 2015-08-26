package cafein.cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cafein.post.Post;

public class CafeDAO {
	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3307/cafein";
		String id = "root";
		String pw = "db1004";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,id,pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	public ArrayList<Post> getPosts(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT * FROM post WHERE cid=? ORDER BY creattime";
		ArrayList<Post> result = new ArrayList<Post>();
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int pid = rs.getInt("pid");
				String contents = rs.getString("contents");
				String creattime = rs.getString("creattime");
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
