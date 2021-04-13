package unitest;
import VotingSystem.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.Calendar;

import org.junit.Test;

public class TestingVotingController {
	VotingController vc = new VotingController();

	@Test
	public void testDates() {	

		// A date is created 2020-06-11 10:20:35
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, 5, 11, 10, 20, 35);
		Date dateTest = calendar.getTime();
        //the date format is changed into string 
		String dateFormatTest = vc.getDateFormat(dateTest);		 
		
		assertEquals("11/06/2020",dateFormatTest); //start date
		
		Date endDateTest = vc.getEndDate(dateTest, 7);
		dateFormatTest = vc.getDateFormat(endDateTest);	
		
		assertEquals("18/06/2020",dateFormatTest); //end date
		
	}

	@Test
	public void testAdmin() {	
		// a new object of type Admin is created using the constructor
		Admin newTestAdmin = new Admin(9999, "Admin Test", "atest", "Password2020");
		
		vc.saveNewAdmin(newTestAdmin);
		
		Admin testAdmin = vc.getAdmin("atest");
		//expected values are compared
		assertEquals(9999,testAdmin.getId());
		assertEquals("atest",testAdmin.getUser());
		assertEquals("Admin Test",testAdmin.getName());
		assertEquals("Password2020",testAdmin.getPassword());
		
		
		//the object is modified
		newTestAdmin.setName("New Admin Test Name");
		vc.saveUpdateAdmin(newTestAdmin, "atest");
		//the modified object is got
		Admin testAdmin2 = vc.getAdminById(9999);
		//expected values are compared
		assertEquals(9999,testAdmin2.getId());
		assertEquals("atest",testAdmin2.getUser());
		assertEquals("New Admin Test Name",testAdmin2.getName());
		assertEquals("Password2020",testAdmin2.getPassword());
		
		
		//the object is deleted 
		boolean removeAdm = vc.removeAdmin("atest");
		//result from delete is verified
		assertEquals(true,removeAdm);
	}

	@Test
	public void testStaff() {	
		// a new object of type Staff is created using the constructor
		Staff newTestStaff = new Staff(7777, "Satff Name", "Password2020", 0, "0");
		
		vc.saveNewStaff(newTestStaff);
		
		Staff testStaff = vc.getStaff(7777);
		//expected values are compared
		assertEquals(7777,testStaff.getId());
		assertEquals("Satff Name",testStaff.getName());
		assertEquals("Password2020",testStaff.getPasword());
		assertEquals(0,testStaff.hasVoted());
		assertEquals("0",testStaff.getDate());
		
		//the object is modified
		newTestStaff.setName("New Satff Test Name");
		vc.saveUpdateStaff(newTestStaff, 7777);
		
		Staff testStaff2 = vc.getStaff(7777);
		//expected values are compared
		assertEquals("New Satff Test Name",testStaff2.getName());
		//the object is deleted 
		boolean removeStaff = vc.removeStaff(7777);
		//result from delete is verified
		assertEquals(true,removeStaff);
	}
	
	@Test
	public void testCandidate() {	
		// a new object of type Candidate is created using the constructor
		Candidate newTestCandidate= new Candidate(8888, "Candidate Test Name", 3, "Test Department");
		
		vc.saveNewCandidate(newTestCandidate);
		
		Candidate testCandidate = vc.getCandidate(8888);
		//expected values are compared
		assertEquals(8888,testCandidate.getCandidateCode());
		assertEquals("Candidate Test Name",testCandidate.getName());
		assertEquals(3,testCandidate.getVotes());
		assertEquals("Test Department",testCandidate.getDept());
		
		//the object is modified
		newTestCandidate.setDept("Marketing");
		vc.saveUpdateCandidate(newTestCandidate, 8888);
		
		Candidate testCandidate2 = vc.getCandidate(8888);
		//expected values are compared
		assertEquals("Marketing",testCandidate2.getDept());
		//the object is deleted 
		boolean removeCandidate = vc.removeCandidate(8888);
		//result from delete is verified
		assertEquals(true,removeCandidate);
	}
	

}
