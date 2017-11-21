package com.haivu.frogtutoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DBManager database;
    ImageView imSearch;
    EditText edtemail, edtpass;
    Button btnlogin;
    TextView tvforgot, tvsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imSearch = (ImageView) findViewById(R.id.imageSearch);
        edtemail = (EditText)findViewById(R.id.edtemail);
        edtpass = (EditText)findViewById(R.id.edtpass);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        tvforgot = (TextView)findViewById(R.id.tvforgot);
        tvsignup = (TextView)findViewById(R.id.tvsignup);

        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosearch = new Intent(MainActivity.this, Search.class);
                startActivity(gotosearch);
            }
        });

        database = new DBManager(this, "frogtutors.db", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS tutors(tuid INTEGER PRIMARY KEY AUTOINCREMENT, tuname TEXT, tusubject TEXT, tuemail TEXT, tupass TEXT, turate REAL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS students(stid INTEGER PRIMARY KEY AUTOINCREMENT, stname TEXT, stemail TEXT, stpass TEXT, stphone TEXT)");
        database.QueryData("CREATE TABLE IF NOT EXISTS review(tuid INTEGER, stid INTEGER, rate REAL, count INTEGER, PRIMARY KEY(tuid, stid))");
        database.QueryData("CREATE TABLE IF NOT EXISTS available(tuid INTEGER PRIMAEY KEY, day TEXT, time TEXT)");
        database.QueryData("CREATE TABLE IF NOT EXISTS appointment(tuid INTEGER, stid INTEGER, day TEXT, time TEXT, price REAL, PRIMARY KEY(tuid, stid))");



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(MainActivity.this, profile.class);
                startActivity(gotoprofile);
            }
        });
        

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignup = new Intent(MainActivity.this, SignUp.class);
                startActivity(gotosignup);
            }
        });

        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoforgot = new Intent(MainActivity.this, forgotpass.class);
                startActivity(gotoforgot);
            }
        });


    }
}



/***************  create tables

 database.QueryData("CREATE TABLE IF NOT EXISTS tutors(tuid INTEGER PRIMARY KEY AUTOINCREMENT, tuname TEXT, tusubject TEXT, tuemail TEXT, tupass TEXT, turate REAL)");
 database.QueryData("CREATE TABLE IF NOT EXISTS students(stid INTEGER PRIMARY KEY AUTOINCREMENT, stname TEXT, stemail TEXT, stpass TEXT, stphone TEXT)");
 database.QueryData("CREATE TABLE IF NOT EXISTS review(tuid INTEGER, stid INTEGER, rate REAL, count INTEGER, PRIMARY KEY(tuid, stid))");
 database.QueryData("CREATE TABLE IF NOT EXISTS available(tuid INTEGER PRIMAEY KEY, day TEXT, time TEXT)");
 database.QueryData("CREATE TABLE IF NOT EXISTS appointment(tuid INTEGER, stid INTEGER, day TEXT, time TEXT, price REAL, PRIMARY KEY(tuid, stid))");


 // insert data
 database.QueryData("INSERT INTO tutors VALUES(null, 'Alex Smith', 'Math', 's.alex@gmail.com', '123456', 4)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Tim McVey', 'Physic', 'tim@gmail.com', '123456', 2.5)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Jacob', 'Chemistry', 'Jacob@gmail.com', '123456', 3.5)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Sophia', 'Mechanical', 'Sophia@gmail.com', '123456', 4.5)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Evelynn', 'Literature', 'Evelynn@gmail.com', '123456', 3)");

 database.QueryData("INSERT INTO students VALUES(null, 'Igbal', 'igbal@yahoo.com', '123456', '8176453678')");
 database.QueryData("INSERT INTO students VALUES(null, 'Minh', 'minhvu@yahoo.com', '123456', '4696423678')");
 database.QueryData("INSERT INTO students VALUES(null, 'AWet', 'awet@gmail.com', '123456', '2146477678')");
 database.QueryData("INSERT INTO students VALUES(null, 'Seth', 'seth@yahoo.com', '123456', '8176678278')");
 database.QueryData("INSERT INTO students VALUES(null, 'Ann', 'ann@yahoo.com', '123456', '8776153678')");

 database.QueryData("INSERT INTO available VALUES(1, 'MON', '2pm-4pm')");
 database.QueryData("INSERT INTO available VALUES(1, 'MON', '6pm-8pm')");
 database.QueryData("INSERT INTO available VALUES(1, 'WED', '1pm-3pm')");
 database.QueryData("INSERT INTO available VALUES(1, 'FRI', '9am-11am')");
 database.QueryData("INSERT INTO available VALUES(2, 'MON', '9am-11am')");
 database.QueryData("INSERT INTO available VALUES(2, 'TUE', '11am-1pm')");
 database.QueryData("INSERT INTO available VALUES(2, 'SAT', '2pm-4pm')");
 database.QueryData("INSERT INTO available VALUES(3, 'WED', '9am-11am')");
 database.QueryData("INSERT INTO available VALUES(3, 'THU', '8am-10am')");
 database.QueryData("INSERT INTO available VALUES(3, 'FRI', '9am-11am')");
 database.QueryData("INSERT INTO available VALUES(4, 'MON', '4pm-6pm')");
 database.QueryData("INSERT INTO available VALUES(4, 'WED', '5pm-7pm')");
 database.QueryData("INSERT INTO available VALUES(4, 'MON', '4pm-6pm')");
 database.QueryData("INSERT INTO available VALUES(5, 'THU', '2pm-4pm')");
 database.QueryData("INSERT INTO available VALUES(5, 'FRI', '1pm-3pm')");
 database.QueryData("INSERT INTO available VALUES(5, 'SAT', '9am-11am')");

 database.QueryData("INSERT INTO review VALUES(1, 1, 3, 0)");
 database.QueryData("INSERT INTO review VALUES(1, 2, 4, 0)");
 database.QueryData("INSERT INTO review VALUES(1, 5, 5, 0)");
 database.QueryData("INSERT INTO review VALUES(2, 3, 1, 0)");
 database.QueryData("INSERT INTO review VALUES(2, 4, 4, 0)");
 database.QueryData("INSERT INTO review VALUES(3, 1, 4, 0)");
 database.QueryData("INSERT INTO review VALUES(3, 2, 3, 0)");
 database.QueryData("INSERT INTO review VALUES(4, 2, 4, 0)");
 database.QueryData("INSERT INTO review VALUES(4, 3, 5, 0)");
 database.QueryData("INSERT INTO review VALUES(5, 5, 3, 0)");
 *****************/