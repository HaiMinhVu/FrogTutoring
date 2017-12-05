package com.haivu.frogtutoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.haivu.frogtutoring.R.id.back;

public class appointment extends BaseActivity {

    DBManager database;
    String stid;
    int tuid, price, total;
    ListView viewavailable;
    ArrayList<tutor_schedule_class> arrayAvailable;
    tutor_available_classAdapter availableAdapter;
    TextView studentname, tutorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // create database
        database = new DBManager(this, "frogtutors.db", null, 1);

        studentname = (TextView)findViewById(R.id.studentname);
        tutorname = (TextView)findViewById(R.id.tutorname);

        Intent getintent = getIntent();
        tuid = getintent.getIntExtra("tutorid",0);
        stid = getintent.getStringExtra("studentid");


        Cursor stname = database.GetData("select stname from students where stid = '"+stid+"'");
        StringBuffer sbstname = new StringBuffer();
        while (stname.moveToNext()){
            sbstname.append(stname.getString(0));
        }
        Cursor tuname = database.GetData("select tuname, tuprice from tutors where tuid = '"+tuid+"'");
        StringBuffer sbtuname = new StringBuffer();
        StringBuffer sbtuprice = new StringBuffer();
        while (tuname.moveToNext()){
            sbtuname.append(tuname.getString(0));
            sbtuprice.append(tuname.getString(1));
        }

        studentname.setText(sbstname.toString());
        tutorname.setText(sbtuname.toString());
        price = Integer.parseInt(sbtuprice.toString());

        viewavailable = (ListView)findViewById(R.id.lvavailable);
        arrayAvailable = new ArrayList<>();
        availableAdapter = new tutor_available_classAdapter(this, R.layout.each_tutor_available, arrayAvailable);
        viewavailable.setAdapter(availableAdapter);

        getavailable(tuid);
        viewavailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAppt(arrayAvailable.get(i));
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(appointment.this,profile.class);
            back2profile.putExtra("studentid", stid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }


    // display available
    public void getavailable(int tuid){
        Cursor dataSchedule = database.GetData("select * from tutorschedule where tuid = '"+tuid+"' and status = 1 order by tutorschedule.date");
        arrayAvailable.clear();
        while (dataSchedule.moveToNext()){
            int id = dataSchedule.getInt(0);
            int s_tuid = dataSchedule.getInt(1);
            String date = dataSchedule.getString(2);
            String start = dataSchedule.getString(3);
            String end = dataSchedule.getString(4);
            String duration = dataSchedule.getString(5);
            int status = dataSchedule.getInt(6);
            arrayAvailable.add(new tutor_schedule_class(id, s_tuid,date,start,end,duration,status));
        }
        availableAdapter.notifyDataSetChanged();
    }

    public void selectedAppt(final tutor_schedule_class schedule){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        total = Integer.parseInt(schedule.getDuration()) * price;
        b.setCancelable(true);
        b.setTitle("View Selected Appointment");
        b.setMessage("Student: " +studentname.getText().toString()+"\nTutor: "+tutorname.getText().toString()+  "\nDate: "+schedule.getDate()+"\nFrom: "+schedule.getStart()+"\nTo: "+schedule.getEnd()+"\nDuration: "+schedule.getDuration()+"\nTotal: $"+Integer.parseInt(schedule.getDuration())*price);
        b.setPositiveButton("Make Appt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotopayment = new Intent(appointment.this,payment.class);
                gotopayment.putExtra("tutorid", tuid);
                gotopayment.putExtra("studentid", stid);
                gotopayment.putExtra("total", total);
                gotopayment.putExtra("apptdate",schedule.getDate().toString());
                gotopayment.putExtra("apptstart",schedule.getStart().toString());
                gotopayment.putExtra("apptend",schedule.getEnd().toString());
                gotopayment.putExtra("scheid",schedule.getScheid());
                startActivity(gotopayment);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.show();
    }

}