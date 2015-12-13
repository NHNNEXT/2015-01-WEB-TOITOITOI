package cafein.cafe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

public class PlaceDAO extends JdbcDaoSupport {

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

	private String getPlaceListSQL(boolean keywordSearch, boolean sortByPostNum) {
		String searchQuery = (keywordSearch) ? " WHERE c.name LIKE ? " : "";
		String orderQuery = (sortByPostNum) ? " ORDER BY posts DESC " : "";

		String result = "SELECT c.cid AS cid, c.name AS name, count(p.pid) AS posts "
				+ " FROM place p LEFT JOIN post p ON p.cid = c.cid " + searchQuery + " GROUP BY c.cid " + orderQuery;

		return result;
	}

	public Place getPlaceById (int placeId) {
		String sql = "SELECT * FROM place WHERE id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Place place = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, placeId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				place = new Place(id, name);
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e){
			logger.error("Can not get place date from DB");
			e.printStackTrace();
		}
		return place;
	}
	
	public ArrayList<Place> getPlaceList() {
		return getPlaceList(false);
	}

	public ArrayList<Place> getPlaceList(boolean sort) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = getPlaceListSQL(false, sort);
		System.out.println("result query : " + sql);

		ArrayList<Place> placeList = new ArrayList<Place>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int placeid = rs.getInt("id");
				String name = rs.getString("name");
				placeList.add(new Place(placeid,name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return placeList;
	}

	public ArrayList<Place> searchPlace(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = getPlaceListSQL(true, false);
		ArrayList<Place> placeList = new ArrayList<Place>();

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
				placeList.add(new Place(id, name));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			//
			e.printStackTrace();
		}
		return placeList;
	}

	public String getName(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM place WHERE id=?";
		String placeName = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				placeName = rs.getString("name");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return placeName;
	}
}
