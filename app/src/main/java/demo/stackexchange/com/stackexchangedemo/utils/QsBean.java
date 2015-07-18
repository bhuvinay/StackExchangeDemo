package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

public class QsBean {

    int id;
    String title;
    int score;
    String owner;

    public QsBean() {
        id = -1;
        title = "";
        score = 0;
        owner = "";
    }

    public QsBean(int id, String title, int score, String owner) {
        this.id = id;
        this.title = title;
        this.score = score;
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
        return score;
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
        this.score = score;
    }

    public void setOwner(String s) {
        this.owner = s;
    }


}
