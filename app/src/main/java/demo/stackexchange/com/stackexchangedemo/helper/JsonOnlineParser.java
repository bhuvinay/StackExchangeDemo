package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.utils.QsBean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.QuestionQsBean;

public class JsonOnlineParser {

    ArrayList<QsBean> items;
    String jsonResponse;


    JsonOnlineParser(String jsonResponse) {
        items = new ArrayList<>();
        this.jsonResponse = jsonResponse;
    }

    public ArrayList<QsBean> getQuestionBeanList() {

        try {
            JSONObject obj = new JSONObject(jsonResponse);

            //Read the array of items ...
            JSONArray item_array = obj.getJSONArray(Constants.Q_ITEM);
            for (int i = 0; i < item_array.length(); i++) {

                JSONObject jsonObject = item_array.getJSONObject(i);
                //Read Owner info from the owner object ..
                JSONObject owner_info = jsonObject.getJSONObject(Constants.Q_OWNER);
                String display_name = owner_info.getString(Constants.Q_OWNER_DISPLAY_NAME);

                String question_title = jsonObject.getString(Constants.Q_TITLE);
                int question_score = jsonObject.getInt(Constants.Q_score);
                int question_id = jsonObject.getInt(Constants.Q_Id);

                //Create an object and add to the list ..
                QuestionQsBean qb = new QuestionQsBean(question_id, question_title, question_score, display_name);
                items.add(qb);

            }
        } catch (JSONException e) {
            Log.d(Constants.TAG, e.toString());
        }

        Log.d(Constants.TAG, "items.size : " + items.size());
        return items;
    }
}