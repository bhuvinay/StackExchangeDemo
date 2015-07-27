package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.ui.WelcomeScreen;
import demo.stackexchange.com.stackexchangedemo.utils.Bean;


public class QuesListAdapter extends BaseAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    private Activity mActivity;
    ArrayList<Bean> mQData;

    public QuesListAdapter(WelcomeScreen activity, ArrayList<Bean> mData) {
        mActivity = activity;
        mQData = mData;
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {
        public TextView mQuesTitle;
        public TextView mUser;
        public TextView mScore;
    }

    @Override
    public int getCount() {
        return mQData.size();
    }

    @Override
    public Object getItem(int i) {
        return mQData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.question_itemlist_view, null);
            holder = new ViewHolder();
            holder.mQuesTitle = (TextView) vi.findViewById(R.id.question_text_view);
            holder.mUser = (TextView) vi.findViewById(R.id.question_owner_view);
            holder.mScore = (TextView) vi.findViewById(R.id.question_score_view);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        //Converting Html text code to plain text
        String qSTitle = Html.fromHtml( mQData.get(position).getTitle()).toString();

        holder.mQuesTitle.setText(qSTitle);
        holder.mUser.setText(Html.fromHtml( mQData.get(position).getOwner()).toString());
        holder.mScore.setText(String.valueOf(mQData.get(position).getScore()));

        vi.setOnClickListener(new OnItemClickListener(position));

        if(mQData.get(position).isAnswered() == 1) {

            vi.setBackgroundResource(R.drawable.list_selected_background_light);
        } else {
            //vi.setOnClickListener(null);
            vi.setBackgroundResource(R.drawable.list_selected_background_no_answer);
        }
            return vi;

    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            WelcomeScreen ws = (WelcomeScreen) mActivity;
            int id = mQData.get(mPosition).getId();
            //Converting Ques Html text code to plain text
            String qTitle = Html.fromHtml(mQData.get(mPosition).getTitle()).toString();
            if(mQData.get(mPosition).isAnswered() == 1) {
                ws.onClickListItem(mPosition, id, qTitle);
            } else {
               Toast.makeText(mActivity, "No Answer present", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
