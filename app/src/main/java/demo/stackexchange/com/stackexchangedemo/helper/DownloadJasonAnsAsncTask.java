package demo.stackexchange.com.stackexchangedemo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.utils.AnsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

/**
 * Created by Vinay on 18-07-2015.
 */
public class DownloadJasonAnsAsncTask extends AsyncTask<String, Void, ArrayList<AnsBean>> {
    private Context mContext;
    //QuestionListAdapter adapter;
    private String mUrl = null;
    private String mResult = null;
    DialogHelper myDialog;
    private JsonParserCallback mListener = null;


    public DownloadJasonAnsAsncTask(Context context) {
        mContext = context;
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
    protected ArrayList<AnsBean> doInBackground(String... params) {

        ArrayList<AnsBean> amsItems = new ArrayList<>();

        //First read the Json string , convert it into bean items..
        String response = Utility.GET(params[0]);
        Log.d(Constants.TAG, "response ans: " + response);
        JsonOnlineParser jsonOnlineParser = new JsonOnlineParser(response);
        amsItems = jsonOnlineParser.getAnswerBeanList();

        return amsItems;
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
