package cafein.cafe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

public class placeDAO extends JdbcDaoSupport {

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

	private String getCafeListSQL(boolean keywordSearch, boolean sortByPostNum) {
		String searchQuery = (keywordSearch) ? " WHERE c.name LIKE ? " : "";
		String orderQuery = (sortByPostNum) ? " ORDER BY posts DESC " : "";

		String result = "SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts "
				+ " FROM cafe c LEFT JOIN post p ON p.cid = c.cid " + searchQuery + " GROUP BY c.cid " + orderQuery;

		return result;
	}

	public ArrayList<Place> getCafeList() {
		return getCafeList(false);
	}

	public ArrayList<Place> getCafeList(boolean sort) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = getCafeListSQL(false, sort);
		System.out.println("result query : " + sql);

		ArrayList<Place> cafeList = new ArrayList<Place>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int placeid = rs.getInt("id");
				String name = rs.getString("name");
				cafeList.add(new Place(placeid,name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cafeList;
	}

	public ArrayList<Place> searchCafe(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = getCafeListSQL(true, false);
		ArrayList<Place> cafeList = new ArrayList<Place>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// 이것도 중복 줄일 수 있을 것 같은데..!
			pstmt.setString(1, "%" + keyword + "%");
			System.out.println("result query : " + pstmt);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				cafeList.add(new Place(id, name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			//
			e.printStackTrace();
		}
		return cafeList;
	}

	public String getName(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM place WHERE id=?";
		String cafeName = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				cafeName = rs.getString("name");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cafeName;
	}
}
