package demo.stackexchange.com.stackexchangedemo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.helper.QuesListAdapter;
import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.intface.OnItemClickCallbackInterface;
import demo.stackexchange.com.stackexchangedemo.utils.Bean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.helper.DialogHelper;
import demo.stackexchange.com.stackexchangedemo.helper.DownloadJsonAsyncTask;
import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;


public class WelcomeScreen extends Activity implements OnItemClickCallbackInterface, JsonParserCallback {

    private Context mContext;
    private String url;
    private DialogHelper myDialog;
    private ListView mList;
    private String qSearchtext;
    private QuesListAdapter mQAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mList = (ListView) findViewById(R.id.listView1);
        mContext = this;
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        //get search view query data
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            qSearchtext = query.trim();

            //use the query to search your data somehow
        } else {
            qSearchtext = getIntent().getStringExtra("searchquery");
        }
        mList.setAdapter(null);
        if (qSearchtext == null || qSearchtext.isEmpty()) {
            Toast.makeText(this, R.string.search_query_empty, Toast.LENGTH_SHORT).show();
        } else {
            url = Constants.URL_Search_Question_Query; //+ text;
            Log.d(Constants.TAG, "Url : " + url);
            if (!Utility.isConnected(mContext))
                alertView("No Network Connection. Results will be populated from Database, if previously searched");
            else
                new DownloadJsonAsyncTask(WelcomeScreen.this, Constants.QUESTION).execute(url, qSearchtext);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

        dialog.setTitle("Offline search")
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                        finish();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        new DownloadJsonAsyncTask(WelcomeScreen.this, Constants.QUESTION).execute(url, qSearchtext);
                    }
                }).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (myDialog != null) {
            myDialog.dismissDialog();
        }

    }


    @Override
    public void setListData(ArrayList<Bean> mData) {
        if (myDialog != null) {
            myDialog.dismissDialog();
        }
        mQAdapter = new QuesListAdapter(this, mData);
        mList.setAdapter(mQAdapter);
        mQAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListItem(int position, int qId, String qTitle) {

        Intent intent = new Intent(this, AnswerScreen.class);
        intent.putExtra("qId_ref", qId);
        intent.putExtra("qTitle_ref", qTitle);
        startActivity(intent);

    }

}
