package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 19-07-2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {
    // Database Name
    static final String DATABASE_NAME = "querydb.db";
    // Database Version
    static final int DATABASE_VERSION = 1;

    static DataBaseHelper mInstance;
    // Table Names
    public static final String TABLE_ques = "ques";
    public static final String TABLE_ans = "ans";
    public static final String TABLE_query = "query";
    // SQL Statement to create a new table.
    static final String DATABASE_CREATE_ques = "create table " + TABLE_ques +
            "( " + "Q_ID" + " integer primary key," + "Q_TITLE  text, SCORE integer, Q_OWNER text); ";
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

}

