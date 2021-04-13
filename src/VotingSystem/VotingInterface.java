package VotingSystem;
import java.io.*;
import java.util.Calendar; //
import java.sql.Timestamp;
import java.util.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/* author : Christian Meza */


public class VotingInterface
{
	//  private variables are created
	private VotingController vc; 
	private Staff theStaff;
	private Candidate theCandidate;
	private Admin theAdmin; //selected administrator object is received 
	//private final String USERNAME = "admin";
	//private final String PASSWORD ="123";
	private int numberOfCandidates = 0;

	private BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));

	public static void main(String[] args)
	{
		VotingInterface vi = new VotingInterface();
		vi.start1();
		vi.start2();
	}
	public void start1()
	{
		vc = new VotingController();

	}

	public void start2()
	{

		commenceVoting();
	}


	public void commenceVoting()
	{
		boolean systemQuit = false;
		while (!systemQuit)
		{
			String input = null;
			System.out.println("\n\t\t============== eVoting System =====================\n\n");
			System.out.print("Welcome to the eVoting System\nEnter \"v\" to Vote as staff \nOR \"a\" to login in as system administrator or \"h\" for Help : ");
			input = getInput();

			if (input.equalsIgnoreCase("V"))
			{
				try
				{
					Timestamp currentDate = new Timestamp(System.currentTimeMillis()); 
					Date start = vc.getStartDate();
					Date end = vc.getEndDate(start, 7); //arguments start date and seven days are passed to generate the end date
					if (start.before(currentDate) && end.after(currentDate)) //if the voting date is in this date range, it is true
					{
						manageVote();
					}

					else 
					{
						System.out.println("\n*** The system is not available this day for voting. ***" );
						systemQuit = false;
					}
				}
				catch(NullPointerException e)
				{
					System.out.println("\n *** The system does not have a voting date range settled. *** ");

				}
			}
			else if (input.equalsIgnoreCase("A")) //ignore the case during comparison
			{
				validateAdmin();
				systemQuit = manageAdmin();
			}else if (input.equalsIgnoreCase("h")) //ignore the case during comparison
			{
				DisplayHelp();
			}
			else
			{
				System.out.println("Your input was not recognised");
			}
		}
	}

	//screen input reader
	private String getInput()
	{
		String theInput = "";

		try
		{
			theInput = in.readLine().trim();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		return theInput;
	}


	public void manageVote()
	{
		boolean moveOn = false;
		int flag = 0;
		//loop for each voter
		while (moveOn == false && flag < 3)
		{

			System.out.print("Please enter your staff ID :");
			try
			{
				theStaff = vc.getStaff(Integer.parseInt(getInput())); //receives a String to convert it into an integer
				if (theStaff != null) 
				{
					System.out.print("Please enter your password :");
				}
				if (!theStaff.getPasword().equals(getInput())) //receives input from the user
				{
					System.out.println("\nYour password is incorrect\nGood bye...!");
					moveOn = false;
					flag++;
				}
				else
				{
					if(theStaff.hasVoted() == 1) //when staff member has already voted, a message is displayed
					{
						System.out.println("\nYou have voted and cannot vote again\nGood bye...!");
						moveOn = true;
					}
					else if (theStaff.hasVoted() == 0) //when a staff member has no voted yet, the voted is got and a method is called
					{
						getStaffVote(); 
						moveOn = true;
					}
					else 
					{
						System.out.println("There seems to be a problem. Contact your administrator");

					}
				}
			}

			catch(NumberFormatException e) //This exception is when the conversion from string to integer failed,
			//a message is displayed
			{
				System.out.println("Invalid entry - you must enter a number\nPlease try again");
				flag++;
			}
			catch(NullPointerException e) //This exception is caught when null pointer exception occurs, 
			//and a message is displayed
			{
				flag++;
				System.out.println("No match found your ID,\nplease press ENTER to try again or enter \"Q\" to quit: ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					moveOn = true;
				}
			}
		}

		if(flag >=3)
		{
			System.out.print("\n** You have entered a wrong ID/Password three times. ** \n");

		}else {

			System.out.print("going back to the voting screen...");
		}

	}


	public void displayVotingScreen() //This method displays the list of candidates with their Codes
	{

		System.out.println("\nWelcome "+ theStaff.getName()+"!\n");
		numberOfCandidates = 0;

		ArrayList candidates = vc.getCandidates(); 

		Iterator it = candidates.iterator();
		System.out.println("\tCode\tCandidate Name\tDepartment");
		System.out.println("\t====\t==============\t==============\n");
		while(it.hasNext())
		{   theCandidate = (Candidate)it.next();
		System.out.println("\t" + theCandidate.getCandidateCode() + "\t" + theCandidate.getName()+ "\t" + theCandidate.getDept());
		numberOfCandidates++;
		}
	}

	public void deleteStartDate() //this method deletes a start date for voting 
	{

		System.out.println(" \n Write Y to confirm this change or any key to quit:  ");
		String input = getInput();
		if (input.equalsIgnoreCase("Y"))
		{
			vc.deleteStartDate();	//this methos is called from the vc to delete the start date
			System.out.println(" *** The start date was deleted ***");
			System.out.println("\nPress ENTER to continue...");
			String q= getInput();
		}

		else
		{
			System.out.println("\n  *** The action was cancelled *** " );
			System.out.println("\nPress ENTER to continue...");
			String q= getInput();
		}
	}

	public void displayStaffScreen() //this method shows the staff list with Staff ID and Staff name
	{

		ArrayList staffs = vc.getStaffs(); //Brings the whole array list to be saved into staffs object

		Iterator it = staffs.iterator(); 
		System.out.println("\n\tID\tStaff Name\tPassword");
		System.out.println("\t====\t==============\t==============\n");
		while(it.hasNext())
		{   
			theStaff = (Staff)it.next(); //it reads the object array list formed by objects (Staff) one by one
			System.out.println("\t" + theStaff.getId() + "\t" + theStaff.getName() + "\t" + theStaff.getPasword());
		}

		System.out.println("\nPress ENTER to continue...");
		String q= getInput(); 
	}

	public void displayCandidateList() //This method shows the list of candidates
	{
		ArrayList candidates = vc.getCandidates();

		Iterator it = candidates.iterator();
		System.out.println("\n\tCode\tCandidate Name\tDepartment");
		System.out.println("\t====\t==============\t==============\n");
		while(it.hasNext())
		{  
			theCandidate = (Candidate)it.next(); //it reads the object array list formed by objects (Candidate) one by one
			System.out.println("\t" + theCandidate.getCandidateCode() + "\t" + theCandidate.getName()+ "\t" + theCandidate.getDept());
		}

		System.out.println("\nPress ENTER to continue...");
		String q= getInput();
	}


	public void displayAdminScreen() //This method displays the information of the administrators
	{
		ArrayList admins = vc.getAdmins(); //returns a collection of admins which are saved in an array object called "admins"

		Iterator it = admins.iterator(); //iterates a collection of admins
		System.out.println("\n\tID\tAdministrator Name\tUser name");
		System.out.println("\t======\t===================\t========\n");
		while(it.hasNext())
		{ 
			theAdmin = (Admin)it.next(); //it reads the object array list formed by objects (Admin) one by one
			System.out.println("\t" + theAdmin.getId() + "\t" + theAdmin.getName() + "\t\t" + theAdmin.getUser());
		}

		System.out.println("\nPress ENTER to continue...");
		String q= getInput();
	}


	public void displayStaffVoted() //method to display whether the staff has voted including ID, name and date of the voting
	{
		ArrayList staffs = vc.getStaffs();

		Iterator it = staffs.iterator();
		System.out.println("\n\tID\tStaff Name\t\tVoted\tDate and time"); //column names are added
		System.out.println("\t====\t===============\t\t====\t=============\n");

		String voted; // variables are created to display performed votes and date
		String dateVoted;
		while(it.hasNext())
		{  
			theStaff = (Staff)it.next(); //it reads the object array list formed by objects (Staff) one 
			if(theStaff.hasVoted()== 0) //verifies whether a staff member voted or not
			{
				voted = "No"; // "No" when a person has not voted
				dateVoted = ""; //Date shows nothing
			}

			else
			{
				voted = "Yes"; // "Yes" when a person has voted
				dateVoted = theStaff.getDate(); //Date shows the date when a staff member voted
			}

			System.out.println("\t" + theStaff.getId() + "\t" + theStaff.getName() 
			+ "\t\t" + voted + "\t" + dateVoted);

		}
		System.out.println("\nPress ENTER to continue...");
		String q= getInput();
	}


	public void displayDateRange() //this method shows the date range to vote
	{
		try
		{
			System.out.printf("\nRange date for voting is between "+ vc.getDateFormat(vc.getStartDate())
			+" and "+ vc.getDateFormat(vc.getEndDate(vc.getStartDate(),7))+"\n"); //the arguments are passed
		}

		catch(NullPointerException e) //when a date is not established yet, a message is displayed
		{
			System.out.println("\n *** The system does not have a voting date range set yet. *** ");
		}

		System.out.println("\nPress ENTER to continue...");
		String q= getInput();
	}


	public void displayStaffManage() //this method displays the staff management
	{
		boolean quit= false; 
		String option;
		do
		{
			System.out.print("\n\nManage Staff accounts: ");
			System.out.print("\n\n\t1. View staff accounts");
			System.out.print("\n\t2. Add a staff account");
			System.out.print("\n\t3. Update a staff account");
			System.out.print("\n\t4. Delete a staff account");
			System.out.printf("\n\nChoose your option or q to quit: ");
			option= getInput();
			switch (option) //according to the option chosen is tested against each case. Matching produces the execution of a code
			{
			case "1": //case is the option number
				displayStaffScreen();
				break;

			case "2":
				addStaff();
				break;

			case "3":
				updateStaff();
				break;

			case "4":
				removeStaff();
				break;

			case "q":
				quit = true;
				break;

			default:
				System.out.println("That is an invalid choice. Try again."); //when there are no matchings, a message is displayed
			}
		}

		while (!quit); //the code block is executed once while condition quit is different to quit (true)

	}


	public void displayCandidateManage() //this method displays the candidate management
	{
		boolean quit= false;
		String option;
		do
		{
			System.out.print("\n\nManage Candidate list: ");
			System.out.print("\n\n\t1. View the candidate list.");
			System.out.print("\n\t2. Add a candidate.");
			System.out.print("\n\t3. Update a candidate.");
			System.out.print("\n\t4. Delete a candidate.");
			System.out.printf("\n\nChoose your option or q to quit: ");

			option= getInput();

			switch (option) //the option chosen is tested against each case. Matching produces the execution of a code
			{
			case "1": //case is the option number
				displayCandidateList();
				break;

			case "2":
				addCandidate();
				break;

			case "3":
				updateCandidate();
				break;

			case "4":
				removeCandidate();
				break;

			case "q":
				quit = true;
				break;

			default: //when there are no matchings, a message is displayed
				System.out.println("That is an invalid choice. Try again.");
			}
		}

		while (!quit); //the code block is executed once while condition quit is different to quit (true)
	}


	public void displayDateManage() //this method displays the date management
	{
		boolean quit= false;
		String option;
		do
		{
			System.out.print("\n\nManage Date Range (seven days from the start date): ");
			System.out.print("\n\n\t1. View the current date range ");
			System.out.print("\n\t2. Add/Update a start date.");
			System.out.print("\n\t3. Delete a start date.");
			System.out.printf("\n\nChoose your option or q to quit: ");

			option= getInput();

			switch (option) //the option chosen is tested against each case. Matching produces the execution of a code
			{
			case "1": //case is the option number
				displayDateRange();
				break;

			case "2":
				setStartDate();
				break;

			case "3":
				deleteStartDate();
				break;

			case "q":
				quit = true;
				break;

			default: //when there are no matchings, a message is displayed
				System.out.println("That is an invalid choice. Try again.");
			}
		}

		while (!quit); //the code block is executed once while condition quit is different to quit (true)


	}
	public void displayAdminManage() //this method is to display the admin account management
	{
		boolean quit= false;
		String option;
		do
		{
			System.out.print("\n\nManage Admin accounts: ");
			System.out.print("\n\n\t1. View admin accounts");
			System.out.print("\n\t2. Add an admin account");
			System.out.print("\n\t3. Update an admin account");
			System.out.print("\n\t4. Delete an admin account");
			System.out.printf("\n\nChoose your option or q to quit: ");

			option= getInput();

			switch (option) //the option chosen is tested against each case. Matching produces the execution of a code
			{

			case "1": //case is the option number
				displayAdminScreen();
				break;

			case "2":
				addAdmin();
				break;

			case "3":
				updateAdmin();
				break;

			case "4":
				removeAdmin();
				break;

			case "q":
				quit = true;
				break;

			default: //when there are no matchings, a message is displayed
				System.out.println("That is an invalid choice. Try again.");
			}
		}

		while (!quit); //the code block is executed once while condition quit is different to quit (true)
	}


	public void setStartDate() //this method establishes a start date
	{
		String inputDate = null; //inputDate variable is initialised as null
		boolean check = true;
		while(check) {
			System.out.printf("\nEnter the start date for voting in format dd/MM/yyyy: ");
			inputDate = getInput(); //this input is a string date

			if (inputDate.trim().equals("")) //check whether date is empty
			{
				System.out.println("\n**** This is not a valid date ***");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}

			else //date is not empty
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Set date format
				dateFormat.setLenient(false); //this method is used to specify whether a date is tolerable(lenient) or not

				try 
				{
					Date start = dateFormat.parse(inputDate);  //convert String to Date
					System.out.println(" \n Write Y to confirm this change or any key to quit:  ");
					String input = getInput();
					if (input.equalsIgnoreCase("Y"))
					{
						vc.saveStartDate(start); //start date is saved into dateRange.txt 
						System.out.println("\n *** The new Start Day is : " + inputDate +" ***"); //displays that the format is correct
						System.out.println("\nPress ENTER to continue...");
						String q= getInput();

					}else {
						System.out.println("\n  * The action was cancelled * " );
						
					}
					check = false;
				}

				catch (ParseException e)
				{
					System.out.println("\n *** " + inputDate+" is invalid Date format *** "); //Displays that the format is not valid
					System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
					if ("q".equalsIgnoreCase(getInput()))
					{
						System.out.println("Good bye!");
						check = false;
					}
				}

			}
		}

	}


	public void addStaff()  //method to add new staff
	{
		int inputID=0;
		String inputName = "";
		String inputPassword ="";
		Staff checkStaff =null; 

		boolean check =true;
		while (check) {
			try {
				System.out.println("\nEnter Staff ID : "); //enter the new staff ID to be added
				inputID=Integer.parseInt(getInput());

				checkStaff = vc.getStaff(inputID);

				if (checkStaff != null) //if the ID staff is already used by another staff, a message is displayed
				{
					System.out.println("\nThe ID "+ inputID + " is taken, please try again");
				}

				else
				{
					check = false;
				}

			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

		}

		check =true;
		while (check) {

			System.out.println("\nEnter Staff name: "); //the user enters the new Staff name to be added
			inputName = getInput();
			System.out.println("\nEnter Staff password: "); //the user enters the new Staff password to be added
			inputPassword = getInput();

			if (inputName.isEmpty() || inputName.replaceAll("\\s+","").isEmpty() || inputPassword.isEmpty() || inputPassword.replaceAll("\\s+","").isEmpty())
			{
				System.out.println("\nAll the attributes are required ");			
			}
			else
			{check = false;}

		}

		//a new Staff object is created with ID, name, password, with 0 for voting (because this person has 
		//not voted yet) and "0" for Date (because a date is recorded when a person has voted)
		Staff newStaff = new Staff(inputID, inputName, inputPassword,0,"0"); 
		System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");

		if ("Y".equalsIgnoreCase(getInput())) //a confirmation for a staff addition is used to execute the if block 
		{
			vc.saveNewStaff(newStaff); //the new staff is saved
			System.out.println("\n  * The new staff was created * " );
		}

		else //when the condition is not fulfilled, a message is displayed
		{
			System.out.println("\n  * The action was cancelled * " ); 
		}

	}


	public void addCandidate()  //method to add new candidate
	{
		int code=0;
		String inputName="";
		String inputdept="";

		Candidate checkCandidate =null; 

		boolean check =true;
		while (check) {
			try {
				System.out.println("\nEnter Candidate Code : ");
				code=Integer.parseInt(getInput());

				checkCandidate = vc.getCandidate(code);

				if (checkCandidate != null) //if the Candidate code is already used by another Candidate, a message is displayed
				{
					System.out.println("\nThe Candidate Code "+ code + " is taken, please try again");
				}

				else
				{
					check = false;
				}

			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

		}

		check =true;
		while (check) 
		{
			System.out.println("\nEnter Candidate name: ");
			inputName = getInput();

			System.out.println("\nEnter Candidate Department: ");
			inputdept = getInput();
			if (inputName.isEmpty() || inputdept.isEmpty() || inputdept.replaceAll("\\s+","").isEmpty() || inputName.replaceAll("\\s+","").isEmpty())
			{
				System.out.println("\nAll the attributes are required ");			
			}
			else
			{check = false;}
		}
		//a new Candidate object is created with code, inputName (candidate name) and 0 (number of votes)
		Candidate newCandidate = new Candidate(code, inputName, 0,inputdept); 
		System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");

		if ("Y".equalsIgnoreCase(getInput()))
		{
			vc.saveNewCandidate(newCandidate);
			System.out.println("\n  * The new candidate was created * " );
		}

		else
		{
			System.out.println("\n  * The action was cancelled * " );
		}

	}


	public void addAdmin()  //this method adds a new administrator
	{
		int inputNewAdminID=0;
		String inputNewAdminName ="";
		String inputNewAdminUsername= "";
		String inputNewAdminPassword="";
		Admin checkAdm = null;	

		boolean check =true;
		while (check) {
			try {
				System.out.println("\nEnter Admin ID : ");
				inputNewAdminID=Integer.parseInt(getInput());

				checkAdm = vc.getAdminById(inputNewAdminID);

				if (checkAdm != null) //if the ID admin is already used by another administrator, a message is displayed
				{
					System.out.println("\nThe ID "+ inputNewAdminID + " is taken, please try again");
				}

				else
				{
					check = false;
				}

			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

		}

		check=true;
		while (check) {

			System.out.println("\nEnter Admin name: "); //A user name of the new administrator is asked to be added
			inputNewAdminName = getInput();

			System.out.println("\nEnter Admin password: ");
			inputNewAdminPassword = getInput();


			if (inputNewAdminName.isEmpty() || inputNewAdminName.replaceAll("\\s+","").isEmpty() || inputNewAdminPassword.isEmpty() || inputNewAdminPassword.replaceAll("\\s+","").isEmpty())
			{
				System.out.println("\nAll the attributes are required, try again. ");			
			}
			else
			{check = false;}
		}

		checkAdm = null;
		check = true;

		while (check) 
		{
			System.out.println("\nEnter Admin username: "); // A user name of the new administrator is asked to be added
			inputNewAdminUsername = getInput();

			checkAdm = vc.getAdmin(inputNewAdminUsername); //looking for an admin with that user name. This result is saved 
			//into a variable "checkAdm"
			if (checkAdm != null) //if this condition is true, user name is already taken
			{
				System.out.println("\nThe Username "+ inputNewAdminUsername + " is taken, please try again");
			}

			else
			{
				if (inputNewAdminUsername.isEmpty() || inputNewAdminUsername.replaceAll("\\s+","").isEmpty() )
				{
					System.out.println("\nAll the attributes are required ");			
				}
				else
				{check = false;}
			}
		}

		Admin newAdmin = new Admin(inputNewAdminID,inputNewAdminName,inputNewAdminUsername,inputNewAdminPassword);

		System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");
		if ("Y".equalsIgnoreCase(getInput()))
		{
			vc.saveNewAdmin(newAdmin); //the new Administrator's information is saved
			System.out.println("\n  * The new admin was created * " );
		}

		else
		{
			System.out.println("\n  * The action was cancelled * " );
		}

	}


	public void removeStaff()  //method to remove staff
	{
		int inputID;
		boolean check = true;
		while(check) {
			try {
				System.out.println("\nEnter Staff ID: "); //Staff ID is asked to the user to remove a staff member
				inputID = Integer.parseInt(getInput());
				boolean result;
				Staff staffRemove = vc.getStaff(inputID); //information related to this ID is obtained and saved in "staffRemove"
				System.out.println("\nYou have chosen ID: " + staffRemove.getId() + ", Name: " + staffRemove.getName());
				System.out.println("\nWrite 'Y' to confirm this removement or any key to quit:  ");

				if ("Y".equalsIgnoreCase(getInput()))
				{
					result=vc.removeStaff(inputID); //the method "removeStaff" is called to remove a staff member

					if (result) 
					{
						System.out.println("\n  * The staff member was deleted * " );
						System.out.println("\nPress ENTER to remove another Staff or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}

					else
					{
						System.out.println("\n  * This staff ID does not exit * " );
						System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}

				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
					System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
					if ("q".equalsIgnoreCase(getInput()))
					{
						System.out.println("Good bye!");
						check = false;
					}
				}
			}
			catch(NumberFormatException e)
			{

				System.out.println("\n** The ID Staff must be a Number ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("\n** This Staff member does not exist ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
		}
	}


	public void removeCandidate()  //method to remove a candidate
	{
		int inputCode;
		boolean result;
		boolean check = true;
		while(check) {
			try {
				System.out.println("\nEnter Candidate Code: "); //Candidate code is asked to the user to remove a candidate
				inputCode = Integer.parseInt(getInput()); //this string code is converted into an integer code
				Candidate candidateRemove = vc.getCandidate(inputCode); //information related to this ID is obtained and saved in "candidateUpdate"
				System.out.println("\nYou have chosen ID: " + candidateRemove.getCandidateCode() + ", Name: " + candidateRemove.getName());
				System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");

				if ("Y".equalsIgnoreCase(getInput()))
				{
					result = vc.removeCandidate(inputCode); //this result will be true if it finds a candidate with the same code
					if (result) 
					{
						System.out.println("\n  * The candidate was deleted * " );
						System.out.println("\nPress ENTER to remove another Candidate or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}

					else
					{
						System.out.println("\n  * This candidate Code does not exit * " );
						System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}

				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
					System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
					if ("q".equalsIgnoreCase(getInput()))
					{
						System.out.println("Good bye!");
						check = false;
					}
				}
			}
			catch(NumberFormatException e)
			{

				System.out.println("\n** The Candidate Code must be a Number ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("\n** This Candidate does not exist *\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
		}
	}


	public void updateStaff()  //method to update staff
	{
		int inputID;
		boolean check = true;
		while(check) 
		{
			try 
			{
				System.out.println("\nEnter Staff ID: "); //an Staff ID is entered by the user to update staff information
				inputID = Integer.parseInt(getInput()); //this String is converted into an integer variable

				Staff staffUpdate = vc.getStaff(inputID); //information related to this ID is obtained and saved in "staffUpdate"
				System.out.println("\nYou have chosen ID: " + staffUpdate.getId() + ", Name: " + staffUpdate.getName());

				String inputNewStaffID =""; //this new Staff ID is initialised as empty
				Staff checkStaff = null;

				while (check) 
				{
					try
					{
						System.out.println("\nEnter new staff's ID or press enter for no changes: ");
						inputNewStaffID = getInput();
						//verifies if the new Staff ID is empty and different of the current Staff ID
						if (!inputNewStaffID.equals("") && Integer.parseInt(inputNewStaffID) != staffUpdate.getId()  )
						{
							//an Staff object is obtained entering the new Staff ID
							checkStaff = vc.getStaff(Integer.parseInt(inputNewStaffID)); 
							//checks if the object obtained is different to null (this is, whether already exists an Staff with that ID)
							if (checkStaff != null) 
							{
								System.out.println("\nThe ID "+ inputNewStaffID + " is taken, please try again");
							}

							else
							{
								check = false; 
							}

						}

						else
						{
							check = false; //no changes in the Staff ID
						}

					}
					catch(NumberFormatException e)
					{
						System.out.println("That was not a number \nPlease try again");
					}
				}

				System.out.println("\nEnter new name or press enter for no changes: ");
				String inputNewName= getInput();
				System.out.println("\nEnter new password or press enter for no changes: ");
				String inputNewPassword= getInput(); 

				if (!inputNewStaffID.equals("")) 
				{ 
					staffUpdate.setId(Integer.parseInt(inputNewStaffID)); 
				}

				if (!inputNewName.equals("")) //When the new name entered is different to null (no empty)
				{
					staffUpdate.setName(inputNewName); // it is set as staff name
				}

				if (!inputNewPassword.equals("")) //When the new password entered is different to null (no empty)
				{
					staffUpdate.setPassword(inputNewPassword); // it is set as new password
				}

				System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");

				if ("Y".equalsIgnoreCase(getInput()))
				{
					vc.saveUpdateStaff(staffUpdate,inputID); //ID staff is passed to know what is the staff to be updated
					System.out.println("\n  * The Staff member information was updated * " );
				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
				}

				System.out.println("\n ");
			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

			catch(NullPointerException e)
			{
				System.out.println("\n** This Staff member does not exist ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
		}

	}


	public void removeAdmin()  //method to remove an administrator's account 
	{
		String inputAdmUsername;
		boolean result;

		boolean check = true;
		while(check) 
		{
			try {
				System.out.println("\nEnter Admin's user name: ");
				inputAdmUsername = getInput();
				Admin adminRemove = vc.getAdmin(inputAdmUsername); //information related to this Username is obtained and saved in "adminRemove"
				System.out.println("\nYou have chosen Username: " + adminRemove.getUser() + ", Name: " + adminRemove.getName());
				System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");
				if ("Y".equalsIgnoreCase(getInput()))
				{
					result = vc.removeAdmin(inputAdmUsername); //this result will be true if it finds a admin with the same user name
					if (result) 
					{
						System.out.println("\n  * The admin account was deleted * " );
						System.out.println("\nPress ENTER to remove another Admin or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}


					else
					{
						System.out.println("\n  * This admin user name does not exit * " );
						System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
						if ("q".equalsIgnoreCase(getInput()))
						{
							System.out.println("Good bye!");
							check = false;
						}
					}
				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
					System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
					if ("q".equalsIgnoreCase(getInput()))
					{
						System.out.println("Good bye!");
						check = false;
					}
				}
			}	
			catch(NullPointerException e)
			{
				System.out.println("\n** This Admin does not exist *\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}
		}
	}


	public void updateAdmin()  //method to update the administrator's information
	{
		String inputUser;
		String inputNewAdmID = "";
		boolean check = true;
		Admin checkAdmin = null;
		while (check) {
			try {
				System.out.println("\nEnter admin user: "); //an admin user name is entered
				inputUser = getInput();

				Admin adminUpdate = vc.getAdmin(inputUser); //information about that user is got
				System.out.println("\nYou have chosen admin user: " + adminUpdate.getUser() + " Name: " + adminUpdate.getName());

				checkAdmin = null;
				check = true;
				while (check) 
				{    //system ask for changes to update the admin 
					try {
						System.out.println("\nEnter new administrator's ID or press enter for no changes: ");
						inputNewAdmID = getInput();

						if (!inputNewAdmID.equals("") && Integer.parseInt(inputNewAdmID) != adminUpdate.getId())
						{   //Checks whether the new admin ID is not empty and already in use 
							checkAdmin = vc.getAdminById(Integer.parseInt(inputNewAdmID));

							if (checkAdmin != null) //if the ID admin is already used by another administrator, a message is displayed
							{
								System.out.println("\nThe ID "+ inputNewAdmID + " is taken, please try again");
							}

							else
							{
								check = false;
							}

						}

						else
						{
							check = false;
						}
					}catch(NumberFormatException e)
					{
						System.out.println("That was not a number \nPlease try again");
					}

				}

				if (!inputNewAdmID.equals("")) //When the new admin ID entered is different to null (no empty)
				{ 
					adminUpdate.setId(Integer.parseInt(inputNewAdmID)); //the new admin ID  is set and converted into integer
				}

				System.out.println("\nEnter new administrator's name or press enter for no changes: ");
				String inputNewAdmName= getInput(); 
				if (!inputNewAdmName.equals("")) //to change the administrator's name
				{
					adminUpdate.setName(inputNewAdmName);
				}

				String inputNewAdmUsername= "";	//to input a new admin username

				checkAdmin = null;
				check = true;
				while (check) 
				{
					System.out.println("\nEnter new administrator's username or press enter for no changes: ");
					inputNewAdmUsername= getInput(); 

					if (!inputNewAdmID.equals("") && !inputNewAdmID.equals(adminUpdate.getUser()))
					{   //when both conditions are fulfilled
						checkAdmin = vc.getAdmin(inputNewAdmUsername); //check this information is not null
						if (checkAdmin != null) 
						{
							System.out.println("\nThe Username "+ inputNewAdmUsername + " is taken, please try again");
						}

						else
						{
							check = false;
						}
					}

					else
					{
						check = false;
					}
				}

				if (!inputNewAdmUsername.equals(""))
				{
					adminUpdate.setUser(inputNewAdmUsername); //the new admin user name is established when it is not empty 
				}

				System.out.println("\nEnter new administrator's password or press enter for no changes: ");
				String inputNewAdmPassword= getInput();
				if (!inputNewAdmPassword.equals(""))
				{
					adminUpdate.setPassword(inputNewAdmPassword);
				}

				System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");
				if ("Y".equalsIgnoreCase(getInput()))
				{
					vc.saveUpdateAdmin(adminUpdate,inputUser);
					System.out.println("\n  * The Administrator's information was updated * " );
				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
				}

				System.out.println("\n ");
				check =false;
			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

			catch(NullPointerException e)
			{
				System.out.println("\n** This Admin does not exist ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}


		}
	}


	public void updateCandidate()  //method to update Candidate's information
	{

		boolean check = true;
		while (check) {
			try //try to get the data for the candidate and update it
			{
				int inputCode;

				System.out.println("\nEnter Candidate code: ");
				inputCode = Integer.parseInt(getInput());

				Candidate candidateUpdate = vc.getCandidate(inputCode); //information about that user is obtained
				System.out.println("\nYou have chosen ID: " + candidateUpdate.getCandidateCode() + ", Name: " + candidateUpdate.getName());
				String inputNewCandidateCode ="";
				Candidate checkCandidate = null;
				check = true;

				while (check) 
				{
					try {
						System.out.println("\nEnter new Candidate's Code or press enter for no changes: ");
						inputNewCandidateCode = getInput();
						if (!inputNewCandidateCode.equals("") && Integer.parseInt(inputNewCandidateCode) != candidateUpdate.getCandidateCode())
						{ //Checks whether the new candidate code is not empty and already in use 
							checkCandidate = vc.getCandidate(Integer.parseInt(inputNewCandidateCode));
							if (checkCandidate != null) 

							{
								System.out.println("\nThe ID "+ inputNewCandidateCode + " is taken, please try again");
							}

							else
							{
								check = false;
							}
						}

						else
						{
							check = false;
						}
					}
					catch(NumberFormatException e)
					{
						System.out.println("That was not a number \nPlease try again");
					}
				}

				System.out.println("\nEnter new name or press enter for no changes: ");
				String inputNewCandidateName= getInput();
				System.out.println("\nEnter new department or press enter for no changes: ");
				String inputNewCandidateDept= getInput();

				if (!inputNewCandidateCode.equals(""))
				{ 
					candidateUpdate.setCandidateCode(Integer.parseInt(inputNewCandidateCode));
				}

				if (!inputNewCandidateName.equals(""))
				{
					candidateUpdate.setName(inputNewCandidateName);
				}
				if (!inputNewCandidateDept.equals(""))
				{
					candidateUpdate.setDept(inputNewCandidateDept);
				}

				System.out.println("\nWrite 'Y' to confirm this change or any key to quit:  ");

				if ("Y".equalsIgnoreCase(getInput()))
				{
					vc.saveUpdateCandidate(candidateUpdate, inputCode);
					System.out.println("\n  * The candidate was updated * " );
				}

				else
				{
					System.out.println("\n  * The action was cancelled * " );
				}
			}

			catch(NumberFormatException e)
			{
				System.out.println("That was not a number \nPlease try again");
			}

			catch(NullPointerException e)
			{
				System.out.println("\n** This candidate code does not exist ***\n");
				System.out.println("\nPress ENTER to try again or  \"Q\" to QUIT :  ");
				if ("q".equalsIgnoreCase(getInput()))
				{
					System.out.println("Good bye!");
					check = false;
				}
			}

			catch(Exception e)
			{
				System.out.println("We have a problem, please contact your administrator");
			}
		}

	}


	private void getStaffVote() //this method manages a staff's vote
	{
		int candidateCode;
		boolean retry = true;

		displayVotingScreen();

		while (retry)
		{
			System.out.print("\n\nEnter your candidate's code OR enter Q to quit voting : ");
			try{
				String input = getInput();

				if (input.equalsIgnoreCase("Q"))
				{
					retry = false;
				}
				else
				{
					candidateCode = Integer.parseInt(input);
					theCandidate = vc.getCandidate(candidateCode);
					System.out.print("\nYou have selected " + theCandidate.getName()+ ". \n\nEnter Y to confirm or any other key to Cancel, then press ENTER : ");

					if (getInput().equalsIgnoreCase("y"))
					{
						vc.recordVote();
						System.out.println("\n\nThanks for voting " + theStaff.getName() + ". Bye!!!");
						retry = false;
					}
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("That was not a number you entered\nPlease try again");
			}
			catch(NullPointerException e)
			{
				System.out.println("This candidate code does not exit\nPlease try again");
			}
			catch(Exception e){
				System.out.println("We have a problem, please contact your administrator");
			}
		}
	}

	private void validateAdmin() //this method validates an administrator
	{
		boolean adminQuit = false;

		while (!adminQuit)
		{
			System.out.print("\nYou have entered Administration space. \nEnter username or \"Q\" to quit : " );
			String input = getInput();
			if (input.equalsIgnoreCase("q"))
			{ //quit voting
				adminQuit = true;
			}

			else
			{
				//get user name and password
				String username, password = null;
				username = input.trim();
				System.out.print("\nPlease enter password : ");
				password = getInput().trim();

				if(validateAdmin(username, password))
				{ //validate admin
					//clearScreen();    - only work in BlueJ, cannot use this method in Windows Console

					while (!adminQuit)
					{
						boolean quit= false;
						String option;
						do
						{
							System.out.println("\nWelcome "+ theAdmin.getName()+"!\n");
							System.out.println("\n============== Administrator Menu =====================\n\n");
							System.out.print("1. Display the polling statistics. ");
							System.out.print("\n2. View staff voting status. ");
							System.out.print("\n3. Manage Staff accounts. ");
							System.out.print("\n4. Manage Administrator accounts. ");
							System.out.print("\n5. Manage Candidate accounts. ");
							System.out.print("\n6. Manage Date voting Range. ");
							System.out.printf("\n\nChoose your option or 'q' to quit: ");
							option= getInput();
							switch (option)
							{
							case "1":
								printVoteResults();
								break;

							case "2":
								displayStaffVoted();
								break;

							case "3":
								displayStaffManage();
								break;

							case "4":
								displayAdminManage();
								break;

							case "5":
								displayCandidateManage();
								break;

							case "6":
								displayDateManage();
								break;

							case "q":
								quit = true;
								break;

							default:
								System.out.println("That is an invalid choice. Try again.");
							}

						}

						while (!quit);

						adminQuit = true;
					}
				}

				else{
					System.out.println("Incorrect username/password.");
				}
			}
		}
	}

	private boolean manageAdmin() 
	{
		boolean adminQuit = false;
		boolean systemQuit = false;

		while (!adminQuit)
		{
			System.out.println("\nTo continue voting enter\"C\".\nTo end voting enter \"Stop\" : ");
			String input = getInput();
			if (input.equalsIgnoreCase("C"))
			{
				//back to voting
				adminQuit = true;
			}

			else if(input.equalsIgnoreCase("Stop"))
			{
				//stop system
				adminQuit = true;
				systemQuit = true;
				System.out.println("Voting System Closed");
			}

			else
			{
				System.out.println("Cannot understand your input, please re-enter : \n\n");
			}
		}

		return systemQuit;
	}

	public void DisplayHelp()
	{
		vc.DisplayHelp();

	}
	//prints out the voting results
	public void printVoteResults()
	{
		ArrayList candidates = vc.getCandidates();
		int totalVoters = vc.getTotalVoters();
		double totalVoted = 0;
		int candidateVotes = 0;

		//formatting display
		DecimalFormat df = new DecimalFormat("###.##");

		Iterator it = candidates.iterator();
		System.out.println("\n\t\t VOTING STATISTICS");
		System.out.println("\t\t=========================\n");
		System.out.println("Code\tName\t\tDepartment\tVotes\t(Vote%)");
		System.out.println("____\t________\t_________\t_____\t______\n");


		//calculate total voted
		while(it.hasNext()) {
			theCandidate = (Candidate) it.next();
			totalVoted += theCandidate.getVotes();// count total votes for this candidate
		}

		it = candidates.iterator();
		while(it.hasNext()) 
		{
			theCandidate = (Candidate) it.next();
			candidateVotes = theCandidate.getVotes();
			System.out.println(theCandidate.getCandidateCode() + "\t" + theCandidate.getName() + "\t" + theCandidate.getDept() + "\t" +
					candidateVotes +"\t(" + df.format((candidateVotes/totalVoted)*100) +"%)");
		}

		System.out.println("\nNumbers on voting list: " + totalVoters);
		System.out.println("Numbers voted: " + totalVoted + "(" + df.format((totalVoted/totalVoters)*100)  + "%)");
		System.out.println();
		System.out.println("\nPress any key to continue...");
		String q= getInput();

	}


	//validates administrator user name & password
	public boolean validateAdmin(String username, String password)
	{

		try
		{
			theAdmin = vc.getAdmin(username); 

			if(password.equals(theAdmin.getPassword()))
			{
				return true;
			}

			else
			{
				return false;
			}

		}
		catch(NullPointerException e)
		{
			return false;
		}

	}
}
