package demo.stackexchange.com.stackexchangedemo.helper;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.ui.AnswerScreen;
import demo.stackexchange.com.stackexchangedemo.utils.Bean;

/**
 * Created by Vinay on 18-07-2015.
 */
public class AnsListAdapter extends BaseAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    private Activity mActivity;
    ArrayList<Bean> mQData;

    public AnsListAdapter(AnswerScreen activity, ArrayList<Bean> mData) {
        mActivity = activity;
        mQData = mData;
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public static class ViewHolder {
        // public WebView mAnsBody;
        public TextView mAnsBody;
        public TextView mUser;
        public TextView mVote;
    }

    @Override
    public void onClick(View view) {

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.answer_itemlist_view, null);

            holder = new ViewHolder();
            holder.mAnsBody = (TextView) vi.findViewById(R.id.answer_web_view);
            //	to make hyperlink clickable
            // holder.mAnsBody.setMovementMethod(LinkMovementMethod.getInstance());
            // holder.mAnsBody = (WebView) vi.findViewById(R.id.answer_web_view);
            holder.mUser = (TextView) vi.findViewById(R.id.answer_owner_view);
            holder.mVote = (TextView) vi.findViewById(R.id.answer_score_view);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        //  holder.mAnsBody.loadDataWithBaseURL("", mQData.get(position).getTitle().trim(), "text/html", "UTF-8", "");
        //HTML text formatting
        holder.mAnsBody.setText(Html.fromHtml(mQData.get(position).getTitle()));
        holder.mUser.setText(Html.fromHtml(mQData.get(position).getOwner()).toString());
        holder.mVote.setText(String.valueOf(mQData.get(position).getScore()));
        vi.setOnClickListener(new OnItemClickListener(position));

        return vi;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            AnswerScreen as = (AnswerScreen) mActivity;
            String webData = mQData.get(mPosition).getTitle();
            as.onClickListItem(mPosition, 0, webData);
        }
    }
}
