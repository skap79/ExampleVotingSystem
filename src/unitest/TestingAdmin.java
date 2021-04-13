package unitest;
import VotingSystem.*;
import static org.junit.Assert.*;


import org.junit.Test;

public class TestingAdmin {
	

	@Test 
	public void testAdminConstructor() {
		// a new object of type Admin is created using the constructor
		Admin testAdmin = new Admin(1111, "Adm one", "aone", "mypass");
		//expected values are compared
		assertEquals(1111, testAdmin.getId());
		assertEquals("Adm one", testAdmin.getName());
		assertEquals("aone", testAdmin.getUser());
		assertEquals("mypass", testAdmin.getPassword());
	
	}

	@Test
	public void testAdminSetters() {
		// a new object of type Admin is created using the constructor
		Admin testAdmin = new Admin(0, "", "", "");
		//values are assigned using the setters methods
		testAdmin.setId(8888);
		testAdmin.setName("Robert Carlos");
		testAdmin.setUser("rcarlos");;
		testAdmin.setPassword("newpass");
		//expected values are compared
		assertEquals(8888, testAdmin.getId());
		assertEquals("Robert Carlos", testAdmin.getName());
		assertEquals("rcarlos", testAdmin.getUser());
		assertEquals("newpass", testAdmin.getPassword());
		
		
	}

}
