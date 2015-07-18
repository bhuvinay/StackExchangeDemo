package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

public class AnsBean {
    int id;
    String title;
    int votes;
    String owner;

    public AnsBean() {
        id = -1;
        title = "";
        votes = 0;
        owner = "";
    }

    public AnsBean(int id, String title, int score, String owner) {
        this.id = id;
        this.title = title;
        this.votes = score;
        this.owner = owner;
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

    //Setter methods ..
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public void setScore(int score) {
        this.votes = score;
    }

    public void setOwner(String s) {
        this.owner = s;
    }


}
