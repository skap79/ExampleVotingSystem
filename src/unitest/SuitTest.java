package unitest; //a package was created to separate it from the program

import org.junit.runner.RunWith; 
import org.junit.runners.Suite;
 
@RunWith(Suite.class) //Suite class is invoked
@Suite.SuiteClasses({ TestingStaff.class, TestingCandidate.class, TestingAdmin.class, TestingVotingController.class,
	                  TestingVotingInterface.class}) //a collection of test classes are met to run all together
public class SuitTest {
 
}
