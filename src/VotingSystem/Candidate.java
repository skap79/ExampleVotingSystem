package VotingSystem;

/* author : Christian Meza */


public class Candidate
{
    int candidateCode = 999;//999 is not an eligible candidate
    String name = null;
    int votes = 0; //total votes
    String dept; // Department

    //constructor
    public Candidate(int candidateCode, String name, int votes, String dept)
    {
        this.candidateCode = candidateCode;
        this.name = name;
        this.votes = votes;
        this.dept = dept;
    }

    public int getCandidateCode ()
    {
        return candidateCode;
    }

    public String getName()
    {
        return  name;
    }

    public int getVotes()
    {
        return  votes;
    }

    public void addVote()
    {
        votes++;
    }

    public void setCandidateCode(int candidateCode)
    {
       this.candidateCode = candidateCode;
    }
    
    public void setName(String name)
    {
        this.name=  name;
    }

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
    
}