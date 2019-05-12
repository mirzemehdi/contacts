package Model;

public class Contact {

    private int id;
    private String username;
    private String password;
    private String addedDate;

    public Contact() {
    }

    public Contact(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public Contact(int id, String username, String password, String addedDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.addedDate = addedDate;
    }

    public Contact(String username, String password, String addedDate) {
        this.username = username;
        this.password = password;
        this.addedDate = addedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }



}
