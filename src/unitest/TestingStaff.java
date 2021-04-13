package unitest;
import VotingSystem.*;
import static org.junit.Assert.*;


import org.junit.Test;

public class TestingStaff {
	

	@Test
	public void testStaffConstructor() {
		// a new object of type Staff is created using the constructor
		Staff testStaff = new Staff(9999, "John Smith", "Password", 0, "0");
		//expected values are compared
		assertEquals(9999, testStaff.getId());
		assertEquals("John Smith", testStaff.getName());
		assertEquals("Password", testStaff.getPasword());
		assertEquals(0, testStaff.hasVoted());
		assertEquals("0", testStaff.getDate());
	}

	@Test
	public void testStaffSetters() {
		// a new object of type Staff is created using the constructor
		Staff testStaff = new Staff(0, "", "", 0, "0");
		//values are assigned using the setters methods
		testStaff.setId(555);
		testStaff.setName("Barbara Salas");
		testStaff.setPassword("secret");
		testStaff.setVoted();
		testStaff.setDate();
		//expected values are compared
		assertEquals(555, testStaff.getId());
		assertEquals("Barbara Salas", testStaff.getName());
		assertEquals("secret", testStaff.getPasword());
		assertEquals(1, testStaff.hasVoted());
		assertNotSame("0", testStaff.getDate()); 
		
	}

}
