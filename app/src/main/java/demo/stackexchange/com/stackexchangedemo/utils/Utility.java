package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class Utility {

    private static String mErrorMessage;

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr

                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            StatusLine mStatusLine = httpResponse.getStatusLine();
            int statusCode = mStatusLine.getStatusCode();
            Log.d(Constants.TAG, "statuscode : " + statusCode);

            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    inputStream = entity.getContent();
                    Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
                    if (contentEncoding != null && contentEncoding.getValue().contains("gzip")) {
                        Log.d(Constants.TAG, "GZIPInputStream");
                        inputStream = new GZIPInputStream(inputStream);

                        result = convertInputStreamToString(inputStream);
                    }
                } else
                    mErrorMessage = "Error Message Entity is null";
            } else {
                mErrorMessage = "Error Message : " + statusCode;
            }

        } catch (Exception e) {
            Log.d(Constants.TAG, e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        if (inputStream == null) {
            return "InputStream Error";
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        }

    }

    public static void hideKeyboard(View view,Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
