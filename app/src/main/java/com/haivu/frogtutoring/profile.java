package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    DBManager database;
    Button btnsearch, btnappointments, btnpayments, btnreviews;
    TextView tvlogout, tvstname, tvstemail, tvstphone;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = new DBManager(this, "frogtutors.db", null, 1);
        session = new UserSession(this);

        btnsearch = (Button)findViewById(R.id.btnSearch);
        btnappointments = (Button)findViewById(R.id.btnAppointments);
        btnpayments = (Button)findViewById(R.id.btnPayments);
        btnreviews = (Button)findViewById(R.id.btnReviews);
        tvlogout = (TextView)findViewById(R.id.tvsignout);
        tvstname = (TextView)findViewById(R.id.tvstname);
        tvstemail = (TextView)findViewById(R.id.tvdisplayemail);
        tvstphone = (TextView)findViewById(R.id.tvdisplayphone);

        Intent getintent = getIntent();
        String stid = getintent.getStringExtra("studentid");
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
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLoggedin(true);
                Intent gotosearch = new Intent(profile.this,Search.class);
                startActivity(gotosearch);
            }
        });
    }

}
