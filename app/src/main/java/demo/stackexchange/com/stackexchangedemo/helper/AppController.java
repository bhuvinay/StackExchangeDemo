package demo.stackexchange.com.stackexchangedemo.helper;

import android.app.Application;

/**
 * Created by vinay.pratap on 22-07-2015.
 */

public class AppController extends Application {
    static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }

        return mInstance;
    }
}
