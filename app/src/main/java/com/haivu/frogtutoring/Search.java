package com.haivu.frogtutoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    // database
    DBManager database;
    UserSession session;
    ImageView ibtnsearch;
    EditText edtsearch;

    // for tutors search
    ListView lvtutors;
    ArrayList<tutors> arrayTutors;
    tutorsAdapter adapter;

    // for filter categories
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


        // create database and session
        database = new DBManager(this, "frogtutors.db", null, 1);
        session = new UserSession(this);

        // load subject to spinner;
        loadCategories();

        // search for tutors
        ibtnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spfilter.getSelectedItem().toString(); // get subject from spinner
                if(text.equals("Subject"))
                    getTutors();
                else
                    getSubTutors(text);
            }
        });

        lvtutors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(session.loggedin()){
                    tutorDetail(arrayTutors.get(i));
                }
                else {
                    dialog_login_require();
                }
            }
        });
    }

    // get tutors by name
    public void getTutors(){
        String searchname = edtsearch.getText().toString();
        Cursor gettutors = database.GetData("SELECT tuname, tusubject, tubiography, turate, tuprice, tuemail FROM tutors WHERE tuname like '%" +searchname+ "%' order by turate DESC, tuname");
        arrayTutors.clear();
        while (gettutors.moveToNext()){
            String name = gettutors.getString(0);
            String subject = gettutors.getString(1);
            String biography = gettutors.getString(2);
            double rate = gettutors.getDouble(3);
            double price = gettutors.getDouble(4);
            String email = gettutors.getString(5);
            arrayTutors.add(new tutors(name, subject,biography, rate, price, email));
        }
        adapter.notifyDataSetChanged();

    }

    // get tutors by name and subject
    public void getSubTutors(String sub){
        String searchname = edtsearch.getText().toString();
        Cursor gettutors = database.GetData("SELECT tuname, tusubject, tubiography, turate, tuprice, tuemail FROM tutors WHERE tuname like '%" +searchname+ "%' AND tusubject = '"+sub+"' order by turate DESC, tuname");
        arrayTutors.clear();
        while (gettutors.moveToNext()){
            String name = gettutors.getString(0);
            String subject = gettutors.getString(1);
            String biography = gettutors.getString(2);
            double rate = gettutors.getDouble(3);
            double price = gettutors.getDouble(4);
            String email = gettutors.getString(5);
            arrayTutors.add(new tutors(name, subject,biography, rate, price, email));
        }
        adapter.notifyDataSetChanged();
    }

    // query subject and load in spinner
    public void loadCategories(){
        Cursor getcat = database.GetData("SELECT DISTINCT tusubject FROM tutors order by tusubject");
        ArrayList arraySub = new ArrayList();
        arraySub.add("Subject");
        while (getcat.moveToNext()){
            arraySub.add(getcat.getString(0));
        }
        ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySub);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spfilter.setAdapter(subAdapter);
    }

    // view tutor detail
    public void tutorDetail(tutors tutor){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(tutor.getTuname());
        b.setMessage("$"+tutor.getTuprice()+"/hr\n\n"+tutor.getTubiography());
        final String tutorEmail = tutor.getTuemail();
        b.setPositiveButton("Make Appt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotoappt = new Intent(Search.this,appointment.class);
                startActivity(gotoappt);
            }
        });
        b.setNegativeButton("View Review", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotoview = new Intent(Search.this,viewtutor.class);
                gotoview.putExtra("tutorEmail", tutorEmail);        // pass tutor email to viewtutor page
                startActivity(gotoview);

            }
        });


        b.show();
    }

    public void dialog_login_require(){
        AlertDialog.Builder alterdialog = new AlertDialog.Builder(this);
        alterdialog.setTitle("Warning");
        alterdialog.setMessage("You must log in to see detail!!!");
        alterdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotomain = new Intent(Search.this,MainActivity.class);
                startActivity(gotomain);
            }
        });
        alterdialog.show();

    }

}
