package demo.stackexchange.com.stackexchangedemo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.helper.AnsListAdapter;
import demo.stackexchange.com.stackexchangedemo.helper.DialogHelper;
import demo.stackexchange.com.stackexchangedemo.helper.DownloadJasonAnsAsncTask;
import demo.stackexchange.com.stackexchangedemo.helper.DownloadJsonAsyncTask;
import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.utils.AnsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;

public class AnswerScreen extends Activity implements JsonParserCallback {
    private Context mContext;
    private String mUrl;
    DialogHelper mDialog;
    ListView mAnsList;
    TextView mQuesTitle;
    public ArrayList<AnsBean> myData;
    public AnsListAdapter mAnsAdapter;
    int mQIdRefInt;
    String qTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_answer_screen);
        mQIdRefInt = getIntent().getExtras().getInt("qId_ref");
        qTitle = getIntent().getStringExtra("qTitle_ref");
        String mQIdRef = String.valueOf(mQIdRefInt);
        mQuesTitle = (TextView) findViewById(R.id.Ques);
        mAnsList = (ListView) findViewById(R.id.AnswerList);
        mQuesTitle.setText(qTitle);
       // http://api.stackexchange.com/2.2/questions/151777/answers?order=desc&sort=activity&site=stackoverflow&filter=!9YdnSM68i
        mUrl = Constants.URL_SEARCH_Answer + mQIdRef + Constants.URL_Search_Answer_Query;
        Log.d(Constants.TAG, "Url : " + mUrl);
        new DownloadJasonAnsAsncTask(AnswerScreen.this).execute(mUrl);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setQuesListData(ArrayList<QsBean> mData) {

    }

    @Override
    public void setAnsListData(ArrayList<AnsBean> mData) {
        for (int i = 0; i < mData.size(); i++) {
            myData = mData;
        }
        if (mDialog != null) {
            mDialog.dismissDialog();
        }
        mAnsAdapter = new AnsListAdapter(this, mData);
        mAnsList.setAdapter(mAnsAdapter);
        mAnsAdapter.notifyDataSetChanged();
    }
}
