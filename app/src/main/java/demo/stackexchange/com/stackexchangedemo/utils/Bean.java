package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

public class Bean {
    int id;
    String title;
    int votes;
    String owner;
    int qId;
    boolean isAnswered;

    public Bean() {
        id = -1;
        title = "";
        votes = 0;
        owner = "";
        qId = -1;
        isAnswered = false;
    }

    public Bean(int id, String title, int score, String owner, int qId, boolean isAnswered) {
        this.id = id;
        this.title = title;
        this.votes = score;
        this.owner = owner;
        this.qId = qId;
        this.isAnswered = isAnswered;
    }

    //Getter methods ..
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return votes;
    }

    public String getOwner() {
        return owner;
    }
    public int getQuestionId() { return qId; }

    public int isAnswered() {
        return (isAnswered ? 1 : 0);
    }


    //Setter methods ..
    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion_id(int qId) {
        this.qId = qId;
    }

    public void setTitle(String title) { this.title = title; }

    public void setScore(int score) {
        this.votes = score;
    }

    public void setOwner(String s) {
        this.owner = s;
    }


}
