package cafein.cafe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

public class PlaceDAO extends JdbcDaoSupport {

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

	public String getName(int placeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * FROM place WHERE id=?";
		String placeName = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, placeId);
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
