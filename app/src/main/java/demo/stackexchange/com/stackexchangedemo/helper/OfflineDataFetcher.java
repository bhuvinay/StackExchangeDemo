package demo.stackexchange.com.stackexchangedemo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.utils.AnsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;

/**
 * Created by vinay.pratap on 23-07-2015.
 */
public class OfflineDataFetcher {
    DataBaseHelper dbHelp;
    Context mContext;
    SQLiteDatabase db;

    public OfflineDataFetcher(Context context) {
        mContext = context;
        dbHelp = DataBaseHelper.getInstance(mContext);

    }

    public String getQIdsList(String qSearchtext) {
        Cursor cursor = db.query(DataBaseHelper.TABLE_query,
                new String[]{"Q_LIST"},
                "SEARCH_STR=?",
                new String[]{qSearchtext},
                null,
                null,
                null);

        String qidies = "";
        while (cursor.moveToNext()) {
            qidies = cursor.getString(cursor.getColumnIndex("Q_LIST"));
        }
        return qidies;

    }

    public ArrayList<QsBean> getDBQuestionBeanList(String qSearch) {

        ArrayList<QsBean> items_qb = new ArrayList<>();
        db = dbHelp.getReadableDatabase();
        try {
            db.beginTransaction();
            String qids = getQIdsList(qSearch);
            String[] qArray = qids.split(":");

            for (int i = 0; i < qArray.length - 1; i++) {

                Cursor item_cursor = db.query(DataBaseHelper.TABLE_ques,
                        new String[]{"Q_OWNER", "Q_ID", "Q_TITLE", "SCORE"},
                        "Q_ID=?",
                        new String[]{qArray[i]},
                        null,
                        null,
                        null);

                while (item_cursor.moveToNext()) {
                    int q_id = item_cursor.getInt(item_cursor.getColumnIndex("Q_ID"));
                    String q_owner = item_cursor.getString(item_cursor.getColumnIndex("Q_OWNER"));
                    int q_score = item_cursor.getInt(item_cursor.getColumnIndex("SCORE"));
                    String q_title = item_cursor.getString(item_cursor.getColumnIndex("Q_TITLE"));
                    Log.d(Constants.TAG, "q_id : " + q_id + " q_owner :" + q_owner + "q_score : " + q_score + "q_title : " + q_title);
                    QsBean qsBean = new QsBean(q_id, q_title, q_score, q_owner);
                    items_qb.add(qsBean);
                }
                item_cursor.close();


            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(Constants.TAG, e.toString());
        } finally {
            db.endTransaction();
            db.close();
        }
        Log.d(Constants.TAG, "items.size : " + items_qb.size());
        return items_qb;
    }


    public ArrayList<AnsBean> getDBAnswerBeanList(String qId) {

        ArrayList<AnsBean> items_ab = new ArrayList<>();
        db = dbHelp.getReadableDatabase();
        try {
            db.beginTransaction();
            // int qid = Integer.parseInt(qId);
            Cursor item_cursor = db.query(DataBaseHelper.TABLE_ans,
                    new String[]{"A_OWNER", "A_ID", "A_BODY", "VOTES", "Q_ID"},
                    "Q_ID=?",
                    new String[]{qId},
                    null,
                    null,
                    null);

            while (item_cursor.moveToNext()) {
                int answer_id = item_cursor.getInt(item_cursor.getColumnIndex("A_ID"));
                String answer_owner = item_cursor.getString(item_cursor.getColumnIndex("A_OWNER"));
                int answer_votes = item_cursor.getInt(item_cursor.getColumnIndex("VOTES"));
                String answer_body = item_cursor.getString(item_cursor.getColumnIndex("A_BODY"));
                int ques_id = item_cursor.getInt(item_cursor.getColumnIndex("A_ID"));
                Log.d(Constants.TAG, "answer_id : " + answer_id + " answer_owner :" + answer_owner + "answer_votes : " + answer_votes + "answer_body : " + answer_body + "ques_id : " + ques_id);
                AnsBean asBean = new AnsBean(answer_id, answer_body, answer_votes, answer_owner, ques_id);
                items_ab.add(asBean);
            }
            item_cursor.close();


            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(Constants.TAG, e.toString());
        } finally {
            db.endTransaction();
            db.close();
        }
        Log.d(Constants.TAG, "Ans_items.size : " + items_ab.size());
        return items_ab;
    }

    public void insertQueryQlist(String sQues, String qIdList) {
        ContentValues values = new ContentValues();
        values.put("SEARCH_STR", sQues);
        values.put("Q_LIST", qIdList);

        //insert ques text map to ques ids
        db.insert(DataBaseHelper.TABLE_query, null, values);
    }

    public void insertQuesData(QsBean mQdata) {

        ContentValues values = new ContentValues();
        values.put("Q_ID", mQdata.getId());
        values.put("Q_OWNER", mQdata.getOwner());
        values.put("Q_TITLE", mQdata.getTitle());
        values.put("SCORE", mQdata.getScore());

        // insert row
        db.insert(DataBaseHelper.TABLE_ques, null, values);
    }

    public void insertAnsData(AnsBean mAnsdata) {

        ContentValues values = new ContentValues();
        values.put("A_ID", mAnsdata.getId());
        values.put("A_OWNER", mAnsdata.getOwner());
        values.put("A_BODY", mAnsdata.getTitle());
        values.put("VOTES", mAnsdata.getScore());
        values.put("Q_ID", mAnsdata.getQuestionId());

        // insert row
        db.insert(DataBaseHelper.TABLE_ans, null, values);
    }

    public void insertQuestionBeanItems(ArrayList<QsBean> items, String search_query) {

        db = dbHelp.getWritableDatabase();
        db.beginTransaction();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (QsBean qB : items) {
                insertQuesData(qB);
                stringBuilder.append(qB.getId());
                stringBuilder.append(":");
                Log.d(Constants.TAG, "qB id " + qB.getId());
            }
            String qList = stringBuilder.toString();
            Log.d(Constants.TAG, "qList " + qList);
            insertQueryQlist(search_query, qList);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void insertAnswerBeanItems(ArrayList<AnsBean> items) {

        db = dbHelp.getWritableDatabase();
        db.beginTransaction();
        try {
            for (AnsBean aB : items) {
                insertAnsData(aB);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
