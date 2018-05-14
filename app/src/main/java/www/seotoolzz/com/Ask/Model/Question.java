package www.seotoolzz.com.Ask.Model;

/**
 * Created by ngant on 4/15/2018.
 */

public class Question {
    private  int id;
    private String title;
    private String username;
    private int vote;
    private String date;
    private int solve;

    public int isSolve() {
        return solve;
    }

    public void setSolve(int solve) {
        this.solve = solve;
    }

    public Question(int id, String title, String userName, int vote, String date, int solve) {
        this.id = id;
        this.title = title;
        this.username = userName;
        this.vote = vote;
        this.date = date;
        this.solve = solve;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


