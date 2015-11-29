package cafein.cafe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

public class CafeDAO extends JdbcDaoSupport{

//	public Connection getConnection() {
//		String url = "jdbc:mysql://localhost:3307/cafein";
//		String id = "root";
//		String pw = "db1004";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			return DriverManager.getConnection(url, id, pw);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return null;
//		}
//	}

	private String getCafeListSQL(boolean keywordSearch, boolean sortByPostNum) {
		String searchQuery = (keywordSearch) ? " WHERE c.name LIKE ? " : "";
		String orderQuery = (sortByPostNum) ? " ORDER BY posts DESC " : "";

		String result = "SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts "
				+ " FROM cafe c LEFT JOIN post p ON p.cid = c.cid " + searchQuery + " GROUP BY c.cid " + orderQuery;

		return result;
	}

	public ArrayList<Cafe> getCafeList() {
		return getCafeList(false);
	}

	public ArrayList<Cafe> getCafeList(boolean sort) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = getCafeListSQL(false, sort);
		System.out.println("result query : " + sql);

		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
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
	
	public ArrayList<Cafe> getCafeList(String latitude, String longitude) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts, distance_between(c.latitude, c.longitude, "
				+ latitude + ", " + longitude
				+ ") AS distance "
				+ "FROM cafe c "
				+ "LEFT JOIN post p ON p.cid = c.cid "
				+ "WHERE c.latitude IS NOT NULL "
				+ "GROUP BY c.cid "
				+ "ORDER BY distance";
		System.out.println("result query : " + sql);

		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				int postNum = rs.getInt("posts");
				Double distance = rs.getDouble("distance");
				
				cafeList.add(new Cafe(cid, name, postNum, distance));
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

		String sql = getCafeListSQL(true, false);
		ArrayList<Cafe> cafeList = new ArrayList<Cafe>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// 이것도 중복 줄일 수 있을 것 같은데..!
			pstmt.setString(1, "%" + keyword + "%");
			System.out.println("result query : " + pstmt);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				int postNum = rs.getInt("posts");
				cafeList.add(new Cafe(cid, name, postNum));
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

		String sql = "SELECT * FROM cafe WHERE cid=?";
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
