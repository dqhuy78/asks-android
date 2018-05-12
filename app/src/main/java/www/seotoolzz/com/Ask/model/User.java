package www.seotoolzz.com.Ask.model;

/**
 * Created by ngant on 5/9/2018.
 */

public class User {
    private int id;
    private String NameUser;
    private String passWD;
    private String email;

    public User(int id, String nameUser, String passWD, String email) {
        this.id = id;
        NameUser = nameUser;
        this.passWD = passWD;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getPassWD() {
        return passWD;
    }

    public void setPassWD(String passWD) {
        this.passWD = passWD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
