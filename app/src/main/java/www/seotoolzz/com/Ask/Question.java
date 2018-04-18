package www.seotoolzz.com.Ask;

/**
 * Created by ngant on 4/15/2018.
 */

public class Question {
    private  int id;
    private String title;
    private String content;
    private String tag;
    private String userName;
    private String voteNumber;

    public Question(int id, String title, String content, String tag, String userName, String voteNumber) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.userName = userName;
        this.voteNumber = voteNumber;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
}


