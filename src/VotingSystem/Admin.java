package VotingSystem;

/* author : Christian Meza */


public class Admin 
{
	private int id;
    private String name;
    private String user; //username for the admin
    private String password;
   
    public Admin(int id, String name, String user, String password) //a constructor is created for Administrator 
    {
            this.id = id;
            this.name = name;
            this.user= user;
            this.password = password;
    }
    
    public void setName(String name)
    {
            this.name = name;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

    
    
    
}
