package com.haivu.frogtutoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haivu on 12/3/17.
 */

public class student_appointment_classAdapter extends BaseAdapter{
    private profile context;
    private int layout;
    private List<student_appointment_class> stapptList;

    public student_appointment_classAdapter(profile context, int layout, List<student_appointment_class> stapptList) {
        this.context = context;
        this.layout = layout;
        this.stapptList = stapptList;
    }

    @Override
    public int getCount() {
        return stapptList.size();
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
        TextView viewname, viewdate, viewstart, viewend;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.viewname = (TextView)view.findViewById(R.id.viewtutorname);
            holder.viewdate = (TextView)view.findViewById(R.id.viewdate);
            holder.viewstart = (TextView)view.findViewById(R.id.apptstarttime);
            holder.viewend = (TextView)view.findViewById(R.id.apptendtime);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        final student_appointment_class appt = stapptList.get(i);
        holder.viewname.setText(appt.getTuname());
        holder.viewdate.setText(appt.getDate());
        holder.viewstart.setText("From "+appt.getStarttime());
        holder.viewend.setText("To "+appt.getEndtime());

        return view;
    }
}
