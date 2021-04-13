package VotingSystem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/* author : Christian Meza */


public class VotingController
{
	private ArrayList staffs = new ArrayList();
	private ArrayList candidates = new ArrayList();
	private ArrayList admins = new ArrayList(); //this array list is an array list for the admins

	private Staff theStaff;
	private Candidate theCandidate;
	private Admin theAdmin; // this admin object was created  
	private Date startDate; //this variable date is created for start date


	public VotingController() //
	{
		loadStaffData();
		loadCandidateData();
		loadAdminData();
		loadStartDate(); //this method is loaded at the begin of the program
	}


	//loads candidates from file. This method is complete and working ok. Read department was aggregated.
	public void loadCandidateData()
	{
		try
		{
			String fileName = "candidates.txt";
			File theFile = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(theFile));

			String candidateData;

			while((candidateData = reader.readLine())!= null)
			{
				String[] candidateDetails = candidateData.split(",");
				int code = Integer.parseInt(candidateDetails[0]);
				int votes = Integer.parseInt(candidateDetails[2]);
				theCandidate = new Candidate(code, candidateDetails[1], votes,candidateDetails[3]); 
				candidates.add(theCandidate);
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error! There was a problem with loading candidate names from file");
		}catch(Exception e){
			System.out.println("We have a problem with the candidate list, please contact your administrator");
		}
	}

	//loads staff names from file. This method is complete and working ok.
	public void loadStaffData()
	{
		try
		{
			String fileName = "staff.txt";
			File theFile = new File(fileName);
			BufferedReader reader = new BufferedReader(new  FileReader(theFile));
			String studentData;
			String[] staffDetails;

			while((studentData = reader.readLine()) != null)
			{
				staffDetails = studentData.split(",");
				int id = Integer.parseInt(staffDetails[0]);
				int voted = Integer.parseInt(staffDetails[3]);
				theStaff = new Staff(id, staffDetails[1], staffDetails[2], voted, staffDetails[4]);
				staffs.add(theStaff);
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error! There was a problem with loading staff names from file");
		}
		catch(Exception e)
		{
			System.out.println("Error! Unlown problem accoured during loading the staff names from file.");
		}
	}


	//loads admin names from file. This method is complete and working ok.
	public void loadAdminData()
	{
		try
		{
			String fileName = "Admin.txt";
			File theFile = new File(fileName);
			BufferedReader reader = new BufferedReader(new  FileReader(theFile));
			String adminData;
			String[] adminDetails;

			while((adminData = reader.readLine()) != null)
			{
				adminDetails = adminData.split(",");
				int id = Integer.parseInt(adminDetails[0]);
				theAdmin = new Admin(id, adminDetails[1], adminDetails[2], adminDetails[3]);
				admins.add(theAdmin); //array of administrators (details are added in this array called "admins")
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error! There was a problem with loading administrator names from file");
		}
		catch(Exception e)
		{
			System.out.println("Error! Unlown problem accoured during loading the administrator names from file.");
		}
	}


	//loads date range from file. 
	public void loadStartDate()
	{
		try
		{
			String fileName = "dateRange.txt";  
			File theFile = new File(fileName); //this text file is created
			BufferedReader reader = new BufferedReader(new FileReader(theFile));

			String dateStart; 
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  //SimpleDateFormat was imported

			while((dateStart = reader.readLine())!= null) //while the file contains a date the block is executed
			{
				startDate = dateFormat.parse(dateStart); //start date for voting
			}
			reader.close(); 
		}
		
		catch(IOException | ParseException e)
		{
			System.out.println("Error! There was a problem with loading date range from file");
		}
	}

	public void DisplayHelp() //displays the information from the file "help.txt"
	{
		try
		{
			String fileName = "help.txt";
			File theFile = new File(fileName);
			BufferedReader reader = new BufferedReader(new  FileReader(theFile));
			String helpData;
	

			while((helpData = reader.readLine()) != null)
			{
				System.out.println(helpData);
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error! There was a problem with loading the help file");
		}
		catch(Exception e)
		{
			System.out.println("Error! There was a problem with loading the help file");
		}
	}
	

	//this method returns a staff if it is found in the staffs ArrayList
	public Staff getStaff(int id)
	{
		Iterator it = staffs.iterator(); //iterates the array list to access the data
		while(it.hasNext())
		{
			theStaff = (Staff) it.next(); //each object is iterated to look for the Staff object with that ID
			if(theStaff.getId()== id) 
			{
				return theStaff; //when the staff id matches with the id received as argument, theStaff object is returned
			}
		}
		return null;
	}

	public void saveNewStaff (Staff newStaff) //this method receives a Staff object called "newStaff"
	{
		staffs.add(newStaff); //the new staff object is added to the array
		saveStaffData(); //this method saves the new staff object in the text file
	}

	public void saveNewCandidate (Candidate newCandidate) //this method save a new candidate
	{
		candidates.add(newCandidate); //the new staff object is added to the array
		saveCandidateData(); //this method saves the new staff object in the text file
	}

	public void saveUpdateStaff (Staff staffUpdate, int staffUpdateId) //this method saves a updated staff information
	{
		Iterator it = staffs.iterator(); 
		ArrayList staffstmp = new ArrayList(); //a temporal array list is created to save the objects
		
		while(it.hasNext())
		{
			theStaff = (Staff) it.next();

			if (theStaff.getId() == staffUpdateId) //if ID from the staffUpdateId matches with the ID obtained from theStaff
			{
				staffstmp.add(staffUpdate);	   //then this ID is added to the temporal array list "staffstmp"
			}

			else
			{
				staffstmp.add(theStaff); //otherwise the object from the original array list is added to the temporal

			}
		}
		staffs = staffstmp; // the temporal array list is saved in "staffs" to replace it
		saveStaffData(); //this is saved
	}
	
	public void saveUpdateCandidate (Candidate candidateUpdate, int candidateUpdateCode) //this method saves update candidate data
	{ 

		Iterator it = candidates.iterator();
		ArrayList candidatestmp = new ArrayList();
		
		while(it.hasNext())
		{
			theCandidate = (Candidate) it.next();

			if (theCandidate.getCandidateCode() == candidateUpdateCode)
			{
				candidatestmp.add(candidateUpdate);	   
			}

			else
			{
				candidatestmp.add(theCandidate);

			}
		}
		candidates = candidatestmp; //this temporal array is replaced by candidates array list
		saveCandidateData();
	}

	public void saveNewAdmin (Admin newAdmin)
	{
		admins.add(newAdmin); //the new admin object is added to the array
		saveAdminData(); //this method saves the new admin object in the text file

	}


	public void saveUpdateAdmin (Admin adminUpdate, String userName) //this method save the Admin object with the updated information
	{

		Iterator it = admins.iterator();
		ArrayList adminstmp = new ArrayList();
		String adminDetails;
		while(it.hasNext())
		{
			theAdmin = (Admin) it.next();

			if (theAdmin.getUser().equals(userName))
			{
				adminstmp.add(adminUpdate);	   
			}

			else
			{
				adminstmp.add(theAdmin);

			}
		}
		admins = adminstmp; //The admins array list is replaced by the array (adminstmp) with the updated information
		saveAdminData();
	}



	//removes a staff if found in the staffs ArrayList
	public boolean removeStaff(int id)
	{
		Iterator it = staffs.iterator();
		while(it.hasNext())
		{
			theStaff = (Staff) it.next();
			if(theStaff.getId()== id)
			{
				it.remove(); 
				saveStaffData(); //this method saves the modification in the text file 
				return true;
			}
		}

		return false;
	}
	
	public boolean removeCandidate(int code)
	{
		Iterator it = candidates.iterator();
		while(it.hasNext())
		{
			theCandidate = (Candidate) it.next();
			if(theCandidate.getCandidateCode()== code)
			{
				it.remove(); 
				saveCandidateData(); //this method saves the modification in the text file 
				return true;
			}
		}

		return false;
	}

	//removes an administrator if is found in the staffs ArrayList
	public boolean removeAdmin(String userName) 
	{
		Iterator it = admins.iterator();
		while(it.hasNext())
		{
			theAdmin = (Admin) it.next();
			if(theAdmin.getUser().equals(userName)) //when a user name is found, the object is removed from the array list
			{
				it.remove(); 
				saveAdminData(); //this method saves the modification in the text file 
				return true;
			}
		}

		return false;
	}


	//returns an administrator if is found in the admins ArrayList
	public Admin getAdmin(String user)
	{
		Iterator it = admins.iterator();
		while(it.hasNext())
		{
			theAdmin = (Admin) it.next();
			if(theAdmin.getUser().equalsIgnoreCase(user))
			{
				return theAdmin;
			}
		}
		return null;
	}
	
	
	//returns an administrator if is found in the admins ArrayList by ID
	public Admin getAdminById(int adminId)
	{
		Iterator it = admins.iterator();
		while(it.hasNext())
		{
			theAdmin = (Admin) it.next();
			if(theAdmin.getId() == adminId)
			{
				return theAdmin;
			}
		}
		return null;
	}


	//returns the candidate if found is in the candidates ArrayList
	public Candidate getCandidate(int candidateCode)
	{
		Iterator it = candidates.iterator();
		while(it.hasNext())
		{
			theCandidate = (Candidate) it.next();
			if(theCandidate.getCandidateCode() == candidateCode)
			{
				return theCandidate;
			}
		}
		return null;
	}

	
	//returns the collection of candidates
	public ArrayList getCandidates()
	{
		return candidates;
	}

	
	public ArrayList getStaffs() //returns a collection of staffs
	{
		return staffs;
	}


	public ArrayList getAdmins() //returns a collection of admins 
	{
		return admins;
	}


	//returns the start date
	public Date getStartDate()
	{
		return startDate;
	}

	
	//returns the end date
	public static Date getEndDate(Date start, int days) //to add n days to the start date 
	{
		GregorianCalendar cal = new GregorianCalendar(); // Create an calendar object
		cal.setTime(start); //set start date
		cal.add(Calendar.DATE,days); //days are added to this start date
		return(cal.getTime()); //start plus days are returned as end date
	}

	
	//set the start date by the Administrator
	public void saveStartDate(Date start)
	{
		try
		{
			BufferedWriter writer = new  BufferedWriter(new FileWriter("dateRange.txt")); //the start date is written in the text file.

			writer.write(getDateFormat(start));

			writer.close();
			startDate = start; //start date is saved as the current system start date 

		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	
	public void deleteStartDate() //this method deletes a start date
	{
		try
		{
			BufferedWriter writer = new  BufferedWriter(new FileWriter("dateRange.txt")); 


			writer.close();
			startDate = null; //the start date is saved as null


		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	//return a date format "dd/MM/yyyy"
	public String getDateFormat(Date date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //show a String as date format
		return dateFormat.format(date);
	}


	//returns total number of staffs in the collection
	public int getTotalVoters()
	{
		return staffs.size();
	}

	
	//every staff vote must be saved to file
	public void recordVote()
	{
		theStaff.setVoted();
		theStaff.setDate(); //this is a new method for set the voting date
		theCandidate.addVote();
		saveStaffData();//save to file
		saveCandidateData();//save to file
	}


	//writes staffs back to file
	public void saveStaffData()
	{
		try
		{
			BufferedWriter writer = new  BufferedWriter(new FileWriter("staff.txt"));
			Iterator it = staffs.iterator();
			String staffDetails;
			while(it.hasNext()) //Information from each staff is written line by line in the file
			{
				theStaff = (Staff) it.next();
				staffDetails = theStaff.getId() + "," +theStaff.getName() + "," + theStaff.getPasword() + 
						"," + theStaff.hasVoted() + "," + theStaff.getDate() + "\n";
				writer.write(staffDetails);
			}
			writer.close();

		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}


	//writes admins back to file
	public void saveAdminData() 
	{
		try
		{
			BufferedWriter writer = new  BufferedWriter(new FileWriter("admin.txt"));
			Iterator it = admins.iterator();
			String adminDetails;
			while(it.hasNext()) //Information from each admin is written line by line in the file
			{
				theAdmin = (Admin) it.next();
				adminDetails = theAdmin.getId() + "," +theAdmin.getName() + "," + theAdmin.getUser() + 
						"," +theAdmin.getPassword() + "\n";
				writer.write(adminDetails);
			}
			writer.close();

		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}


	//writes candidates back to file
	public void saveCandidateData()
	{
		try
		{
			BufferedWriter writer = new  BufferedWriter(new FileWriter("candidates.txt"));
			Iterator it = candidates.iterator();
			String candidateDetails;
			while(it.hasNext()) //Information from each candidate is written line by line in the file
			{
				theCandidate = (Candidate) it.next();
				candidateDetails = theCandidate.getCandidateCode() + "," +theCandidate.getName() + "," + theCandidate.getVotes() + "," + theCandidate.getDept() +"\n";
				writer.write(candidateDetails);
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}