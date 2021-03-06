package demo.stackexchange.com.stackexchangedemo.ui;

/**
 * Created by vinay.pratap on 26-07-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.helper.AnsListAdapter;
import demo.stackexchange.com.stackexchangedemo.helper.DialogHelper;
import demo.stackexchange.com.stackexchangedemo.helper.DownloadJsonAsyncTask;
import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.intface.OnItemClickCallbackInterface;
import demo.stackexchange.com.stackexchangedemo.utils.Bean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;


public class AnswerScreen extends Activity implements JsonParserCallback, OnItemClickCallbackInterface {
    private String mUrl;
    private DialogHelper mDialog;
    private ListView mAnsList;
    private TextView mQuesTitle;
    private AnsListAdapter mAnsAdapter;
    private int mQIdRefInt;
    private String qTitle;

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
        new DownloadJsonAsyncTask(AnswerScreen.this, Constants.ANSWER).execute(mUrl, mQIdRef);
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
    public void setListData(ArrayList<Bean> mData) {
        if (mDialog != null) {
            mDialog.dismissDialog();
        }
        mAnsAdapter = new AnsListAdapter(this, mData);
        mAnsList.setAdapter(mAnsAdapter);
        mAnsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListItem(int position, int id, String webData) {

        Intent intent = new Intent(this, SingleAnsView.class);
        intent.putExtra("webData_ref", webData);
        startActivity(intent);

    }
}
