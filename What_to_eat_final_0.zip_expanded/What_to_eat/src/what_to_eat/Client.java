package what_to_eat;

public class Client {
    private String id;
    private String pw;
    private String name;
    //private email, getter, setter Ãß°¡_±è¼ºÅ¹
    private String email;
    
    public Client(String id, String pw, String name, String email) {
	this.id = id;
	this.pw = pw;
	this.name = name;
	this.email = email;
	
    }

    public Client() {

    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getPw() {
	return pw;
    }

    public void setPw(String pw) {
	this.pw = pw;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return email;
    }

    public String toString() {
	return "id : " + id;
    }
}