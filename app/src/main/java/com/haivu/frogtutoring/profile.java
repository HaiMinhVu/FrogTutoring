package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class profile extends BaseActivity {

    DBManager database;
    Button btnsearch;
    Calendar currentdate;
    SimpleDateFormat simpledate;
    TextView tvstname, tvstemail, tvstphone;
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

        simpledate = new SimpleDateFormat("yyyy-MM-dd");
        btnsearch = (Button)findViewById(R.id.btnSearch);
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

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLoggedin(true);
                Intent gotosearch = new Intent(profile.this,Search.class);
                gotosearch.putExtra("studentid", stid);
                startActivity(gotosearch);
            }
        });
        comparedate();
        displayapptUp(stid);
        displayapptPass(stid);

        lvpastappt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gettutorinfo(arrayStApptPass.get(i));
            }
        });

    }





    public void gettutorinfo(student_appointment_class appt){
        int tuid = appt.getTuid();
        String name = appt.getTuname();
        int apptid = appt.getStapptid();
        Intent makereview = new Intent(profile.this,make_review.class);
        makereview.putExtra("tutorname", name);
        makereview.putExtra("studentid", stid);
        makereview.putExtra("apptid", apptid);
        makereview.putExtra("tutorid", tuid);
        startActivity(makereview);

        //Toast.makeText(this, tuid+"", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, apptid+"", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, name+"", Toast.LENGTH_SHORT).show();
    }

    public void comparedate(){
        currentdate = Calendar.getInstance();
        String current = simpledate.format(currentdate.getTime());
        Cursor getapptdate = database.GetData("select apptID, apptdate from studentappointment");
        while (getapptdate.moveToNext()){
            int apptid = getapptdate.getInt(0);
            String apptdate = getapptdate.getString(1);
            try {
                Date date1 = simpledate.parse(apptdate);
                Date date2 = simpledate.parse(current);
                if(date2.compareTo(date1) > 0){
                    database.QueryData("update studentappointment set apptstatus = 0 where apptID = '"+apptid+"'");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }


    public void displayapptUp(String stid){
        Cursor getappt = database.GetData("select tutors.tuname, studentappointment.apptdate, studentappointment.apptstart, studentappointment.apptend from tutors join studentappointment on tutors.tuid = studentappointment.tuid where studentappointment.stid = '"+stid+"' and studentappointment.apptstatus = 1 order by studentappointment.apptdate");
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
        Cursor getappt = database.GetData("select tutors.tuname, studentappointment.apptdate, studentappointment.apptstart, studentappointment.apptend, studentappointment.apptID, studentappointment.tuid from tutors join studentappointment on tutors.tuid = studentappointment.tuid where studentappointment.stid = '"+stid+"' and studentappointment.apptstatus = 0 order by studentappointment.apptdate");
        arrayStApptPass.clear();
        while (getappt.moveToNext()) {
            String tuname = getappt.getString(0);
            String apptdate = getappt.getString(1);
            String apptstart = getappt.getString(2);
            String apptend = getappt.getString(3);
            int apptid = getappt.getInt(4);
            int appttuid = getappt.getInt(5);
            arrayStApptPass.add(new student_appointment_class(tuname,apptdate,apptstart,apptend, apptid, appttuid));
        }
        apptAdapterPass.notifyDataSetChanged();

    }


}
