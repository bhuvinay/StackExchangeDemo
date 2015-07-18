package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

public class AnswerQsBean extends QsBean {

    private int question_id;

    public AnswerQsBean() {
        super();
        this.question_id = -1;
    }

    public AnswerQsBean(int id, String title, int score, String owner, int question_id) {
        super(id, title, score, owner);
        this.question_id = question_id;
    }

    //Getter and Setter methods ..

    public int getQuestionId() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

}
