package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 19-07-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.utils.Bean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;



public class DataBaseHelper extends SQLiteOpenHelper {
    // Database Name
    static final String DATABASE_NAME = "querydb.db";
    // Database Version
    static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    static DataBaseHelper mInstance;
    // Table Names
    public static final String TABLE_ques = "ques";
    public static final String TABLE_ans = "ans";
    public static final String TABLE_query = "query";
    // SQL Statement to create a new table.
    static final String DATABASE_CREATE_ques = "create table " + TABLE_ques +
            "( " + "Q_ID" + " integer primary key," + "Q_TITLE  text, SCORE integer, Q_OWNER text, Q_ANSWERED integer); ";
    static final String DATABASE_CREATE_ans = "create table " + TABLE_ans +
            "( " + "A_ID" + " integer primary key," + "A_BODY  text, A_OWNER text,  VOTES integer, Q_ID integer); ";
    static final String DATABASE_CREATE_query = "create table " + TABLE_query +
            "( " + "ID" + " integer primary key autoincrement," + "SEARCH_STR  text, Q_LIST text); ";

    private DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public static DataBaseHelper getInstance(Context c) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(c, DATABASE_NAME, null, DATABASE_VERSION);

        }
        return mInstance;
    }

    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(DATABASE_CREATE_ques);
        _db.execSQL(DATABASE_CREATE_ans);
        _db.execSQL(DATABASE_CREATE_query);


    }

    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");


        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_ques);
        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_ans);
        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_query);
        // Create a new one.
        onCreate(_db);
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

    public ArrayList<String> getDBSearchQueryList() {

        ArrayList<String> searchList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_query,
                new String[]{"SEARCH_STR"},
                null,
                null,
                null,
                null,
                null);

        String queryText = "";
        while (cursor.moveToNext()) {
            queryText = cursor.getString(cursor.getColumnIndex("SEARCH_STR"));
            searchList.add(queryText);
        }
        return searchList;
    }



    public ArrayList<Bean> getDBQuestionBeanList(String qSearch) {

        ArrayList<Bean> items_qb = new ArrayList<>();
        db = getReadableDatabase();
        try {
            db.beginTransaction();
            String qids = getQIdsList(qSearch);
            String[] qArray = qids.split(":");

            for (int i = 0; i < qArray.length - 1; i++) {

                Cursor item_cursor = db.query(DataBaseHelper.TABLE_ques,
                        new String[]{"Q_OWNER", "Q_ID", "Q_TITLE", "SCORE", "Q_ANSWERED"},
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
                    int q_answered  =  item_cursor.getInt(item_cursor.getColumnIndex("Q_ANSWERED"));
                    boolean is_Answered = (q_answered == 1) ? true : false;
                    Log.d(Constants.TAG, "q_id : " + q_id + " q_owner :" + q_owner + "q_score : " + q_score + "q_title : " + q_title);
                    Bean qsBean = new Bean(q_id, q_title, q_score, q_owner,0, is_Answered);
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


    public ArrayList<Bean> getDBAnswerBeanList(String qId) {

        ArrayList<Bean> items_ab = new ArrayList<>();
        db = getReadableDatabase();
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
                Bean asBean = new Bean(answer_id, answer_body, answer_votes, answer_owner, ques_id, false);
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

    //updadte query data DB
    public void updateQueryQlist(String sQues, String qIdList) {
        ContentValues updateValues = new ContentValues();
        updateValues.put("SEARCH_STR", sQues);
        updateValues.put("Q_LIST", qIdList);

        //insert ques text map to ques ids
        db.update(DataBaseHelper.TABLE_query, updateValues, "SEARCH_STR=" + sQues, null);
    }

    public void insertQuesData(Bean mQdata) {

        ContentValues values = new ContentValues();
        values.put("Q_ID", mQdata.getId());
        values.put("Q_OWNER", mQdata.getOwner());
        values.put("Q_TITLE", mQdata.getTitle());
        values.put("SCORE", mQdata.getScore());
        values.put("Q_ANSWERED", mQdata.isAnswered());

        // insert row
        db.insert(DataBaseHelper.TABLE_ques, null, values);
    }

    public void insertAnsData(Bean mAnsdata) {

        ContentValues values = new ContentValues();
        values.put("A_ID", mAnsdata.getId());
        values.put("A_OWNER", mAnsdata.getOwner());
        values.put("A_BODY", mAnsdata.getTitle());
        values.put("VOTES", mAnsdata.getScore());
        values.put("Q_ID", mAnsdata.getQuestionId());

        // insert row
        db.insert(DataBaseHelper.TABLE_ans, null, values);
    }

    public void insertQuestionBeanItems(ArrayList<Bean> items, String search_query) {

        db = getWritableDatabase();
        db.beginTransaction();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (Bean qB : items) {
                insertQuesData(qB);
                stringBuilder.append(qB.getId());
                stringBuilder.append(":");
                Log.d(Constants.TAG, "qB id " + qB.getId());
            }
            String qList = stringBuilder.toString();
            Log.d(Constants.TAG, "qList " + qList);
            //check DB for already present search_query
            if(!getDBSearchQueryList().contains(search_query)) {
                //insert if new seach item
                insertQueryQlist(search_query, qList);
            }else {
                //update if already present
                updateQueryQlist(search_query,qList);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void insertAnswerBeanItems(ArrayList<Bean> items) {

        db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Bean aB : items) {
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

