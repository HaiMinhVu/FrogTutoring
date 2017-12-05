package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.haivu.frogtutoring.R.id.tvstname;

public class profile_tutor extends BaseActivity {

    DBManager database;
    Button btnupdateprofile, btneditschedule;
    TextView tvtuname, tvtuemail, tvtuphone, tvtusubject, tvtubio, tvturating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tutor);

        database = new DBManager(this, "frogtutors.db", null, 1);

        tvtuname = (TextView)findViewById(tvstname);
        tvtuemail = (TextView)findViewById(R.id.tvdisplayemail);
        tvtuphone = (TextView)findViewById(R.id.tvdisplayphone);
        tvtusubject = (TextView)findViewById(R.id.tvdisplaysubject);
        tvtubio = (TextView)findViewById(R.id.tvdisplaybio);
        tvturating = (TextView)findViewById(R.id.tvdisplayrating);
        btnupdateprofile = (Button)findViewById(R.id.btnupdatetutor);
        btneditschedule = (Button)findViewById(R.id.btneditschedule);

        Intent getintent = getIntent();
        String tuid = getintent.getStringExtra("tutorid");
        getintent.removeExtra("tutorid");
        Cursor tuprofile = database.GetData("select tuemail, tuphone, tuname, tusubject, tubiography, tuprice from tutors where tuid = '"+tuid+"'");

        StringBuffer sbtuemail = new StringBuffer();
        StringBuffer sbtuphone = new StringBuffer();
        StringBuffer sbtuname = new StringBuffer();
        StringBuffer sbtusubject = new StringBuffer();
        StringBuffer sbtubiography = new StringBuffer();
        StringBuffer sbtuprice = new StringBuffer();
        while (tuprofile.moveToNext()){
            sbtuemail.append(tuprofile.getString(0));
            sbtuphone.append(tuprofile.getString(1));
            sbtuname.append(tuprofile.getString(2));
            sbtusubject.append(tuprofile.getString(3));
            sbtubiography.append(tuprofile.getString(4));
            sbtuprice.append(tuprofile.getString(5));
        }
        tvtuname.setText("Welcome "+sbtuname.toString());
        tvtuemail.setText(sbtuemail.toString());
        tvtuphone.setText(sbtuphone.toString());
        tvtusubject.setText(sbtusubject.toString());
        tvtubio.setText(sbtubiography.toString());
        tvturating.setText("$"+sbtuprice.toString()+"/hour");

        btnupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor gettuid = database.GetData("select tuid from tutors where tuemail = '"+tvtuemail.getText().toString()+"' and tuphone = '"+tvtuphone.getText().toString()+"'");
                StringBuffer sb = new StringBuffer();
                while (gettuid.moveToNext()){
                    sb.append(gettuid.getString(0));
                }
                String tuid = sb.toString();
                Intent gotoedit = new Intent(profile_tutor.this,edit_tutor_profile.class);
                gotoedit.putExtra("tutorid",tuid);
                startActivity(gotoedit);
            }
        });

        btneditschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor gettuid = database.GetData("select tuid from tutors where tuemail = '"+tvtuemail.getText().toString()+"' and tuphone = '"+tvtuphone.getText().toString()+"'");
                StringBuffer sb = new StringBuffer();
                while (gettuid.moveToNext()){
                    sb.append(gettuid.getString(0));
                }
                String tuid = sb.toString();
                Intent gotoedit = new Intent(profile_tutor.this,tutor_schedule.class);
                gotoedit.putExtra("tutorid",tuid);
                startActivity(gotoedit);
            }
        });

    }
}
