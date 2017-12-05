package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.haivu.frogtutoring.R.id.back;

public class viewtutor extends BaseActivity {

    DBManager database;

    ListView lvreviews;
    ArrayList<reviews> arrayReviews;
    viewtutorAdapter adapter;

    TextView name, price, subject;
    Button makeappt;
    int tuid;
    String stid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtutor);

        lvreviews = (ListView)findViewById(R.id.listreviews);
        arrayReviews = new ArrayList<>();
        adapter = new viewtutorAdapter(this, R.layout.each_tutor_review, arrayReviews);
        lvreviews.setAdapter(adapter);

        name = (TextView)findViewById(R.id.tudetailname) ;
        price = (TextView)findViewById(R.id.tudetailprice);
        subject = (TextView)findViewById(R.id.tudetailsubject);
        makeappt = (Button)findViewById(R.id.btnmakeppt);
        // create database
        database = new DBManager(this, "frogtutors.db", null, 1);

        Intent getintent = getIntent();
        tuid = getintent.getIntExtra("tutorid",0);
        stid = getintent.getStringExtra("studentid");
        getReview(tuid);

        Cursor tutordetail = database.GetData("select tuname, tusubject, tuprice from tutors where tuid = '"+tuid+"'");
        StringBuffer sbname = new StringBuffer();
        StringBuffer sbsubject = new StringBuffer();
        StringBuffer sbprice = new StringBuffer();
        while (tutordetail.moveToNext()){
            sbname.append(tutordetail.getString(0));
            sbsubject.append(tutordetail.getString(1));
            sbprice.append(tutordetail.getString(2));
        }
        name.setText(sbname.toString());
        subject.setText("Subject: "+sbsubject.toString());
        price.setText("$"+sbprice.toString() +"/h");

        makeappt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoappt = new Intent(viewtutor.this, appointment.class);
                gotoappt.putExtra("tutorid", tuid);
                gotoappt.putExtra("studentid", stid);
                startActivity(gotoappt);
            }
        });
    }

    public void getReview(int tuid){
        Cursor getreviews = database.GetData("select rate, comment from review join tutors where  review.tuid = tutors.tuid and tutors.tuid = '"+tuid+"'");
        arrayReviews.clear();
        while (getreviews.moveToNext()){
            double rate = getreviews.getDouble(0);
            String comment = getreviews.getString(1);
            arrayReviews.add(new reviews(rate, comment));
        }
        adapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(viewtutor.this,profile.class);
            back2profile.putExtra("studentid", stid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }

}
