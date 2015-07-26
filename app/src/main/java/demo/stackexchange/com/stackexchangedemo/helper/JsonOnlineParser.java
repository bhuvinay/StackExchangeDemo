package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.utils.Bean;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;

public class JsonOnlineParser {

    private static JsonOnlineParser mInstance;

    private JsonOnlineParser() {

    }

    public static JsonOnlineParser getInstance() {
        if (mInstance == null) {
            mInstance = new JsonOnlineParser();

        }
        return mInstance;
    }


    public ArrayList<Bean> getQuestionBeanList(String jsonResponse) {

        ArrayList<Bean> items = new ArrayList<>();
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
                Bean qb = new Bean(question_id, question_title, question_score, display_name,0);
                items.add(qb);

            }
        } catch (JSONException e) {
            Log.d(Constants.TAG, e.toString());
        }

        Log.d(Constants.TAG, "items.size : " + items.size());
        return items;
    }


    public ArrayList<Bean> getAnswerBeanList(String jsonResponse) {

        ArrayList<Bean> itemsAns = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonResponse);

            //Read the array of items ...
            JSONArray item_array = obj.getJSONArray(Constants.A_ITEM);
            for (int i = 0; i < item_array.length(); i++) {

                JSONObject jsonObject = item_array.getJSONObject(i);
                //Read Owner info from the owner object ..
                JSONObject owner_info = jsonObject.getJSONObject(Constants.A_OWNER);
                String display_name = owner_info.getString(Constants.A_OWNER_DISPLAY_NAME);

                String answer_body = jsonObject.getString(Constants.A_BODY);
                int answer_votes = jsonObject.getInt(Constants.A_score);
                int answer_id = jsonObject.getInt(Constants.A_Id);
                int ques_id = jsonObject.getInt(Constants.Q_Id);

                //Create an object and add to the list ..
                Bean ab = new Bean(answer_id, answer_body, answer_votes, display_name, ques_id);
                itemsAns.add(ab);

            }
        } catch (JSONException e) {
            Log.d(Constants.TAG, e.toString());
        }
        Log.d(Constants.TAG, "items.size : " + itemsAns.size());
        return itemsAns;
    }
}
