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
    //QuestionListAdapter adapter;
    private String mUrl = null;
    private String mResult = null;
    DialogHelper myDialog;
    private JsonParserCallback mListener = null;
    private String TAG = "DownloadJSONString";


    public DownloadJsonAsyncTask(Context context) {
        mContext = context;
        //mUrl = URL;
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

        //First read the Json string , convert it into bean items..
        String response = Utility.GET(params[0]);
        Log.d(Constants.TAG, response);
        JsonOnlineParser jsonOnlineParser = new JsonOnlineParser(response);
        items = jsonOnlineParser.getQuestionBeanList();

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
