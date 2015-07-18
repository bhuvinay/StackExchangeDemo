package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.ui.WelcomeScreen;
import demo.stackexchange.com.stackexchangedemo.utils.QsBean;

public class QuesListAdapter extends BaseAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    private Activity mActivity;
    ArrayList<QsBean> mQData;

    public QuesListAdapter(WelcomeScreen activity, ArrayList<QsBean> mData) {
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

            Log.d("vinay", "convertView==null");
            vi = inflater.inflate(R.layout.question_itemlist_view, null);

            holder = new ViewHolder();
            holder.mQuesTitle = (TextView) vi.findViewById(R.id.question_text_view);
            holder.mUser = (TextView) vi.findViewById(R.id.question_owner_view);
            holder.mScore = (TextView) vi.findViewById(R.id.question_score_view);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.mQuesTitle.setText(mQData.get(position).getTitle());
        holder.mUser.setText(mQData.get(position).getOwner());
        holder.mScore.setText(String.valueOf(mQData.get(position).getScore()));

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
            WelcomeScreen ws = (WelcomeScreen) mActivity;
            int id = mQData.get(mPosition).getId();
            ws.onClickListItem(mPosition, id);
        }
    }
}
