package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.ui.AnswerScreen;
import demo.stackexchange.com.stackexchangedemo.ui.WelcomeScreen;
import demo.stackexchange.com.stackexchangedemo.utils.Bean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

public class DownloadJsonAsyncTask extends AsyncTask<String, Void, ArrayList<Bean>> {

    private Context mContext;
    private boolean isConnected;
    private DialogHelper myDialog;
    private JsonParserCallback mListener = null;
    private DataBaseHelper dbHelp;
    private JsonOnlineParser jsonOnlineParser;
    private int mType;


    public DownloadJsonAsyncTask(Context context, int type) {
        mContext = context;
        this.mType = type;
        dbHelp = DataBaseHelper.getInstance(mContext);
        jsonOnlineParser = JsonOnlineParser.getInstance();
        mListener = (JsonParserCallback) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myDialog = new DialogHelper(mContext, 1);
        if (myDialog != null)
            myDialog.showDialog();
        if (!Utility.isConnected(mContext)) {
            isConnected = false;
            Toast.makeText(mContext, "No network connection, Searching offline", Toast.LENGTH_SHORT).show();
        } else {
            isConnected = true;
        }

    }

    @Override
    protected ArrayList<Bean> doInBackground(String... params) {

        ArrayList<Bean> items = new ArrayList<>();
        String URL;
        //First read the Json string , convert it into bean items..
        if (isConnected) {
            switch (mType) {
                case Constants.QUESTION:
                    String paramUrl = params[1].replace(" ", "%20");
                    URL = params[0] + paramUrl;
                    Log.d(Constants.TAG, "params[0]" + params[0] + "params[1]" + params[1]);
                    items = jsonOnlineParser.getQuestionBeanList(Utility.GET(URL));
                    dbHelp.insertQuestionBeanItems(items, params[1]);
                    break;
                case Constants.ANSWER:
                    URL = params[0];
                    Log.d(Constants.TAG, "params[0]" + params[0] + "params[1]" + params[1]);
                    items = jsonOnlineParser.getAnswerBeanList(Utility.GET(URL));
                    dbHelp.insertAnswerBeanItems(items);
                    break;
            }

        } else {
            //look in DB for saved data for Search Text{prams[1]}
            switch (mType) {
                case Constants.QUESTION:
                    items = dbHelp.getDBQuestionBeanList(params[1]);
                    break;
                case Constants.ANSWER:
                    items = dbHelp.getDBAnswerBeanList(params[1]);
                    break;
            }

        }
        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<Bean> items) {
        if (myDialog != null)
            myDialog.dismissDialog();
        if (items.size() > 0) {
            if (mListener != null)
                mListener.setListData(items);
        } else {
            // if data null finish current activity
            if (mContext instanceof WelcomeScreen)
                ((WelcomeScreen) mContext).finish();
            if (mContext instanceof AnswerScreen)
                ((AnswerScreen) mContext).finish();

            Toast.makeText(mContext, "Data is null", Toast.LENGTH_SHORT).show();
        }
    }

}
