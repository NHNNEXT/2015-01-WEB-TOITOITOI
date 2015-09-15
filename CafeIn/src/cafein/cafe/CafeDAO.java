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
	
	public ArrayList<Cafe> getCafeList() {
		return getCafeList(false);
	}
	
	public ArrayList<Cafe> getCafeList(boolean sort) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts "
				+ "FROM cafe c JOIN post p ON p.cid = c.cid GROUP BY c.cid ";
		sql += (sort)? "ORDER BY posts DESC;" : "";
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				int postNum = rs.getInt("posts");
				cafeList.add(new Cafe(cid, name, postNum));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cafeList;
	}
}
