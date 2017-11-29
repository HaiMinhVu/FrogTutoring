package com.haivu.frogtutoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haivu on 11/27/17.
 */

public class viewtutorAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<reviews> listReviews;

    public viewtutorAdapter(Context context, int layout, List<reviews> listReviews) {
        this.context = context;
        this.layout = layout;
        this.listReviews = listReviews;
    }

    @Override
    public int getCount() {
        return listReviews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class ViewHolder{
        TextView tvrate, tvcomment;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvcomment    = (TextView) view.findViewById(R.id.tvreview);
            holder.tvrate = (TextView) view.findViewById(R.id.tvrating);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final reviews review = listReviews.get(i);
        holder.tvcomment.setText(review.getComment());
        holder.tvrate.setText(Double.toString(review.getRating()));

        return view;
    }
}
