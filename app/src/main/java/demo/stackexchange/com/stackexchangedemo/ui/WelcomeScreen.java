package demo.stackexchange.com.stackexchangedemo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.helper.QuesListAdapter;
import demo.stackexchange.com.stackexchangedemo.intface.JsonParserCallback;
import demo.stackexchange.com.stackexchangedemo.intface.OnItemClickCallbackInterface;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.helper.DialogHelper;
import demo.stackexchange.com.stackexchangedemo.helper.DownloadJsonAsyncTask;
import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;


public class WelcomeScreen extends Activity implements View.OnClickListener, OnItemClickCallbackInterface, JsonParserCallback {
    Button mButton;
    EditText mEditText;
    private Context mContext;
    private String url;
    DialogHelper myDialog;
    ListView mList;
    public ArrayList<QsBean> myData;
    public QuesListAdapter mQAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mList = (ListView) findViewById(R.id.listView1);
        mButton = (Button) findViewById(R.id.search_button);
        mButton.setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.search_edit_text);
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.search_button:
                //Get the text from search view and query the Json ..
                String text = mEditText.getText().toString().trim();
                text = text.replace(" ", "%20");
                if (text == null || text.isEmpty())
                    Toast.makeText(this, R.string.search_query_empty, Toast.LENGTH_SHORT).show();
                 else if(!Utility.isConnected(mContext)) {
                    Toast.makeText(mContext,"No network connection",Toast.LENGTH_SHORT).show();
                } else {
                    url = Constants.URL_Search_Question_Query + text;
                    Log.d(Constants.TAG, "Url : " + url);
                    new DownloadJsonAsyncTask(WelcomeScreen.this).execute(url);

                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    public void setQuesListData(ArrayList<QsBean> mData) {
        for(int i=0;i<mData.size();i++){
           myData=mData;
        }
        if(myDialog != null){
            myDialog.dismissDialog();
        }
        mQAdapter = new QuesListAdapter(this,mData);
        mList.setAdapter(mQAdapter);
        mQAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListItem(int position) {

    }

}