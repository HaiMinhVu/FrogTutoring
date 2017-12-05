package com.haivu.frogtutoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by haivu on 11/18/17.
 */

public class tutorsAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private List<tutors> listTutors;

    public tutorsAdapter(Context context, int layout, List<tutors> listTutors) {
        this.context = context;
        this.layout = layout;
        this.listTutors = listTutors;
    }

    @Override
    public int getCount() {
        return listTutors.size();
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
        TextView tvtuname, tvtusubject, tvturate;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        DecimalFormat df = new DecimalFormat("#.#");
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvtuname    = (TextView) view.findViewById(R.id.tvtuname);
            holder.tvtusubject = (TextView) view.findViewById(R.id.tvtusubject);
            holder.tvturate    = (TextView) view.findViewById(R.id.tvturate);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final tutors tutor = listTutors.get(i);
        String rating = df.format(tutor.getTurate());
        holder.tvtuname.setText(tutor.getTuname());
        holder.tvtusubject.setText(tutor.getTusubject());
        holder.tvturate.setText(rating);

        return view;
    }





}
