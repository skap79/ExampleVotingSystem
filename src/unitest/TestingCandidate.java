package unitest;
import VotingSystem.*;
import static org.junit.Assert.*;


import org.junit.Test;

public class TestingCandidate {
	

	@Test
	public void testCandidateConstructor() {
		// a new object of type Candidate is created using the constructor
		Candidate testCandidate = new Candidate(9999, "Taika Waititi", 0, "movies");
		//expected values are compared
		assertEquals(9999, testCandidate.getCandidateCode());
		assertEquals("Taika Waititi", testCandidate.getName());
		assertEquals(0, testCandidate.getVotes());
		assertEquals("movies", testCandidate.getDept());
	
	}

	@Test
	public void testCandidateSetters() {
		// a new object of type Candidate is created using the constructor
		Candidate testCandidate = new Candidate(0, "", 0, "0");
		//values are assigned using the setters methods
		testCandidate.setCandidateCode(333);
		testCandidate.setName("Marco Polo");
		testCandidate.addVote();
		testCandidate.setDept("Marketing");
		//expected values are compared
		assertEquals(333, testCandidate.getCandidateCode());
		assertEquals("Marco Polo", testCandidate.getName());
		assertEquals(1, testCandidate.getVotes());
		assertEquals("Marketing", testCandidate.getDept());
		
		
	}

}
