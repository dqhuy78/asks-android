package www.seotoolzz.com.Ask.model;

/**
 * Created by ngant on 5/2/2018.
 */

public class Answer {
    private  int id;
    private String content;
    private String userName;
    private String voteNumber;
    private String time;

    public Answer(int id, String content, String userName, String voteNumber, String time) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.voteNumber = voteNumber;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(String voteNumber) {
        this.voteNumber = voteNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
