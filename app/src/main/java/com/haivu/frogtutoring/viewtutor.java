package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class viewtutor extends AppCompatActivity {

    DBManager database;

    ListView lvreviews;
    ArrayList<reviews> arrayReviews;
    viewtutorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtutor);

        lvreviews = (ListView)findViewById(R.id.listreviews);

        arrayReviews = new ArrayList<>();
        adapter = new viewtutorAdapter(this, R.layout.each_tutor_review, arrayReviews);
        lvreviews.setAdapter(adapter);

        // create database
        database = new DBManager(this, "frogtutors.db", null, 1);

        Intent getintent = getIntent();
        String tutoremail = getintent.getStringExtra("tutorEmail");

        getReview(tutoremail);

    }

    public void getReview(String email){
        Cursor getreviews = database.GetData("select rate, comment from review join tutors where  review.tuid = tutors.tuid and tutors.tuemail = '"+email+"'");
        arrayReviews.clear();
        while (getreviews.moveToNext()){
            double rate = getreviews.getDouble(0);
            String comment = getreviews.getString(1);
            arrayReviews.add(new reviews(rate, comment));
        }
        adapter.notifyDataSetChanged();

    }

}
