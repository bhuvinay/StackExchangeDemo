package demo.stackexchange.com.stackexchangedemo.ui;

/**
 * Created by vinay.pratap on 05-08-2015.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import demo.stackexchange.com.stackexchangedemo.R;

public class SingleAnsView extends AppCompatActivity {

    private String mWebData;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ans_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DDF68A1F")));
        mWebData = getIntent().getStringExtra("webData_ref");
        mWebView = (WebView) findViewById(R.id.myweb_view);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        // Enable pinch to zoom without the zoom buttons
        mWebView.getSettings().setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB above
            mWebView.getSettings().setDisplayZoomControls(false);
        }
        mWebView.loadDataWithBaseURL("", mWebData, "text/html", "UTF-8", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_ans_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
