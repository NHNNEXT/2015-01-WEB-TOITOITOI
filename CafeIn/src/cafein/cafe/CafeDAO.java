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
			return DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ArrayList<Cafe> getCafeList() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM cafe";
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				cafeList.add(new Cafe(cid, name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cafeList;
	}

	public ArrayList<Cafe> searchCafe(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM cafe WHERE name LIKE ?";
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				cafeList.add(new Cafe(cid, name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			//
			e.printStackTrace();
		}
		return cafeList;
	}
}
