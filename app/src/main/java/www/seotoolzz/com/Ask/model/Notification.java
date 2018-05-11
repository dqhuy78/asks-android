package www.seotoolzz.com.Ask.model;

/**
 * Created by ngant on 4/18/2018.
 */

public class Notification {
    private int id;
    private String NameUser;
    private String infoAction;
    private String infoTime;

    public Notification(int id, String nameUser, String infoAction, String infoTime) {
        this.id = id;
        NameUser = nameUser;
        this.infoAction = infoAction;
        this.infoTime = infoTime;
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

    public String getInfoAction() {
        return infoAction;
    }

    public void setInfoAction(String infoAction) {
        this.infoAction = infoAction;
    }

    public String getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(String infoTime) {
        this.infoTime = infoTime;
    }
}
