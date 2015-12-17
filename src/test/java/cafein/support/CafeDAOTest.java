package cafein.support;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cafein.cafe.Place;
import cafein.cafe.Nudge;
import cafein.cafe.NudgeDAO;
import cafein.cafe.PlaceDAO;

public class CafeDAOTest {
	private PlaceDAO cafeDAO;
	private NudgeDAO nudgeDAOTest;
	
	@Before
	public void setup() {
		cafeDAO = new PlaceDAO();
	}
	
	@Before
	
	public void nudgeSetup() {
		nudgeDAOTest = new NudgeDAO();
	}

	
	@Test
	public void getNudegeListTest() {
		ArrayList<Nudge> nudgeList = nudgeDAOTest.getNudgeList(1);
		System.out.println(nudgeList.get(0));
	}
}
