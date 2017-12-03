package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class profile extends AppCompatActivity {

    DBManager database;
    Button btnsearch;

    TextView tvlogout, tvstname, tvstemail, tvstphone;
    UserSession session;
    String stid;
    ListView lvcomingupappt, lvpastappt;
    ArrayList<student_appointment_class> arrayStApptUp, arrayStApptPass;
    student_appointment_classAdapter apptAdapterUp, apptAdapterPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = new DBManager(this, "frogtutors.db", null, 1);
        session = new UserSession(this);

        btnsearch = (Button)findViewById(R.id.btnSearch);
        tvlogout = (TextView)findViewById(R.id.tvsignout);
        tvstname = (TextView)findViewById(R.id.tvstname);
        tvstemail = (TextView)findViewById(R.id.tvdisplayemail);
        tvstphone = (TextView)findViewById(R.id.tvdisplayphone);

        lvcomingupappt = (ListView)findViewById(R.id.lvcomingupappt);
        lvpastappt = (ListView)findViewById(R.id.lvfinishedappt);
        arrayStApptUp = new ArrayList<>();
        arrayStApptPass = new ArrayList<>();
        apptAdapterUp = new student_appointment_classAdapter(this, R.layout.each_student_appt, arrayStApptUp);
        apptAdapterPass = new student_appointment_classAdapter(this, R.layout.each_student_appt, arrayStApptPass);
        lvcomingupappt.setAdapter(apptAdapterUp);
        lvpastappt.setAdapter(apptAdapterPass);

        Intent getintent = getIntent();
        stid = getintent.getStringExtra("studentid");
        getintent.removeExtra("studentid");
        Cursor stprofile = database.GetData("select stemail, stphone, stname from students where stid = '"+stid+"'");

        StringBuffer sbemail = new StringBuffer();
        StringBuffer sbphone = new StringBuffer();
        StringBuffer sbname = new StringBuffer();
        while (stprofile.moveToNext()){
            sbemail.append(stprofile.getString(0));
            sbphone.append(stprofile.getString(1));
            sbname.append(stprofile.getString(2));
        }
        tvstname.setText("Welcome "+sbname.toString());
        tvstemail.setText(sbemail.toString());
        tvstphone.setText(sbphone.toString());

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLoggedin(false);
                Intent backtomain = new Intent(profile.this,MainActivity.class);
                startActivity(backtomain);

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLoggedin(true);
                Intent gotosearch = new Intent(profile.this,Search.class);
                gotosearch.putExtra("studentid", stid);
                startActivity(gotosearch);
            }
        });
        displayapptUp(stid);
        displayapptPass(stid);
    }

    public void displayapptUp(String stid){
        Cursor getappt = database.GetData("select tutors.tuname, studentappointment.apptdate, studentappointment.apptstart, studentappointment.apptend from tutors join studentappointment on tutors.tuid = studentappointment.tuid where studentappointment.stid = '"+stid+"' and studentappointment.appstatus = 1");
        arrayStApptUp.clear();
        while (getappt.moveToNext()) {
                String tuname = getappt.getString(0);
                String apptdate = getappt.getString(1);
                String apptstart = getappt.getString(2);
                String apptend = getappt.getString(3);
                arrayStApptUp.add(new student_appointment_class(tuname,apptdate,apptstart,apptend));
        }
        apptAdapterUp.notifyDataSetChanged();
    }
    public void displayapptPass(String stid){
        Cursor getappt = database.GetData("select tutors.tuname, studentappointment.apptdate, studentappointment.apptstart, studentappointment.apptend from tutors join studentappointment on tutors.tuid = studentappointment.tuid where studentappointment.stid = '"+stid+"' and studentappointment.appstatus = 0");
        arrayStApptPass.clear();
        while (getappt.moveToNext()) {
            String tuname = getappt.getString(0);
            String apptdate = getappt.getString(1);
            String apptstart = getappt.getString(2);
            String apptend = getappt.getString(3);
            arrayStApptPass.add(new student_appointment_class(tuname,apptdate,apptstart,apptend));
        }
        apptAdapterPass.notifyDataSetChanged();

    }


}
