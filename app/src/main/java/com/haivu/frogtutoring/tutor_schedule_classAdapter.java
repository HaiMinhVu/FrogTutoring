package com.haivu.frogtutoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haivu on 12/2/17.
 */

public class tutor_schedule_classAdapter extends BaseAdapter{

    private tutor_schedule context;
    private int layout;
    private List<tutor_schedule_class> schedulelist;

    public tutor_schedule_classAdapter(tutor_schedule context, int layout, List<tutor_schedule_class> schedulelist) {
        this.context = context;
        this.layout = layout;
        this.schedulelist = schedulelist;
    }

    @Override
    public int getCount() {
        return schedulelist.size();
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
        TextView viewdate, viewstart, viewend, viewduration;
        ImageView imdelete;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.viewdate = (TextView)view.findViewById(R.id.tvdisplaydate);
            holder.viewstart = (TextView)view.findViewById(R.id.tvdisplaystart);
            holder.viewend = (TextView)view.findViewById(R.id.tvdisplayend);
            holder.viewduration = (TextView)view.findViewById(R.id.tvdisplayduration);
            holder.imdelete = (ImageView)view.findViewById(R.id.imdelete);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        final tutor_schedule_class schedule = schedulelist.get(i);
        holder.viewdate.setText(schedule.getDate());
        holder.viewstart.setText(schedule.getStart());
        holder.viewend.setText(schedule.getEnd());
        holder.viewduration.setText(schedule.getDuration()+" hours");

        // delete onclick
        holder.imdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogdelete(schedule.getScheid());
            }
        });

        return view;
    }
}
