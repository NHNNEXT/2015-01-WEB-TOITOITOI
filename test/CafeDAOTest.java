import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;

public class CafeDAOTest {

	@Test
	public void getCafeTest() {
		CafeDAO cafedao = new CafeDAO();
		ArrayList<Cafe> cafeList = cafedao.getCafeList();
		System.out.println(cafeList.get(0));
		assertNotNull(cafeList.get(0));
	}
	
	@Test
	public void searchCafeTest() {
		CafeDAO cafedao = new CafeDAO();
		ArrayList<Cafe> cafeList = cafedao.searchCafe("test");
		System.out.println(cafedao.searchCafe("test"));
 	}

}
