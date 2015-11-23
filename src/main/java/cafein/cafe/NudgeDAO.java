package cafein.cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NudgeDAO {
	 
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
	 
	public ArrayList<Nudge> getNudgeList(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT * FROM nudge WHERE cid=? ORDER BY nudgeid";

		ArrayList<Nudge> nudgeList = new ArrayList<Nudge>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nudgeid = rs.getInt("nudgeid");
				String contents = rs.getString("contents");
			    nudgeList.add(new Nudge(nudgeid, contents));
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nudgeList;
	}

}
