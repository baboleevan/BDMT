
package kr.co.cashqc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Jung-Hum Cho Created by anp on 15. 1. 9..
 */
public class ReviewListAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    public ReviewListAdapter(Context context, ArrayList<ReviewData> datas) {
        mDataList = datas;

        inflater = LayoutInflater.from(context);
    }

    private ArrayList<ReviewData> mDataList;

    private LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ViewHolder h;
        if (v == null) {
            h = new ViewHolder();
            v = inflater.inflate(R.layout.row_review, parent, false);

            h.nick = (TextView)v.findViewById(R.id.row_review_nick);
            h.phone = (TextView)v.findViewById(R.id.row_review_phone);
            h.content = (TextView)v.findViewById(R.id.row_review_content);

            h.star1 = (ImageView)v.findViewById(R.id.row_review_star1);
            h.star2 = (ImageView)v.findViewById(R.id.row_review_star2);
            h.star3 = (ImageView)v.findViewById(R.id.row_review_star3);
            h.star4 = (ImageView)v.findViewById(R.id.row_review_star4);
            h.star5 = (ImageView)v.findViewById(R.id.row_review_star5);

            v.setTag(h);
        } else {
            h = (ViewHolder)v.getTag();
        }

        ReviewData item = (ReviewData)getItem(position);

        int score = item.getRating();

        switch (score) {
            case 0:
                h.star1.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star2.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star3.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star4.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star5.setImageResource(R.drawable.custom_ratingbar_empty);
                break;
            case 1:
                h.star1.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star2.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star3.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star4.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star5.setImageResource(R.drawable.custom_ratingbar_empty);
                break;
            case 2:
                h.star1.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star2.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star3.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star4.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star5.setImageResource(R.drawable.custom_ratingbar_empty);
                break;
            case 3:
                h.star1.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star2.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star3.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star4.setImageResource(R.drawable.custom_ratingbar_empty);
                h.star5.setImageResource(R.drawable.custom_ratingbar_empty);
                break;
            case 4:
                h.star1.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star2.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star3.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star4.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star5.setImageResource(R.drawable.custom_ratingbar_empty);
                break;
            case 5:
                h.star1.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star2.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star3.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star4.setImageResource(R.drawable.custom_ratingbar_filled);
                h.star5.setImageResource(R.drawable.custom_ratingbar_filled);
                break;
        }

        // h.ratingBar.setNumStars(score);

        // h.nick.setText(item.getNick());
//01037452742
//1234567891011

        Log.e(TAG, "item.getPhone() : " + item.getPhone());

        try {
            String first = item.getPhone().substring(0, 3);
            Log.e(TAG, "first : " + first);
            String last = item.getPhone().substring(7, 11);
            Log.e(TAG, "last : " + last);

            String phoneCode = first.concat("****").concat(last);

            h.phone.setText(phoneCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        h.content.setText(item.getContent());

        return v;
    }

    private class ViewHolder {

        private TextView nick, phone, content;

        private ImageView star1, star2, star3, star4, star5;

    }
}
