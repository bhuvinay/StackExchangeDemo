package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.utils.AnsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

public class DownloadJsonAnsAsyncTask extends AsyncTask<String, Void, ArrayList<AnsBean>> {
    private Context mContext;
    private String mUrl = null;
    private String mResult = null;
    DialogHelper myDialog;
    private boolean isConnected;
    private JsonParserCallback mListener = null;
    OfflineDataFetcher offLineDbData;

    public DownloadJsonAnsAsyncTask(Context context) {
        mContext = context;
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
    protected ArrayList<AnsBean> doInBackground(String... params) {

        ArrayList<AnsBean> ansItems = new ArrayList<>();
        offLineDbData = new OfflineDataFetcher(mContext);
        //First read the Json string , convert it into bean items..
        if (isConnected) {
            String response = Utility.GET(params[0]);
            Log.d(Constants.TAG, "response ans: " + response);
            JsonOnlineParser jsonOnlineParser = new JsonOnlineParser(response);
            ansItems = jsonOnlineParser.getAnswerBeanList();
            offLineDbData.insertAnswerBeanItems(ansItems);

        }else {
            ansItems = offLineDbData.getDBAnswerBeanList(params[1]);
        }
        return ansItems;
    }

    @Override
    protected void onPostExecute(ArrayList<AnsBean> items) {
        if (myDialog != null)
            myDialog.dismissDialog();
        if (items.size() > 0) {
            if (mListener != null)
                mListener.setAnsListData(items);
        } else {
            Toast.makeText(mContext, "Data is null", Toast.LENGTH_SHORT).show();
        }
    }

}
