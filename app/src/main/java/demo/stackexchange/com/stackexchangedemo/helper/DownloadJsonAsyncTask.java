package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

public class DownloadJsonAsyncTask extends AsyncTask<String, Void, ArrayList<QsBean>> {

    private Context mContext;
    //QuestionListAdapter adapter;
    private String mUrl = null;
    private String mResult = null;
    DialogHelper myDialog;
    private JsonParserCallback mListener = null;
    private String TAG = "DownloadJSONString";
    DataBaseHelper dbHelp;

    public DownloadJsonAsyncTask(Context context) {
        mContext = context;
        dbHelp = DataBaseHelper.getInstance(mContext);
        mListener = (JsonParserCallback) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myDialog = new DialogHelper(mContext, 1);
        if (myDialog != null)
            myDialog.showDialog();

    }

    @Override
    protected ArrayList<QsBean> doInBackground(String... params) {

        ArrayList<QsBean> items = new ArrayList<>();
        StringBuffer sb = new StringBuffer();;
        //First read the Json string , convert it into bean items..
        String URL = params[0] + params[1];
        Log.d(Constants.TAG,"params[0]" + params[0] + "params[1]" + params[1]);
        String response = Utility.GET(URL);
       // String response = Utility.GET(params[0]);
        Log.d(Constants.TAG, "response" + response);
        JsonOnlineParser jsonOnlineParser = new JsonOnlineParser(response);
        items = jsonOnlineParser.getQuestionBeanList();

        for(QsBean qB : items){
            insertQuesData(qB);
            sb.append(qB.getId());
            sb.append(";");
            Log.d(Constants.TAG,"qB id " + qB.getId());
        }
        String qList = sb.toString();
        Log.d(Constants.TAG,"qList " + qList);
        insertQueryQlist(params[1], qList);
        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<QsBean> items) {
        if (myDialog != null)
            myDialog.dismissDialog();
        if (items.size() > 0) {
            if (mListener != null)
                mListener.setQuesListData(items);
        } else {
            Toast.makeText(mContext, "Data is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertQueryQlist(String sQues, String qIdList) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("SEARCH_STR", sQues);
        values.put("Q_LIST", qIdList);

        //insert ques text map to ques ids
        db.insert(DataBaseHelper.TABLE_query, null, values);
    }

    public void insertQuesData(QsBean mQdata) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Q_ID", mQdata.getId());
        values.put("Q_OWNER", mQdata.getOwner());
        values.put("Q_TITLE", mQdata.getTitle());
        values.put("SCORE", mQdata.getScore());

        // insert row
        db.insert(DataBaseHelper.TABLE_ques, null, values);
    }
}
