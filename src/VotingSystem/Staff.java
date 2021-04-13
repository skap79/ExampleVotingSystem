package VotingSystem;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/* author : Christian Meza */


public class Staff
{
    private int id;
    private String name;
    private int voted; //has the staff voted
    private String password;
    private String date;

    public Staff(int id, String name, String password, int voted, String date)
    {
            this.id = id;
            this.name = name;
            this.password= password;
            this.voted = voted;
            this.date = date;
    }

    public void setId(int id)
    {
       this.id = id;
    }

    public void setName(String name)
    {
            this.name = name;
    }

    public void setVoted()
    {
            this.voted = 1;    
    }
    
    public void setDate() //setDate() method is created to set the voting date based on the date timestamp
    {
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	Date dateVoted = new Date(timestamp.getTime());
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //show a String as date format
        //this.date = String.valueOf(timestamp); //date is converted into string to be saved in the text file
    	this.date = dateFormat.format(dateVoted);
    }
    
    public void setPassword(String password) //******************************************************
    {
            this.password = password;
    }
    
    
    public String getDate() //getDate() method is created to get the voting date
    {
       return date;
    }

        
    public int getId()
    {
       return id;
    }

    public String getName()
    {
            return name;
    }

    public int hasVoted()
    {
            return voted;
    }

    public String getPasword()
    {
            return password;
    }
}
