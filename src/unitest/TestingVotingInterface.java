package unitest;
import VotingSystem.*;
import static org.junit.Assert.*;


import org.junit.Test;

public class TestingVotingInterface {
	 VotingInterface vi = new VotingInterface(); //a new object of type VotingInterface is created

	@Test 
	public void testVotingInterfacer() {	
			
		 vi.start1(); //a load is made with all information
		
		 boolean adminLogin = vi.validateAdmin("rstuven", "456"); //a login with valid data
		//expected values are compared
		 assertEquals(true,adminLogin);
		 
		 adminLogin = vi.validateAdmin("Fake", "456"); //a login with invalid data 
		//expected values are compared
		 assertEquals(false,adminLogin);
	}


}
