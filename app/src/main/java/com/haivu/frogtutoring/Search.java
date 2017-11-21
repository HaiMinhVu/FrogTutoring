package com.haivu.frogtutoring;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    // database
    DBManager database;

    ImageView ibtnsearch;
    EditText edtsearch;

    // for tutors search
    ListView lvtutors;
    ArrayList<tutors> arrayTutors;
    tutorsAdapter adapter;

    // for filter categories
    ArrayList<String> arrayCategories;
    Spinner spfilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtsearch = (EditText)findViewById(R.id.edtSearch);
        ibtnsearch = (ImageView)findViewById(R.id.imsearch);
        spfilter = (Spinner)findViewById(R.id.spfilter);

        lvtutors = (ListView)findViewById(R.id.listtutors);
        arrayTutors = new ArrayList<>();
        adapter = new tutorsAdapter(this, R.layout.each_tutor_view, arrayTutors);
        lvtutors.setAdapter(adapter);

        // create database
        database = new DBManager(this, "frogtutors.db", null, 1);

        // image search onclick
        ibtnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTutors();
            }
        });




    }
    // get tutors and add to arraytutors
    public void getTutors(){
        String searchname = edtsearch.getText().toString();
        Cursor gettutors = database.GetData("SELECT tuname, tusubject, turate FROM tutors WHERE tuname like '%" +searchname+"%'");
        arrayTutors.clear();
        while (gettutors.moveToNext()){
            String name = gettutors.getString(0);
            String subject = gettutors.getString(1);
            double rate = gettutors.getDouble(2);
            arrayTutors.add(new tutors(name, subject, rate));
        }
        adapter.notifyDataSetChanged();

    }

    // get categories and add to arraycategories
    public void getCategories(){
        Cursor getcat = database.GetData("select distinct tusubject from tutors");
        arrayCategories.clear();
        while (getcat.moveToNext()){
            String subject = getcat.getString(0);
            arrayCategories.add(subject);
        }


    }

    public void loadSpinnerData() {
        getCategories();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCategories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spfilter.setAdapter(dataAdapter);
    }


}
