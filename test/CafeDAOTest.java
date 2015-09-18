import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cafein.cafe.Cafe;
import cafein.cafe.CafeDAO;
import cafein.cafe.Nudge;
import cafein.cafe.NudgeDAO;

public class CafeDAOTest {
	private CafeDAO cafeDAO;
	private NudgeDAO nudgeDAOTest;
	
	@Before
	public void setup() {
		cafeDAO = new CafeDAO();
	}
	
	@Before
	
	public void nudgeSetup() {
		nudgeDAOTest = new NudgeDAO();
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

	@Test
	public void searchCafeTest() {
		System.out.println(cafeDAO.searchCafe("test"));
 	}
	
	@Test
	public void getNudegeListTest() {
		ArrayList<Nudge> nudgeList = nudgeDAOTest.getNudgeList(1);
		System.out.println(nudgeList.get(0));
	}
}
