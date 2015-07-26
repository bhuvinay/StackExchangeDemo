package demo.stackexchange.com.stackexchangedemo.ui;

/**
 * Created by vinay.pratap on 26-07-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.helper.DataBaseHelper;

public class SplashActivity extends Activity {

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);

        startSplashTimer();
    }

    private void startSplashTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SearchResultActivity.class);

                startActivity(i);
                finish();
            }
        };
        timer = new Timer();
        timer.schedule(task, 1500);
    }


}
