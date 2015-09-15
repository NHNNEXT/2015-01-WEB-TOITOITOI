import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;

public class CafeDAOTest {
	private CafeDAO cafeDAO;
	
	@Before
	public void setup() {
		cafeDAO = new CafeDAO();
	}

	@Test
	public void getCafeTest() {
		ArrayList<Cafe> cafeList = cafeDAO.getCafeList();
		System.out.println(cafeList.get(0));
		assertNotNull(cafeList.get(0));
	}
	
	@Test
	public void getCafeListOrderByPostsTest() {
		ArrayList<Cafe> cafeList = cafeDAO.getCafeList(true);
		System.out.println(cafeList.get(0));
		System.out.println(cafeList.get(1));
		assertNotNull(cafeList.get(0));
	}
}
