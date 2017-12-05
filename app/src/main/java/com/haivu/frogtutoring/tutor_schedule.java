package com.haivu.frogtutoring;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.haivu.frogtutoring.R.id.back;

public class tutor_schedule extends BaseActivity {

    DBManager database;
    TextView tvpickdate, tvstart, tvend;
    Calendar time1, time2, day;
    Button add, cancel;
    SimpleDateFormat simpledate, simplehour;
    ListView viewschedules;
    ArrayList<tutor_schedule_class> arraySchedule;
    tutor_schedule_classAdapter scheduleAdapter;

    String tuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_schedule);

        database = new DBManager(this, "frogtutors.db", null, 1);

        tvpickdate = (TextView)findViewById(R.id.tvpickdate);
        tvstart = (TextView)findViewById(R.id.tvstarttime);
        tvend = (TextView)findViewById(R.id.tvendtime);
        add = (Button)findViewById(R.id.btnaddschedule);
        cancel = (Button)findViewById(R.id.btncancelschedule);

        // view schedule init
        viewschedules = (ListView)findViewById(R.id.lvschedule);
        arraySchedule = new ArrayList<>();
        scheduleAdapter = new tutor_schedule_classAdapter(this, R.layout.each_tutor_schedule, arraySchedule);
        viewschedules.setAdapter(scheduleAdapter);

        simpledate = new SimpleDateFormat("yyyy-MM-dd");
        simplehour = new SimpleDateFormat("HH:mm:00");

        Intent getintent = getIntent();
        tuid = getintent.getStringExtra("tutorid");
        //getintent.removeExtra("tutorid");

        tvpickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickday();
            }
        });

        tvstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picktime1();
            }
        });

        tvend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picktime2();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvpickdate.getText().toString().equals("Pick Date") || tvstart.getText().toString().equals("Start Time") || tvend.getText().toString().equals("End Time")) {
                    Toast.makeText(tutor_schedule.this, "Select Date and Time", Toast.LENGTH_SHORT).show();
                }
                else {
                    int duration = (int) ((time2.getTimeInMillis() - time1.getTimeInMillis()) / (1000 * 60 * 60));
                    if (duration < 0) {
                        Toast.makeText(tutor_schedule.this, "Choose end time greater than start time", Toast.LENGTH_SHORT).show();
                    } else {
                        database.QueryData("insert into tutorschedule values(null, '" + tuid + "', '" + tvpickdate.getText().toString() + "', '" + tvstart.getText().toString() + "', '" + tvend.getText().toString() + "', " + duration + ", 1)");
                        getschedule(tuid);
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2tutor = new Intent(tutor_schedule.this, profile_tutor.class);
                back2tutor.putExtra("tutorid", tuid);
                startActivity(back2tutor);
            }
        });

        getschedule(tuid);
    }

    public void getschedule(String tuid){
        Cursor dataSchedule = database.GetData("select * from tutorschedule where tuid = '"+tuid+"' and status = 1 order by tutorschedule.date");
        arraySchedule.clear();
        while (dataSchedule.moveToNext()){
            int id = dataSchedule.getInt(0);
            int s_tuid = dataSchedule.getInt(1);
            String date = dataSchedule.getString(2);
            String start = dataSchedule.getString(3);
            String end = dataSchedule.getString(4);
            String duration = dataSchedule.getString(5);
            int status = dataSchedule.getInt(6);
            arraySchedule.add(new tutor_schedule_class(id, s_tuid,date,start,end,duration,status));
        }
        scheduleAdapter.notifyDataSetChanged();
    }

    public void dialogdelete(final int scheid){
        final AlertDialog.Builder dia_delete = new AlertDialog.Builder(this);
        dia_delete.setMessage("Do you want to delete this schedule?");
        dia_delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("delete from tutorschedule where scheID = '"+scheid+"'");
                getschedule(tuid);
            }
        });
        dia_delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dia_delete.show();
    }

    private void picktime1(){
        time1 = Calendar.getInstance();
        int hour = time1.get(Calendar.HOUR_OF_DAY);
        int min = time1.get(Calendar.MINUTE);
        TimePickerDialog t1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time1.set(0,0,0,i,i1);
                tvstart.setText(simplehour.format(time1.getTime()));
            }
        }, hour,min,true);
        t1.show();
    }

    private void picktime2(){
        time2 = Calendar.getInstance();
        int hour = time2.get(Calendar.HOUR_OF_DAY);
        int min = time2.get(Calendar.MINUTE);
        TimePickerDialog t2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time2.set(0,0,0,i,i1);
                tvend.setText(simplehour.format(time2.getTime()));
            }
        }, hour,min,true);
        t2.show();
    }

    public void pickday(){
        day = Calendar.getInstance();
        int date = day.get(Calendar.DATE);
        int month = day.get(Calendar.MONTH);
        int year = day.get(Calendar.YEAR);
        DatePickerDialog date1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                day.set(i,i1,i2);
                tvpickdate.setText(simpledate.format(day.getTime()));
            }
        }, year, month, date);
        date1.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(tutor_schedule.this,profile_tutor.class);
            back2profile.putExtra("tutorid", tuid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }


}
