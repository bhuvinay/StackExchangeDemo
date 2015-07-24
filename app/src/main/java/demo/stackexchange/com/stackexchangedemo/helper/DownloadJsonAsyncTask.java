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
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

public class DownloadJsonAsyncTask extends AsyncTask<String, Void, ArrayList<QsBean>> {

    private Context mContext;
    private boolean isConnected;
    DialogHelper myDialog;
    private JsonParserCallback mListener = null;
    DataBaseHelper dbHelp;
    OfflineDataFetcher offLineDbData;

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
        if (!Utility.isConnected(mContext)) {
            isConnected = false;
            Toast.makeText(mContext, "No network connection, Searching offline", Toast.LENGTH_SHORT).show();
        } else {
            isConnected = true;
        }

    }

    @Override
    protected ArrayList<QsBean> doInBackground(String... params) {

        ArrayList<QsBean> items = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        offLineDbData = new OfflineDataFetcher(mContext);
        //First read the Json string , convert it into bean items..
        if (isConnected) {
            //Fetch from Network
            String URL = params[0] + params[1];   // params[0]: URL, params[1]: Search text
            Log.d(Constants.TAG, "params[0]" + params[0] + "params[1]" + params[1]);
            String response = Utility.GET(URL);
            Log.d(Constants.TAG, "response" + response);
            JsonOnlineParser jsonOnlineParser = new JsonOnlineParser(response);
            items = jsonOnlineParser.getQuestionBeanList();
            offLineDbData.insertQuestionBeanItems(items, params[1]);

        } else {
            //look in DB for saved data for Search Text{prams[1]}
            items = offLineDbData.getDBQuestionBeanList(params[1]);
        }
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

}
