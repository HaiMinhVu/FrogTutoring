package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    DBManager database;
    ImageView imSearch;
    EditText edtemail, edtpass;
    Button btnlogin;
    TextView tvforgot, tvsignup;
    CheckBox cbremember;
    UserSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        session = new UserSession(this);

        edtemail.setText(session.prefs.getString("username",""));
        edtpass.setText(session.prefs.getString("pass",""));
        cbremember.setChecked(session.prefs.getBoolean("checked", false));

        database = new DBManager(this, "frogtutors.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tutors(tuid INTEGER PRIMARY KEY AUTOINCREMENT, tuname TEXT, tusubject TEXT, tubiography TEXT, tuemail TEXT, tupass TEXT, tuphone TEXT, turate REAL, tuprice REAL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS students(stid INTEGER PRIMARY KEY AUTOINCREMENT, stname TEXT NOT NULL, stemail TEXT NOT NULL, stpass TEXT NOT NULL, stphone TEXT)");
        database.QueryData("CREATE TABLE IF NOT EXISTS review(tuid INTEGER, stid INTEGER, comment TEXT, rate INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS tutorschedule(scheID INTEGER PRIMARY KEY AUTOINCREMENT, tuid INTEGER, date TEXT, starttime TEXT, endtime TEXT, duration REAL, status INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS studentappointment(apptID INTEGER PRIMARY KEY AUTOINCREMENT, tuid INTEGER, stid INTEGER, apptdate TEXT, apptstart TEXT, apptend TEXT, apptstatus INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS contactus(ctid INTEGER PRIMARY KEY AUTOINCREMENT, phoneoremail TEXT, comment TEXT)");

        /*database.QueryData("INSERT INTO tutors VALUES(null, 'Alex Smith', 'science', 'I have a master degree in computer science (Information Security). My associate and bachelor degrees are in computer science as well (Software engineering). With more than 14 years of experience in computer science.', 's.alex@gmail.com', '123456', '8171234672', 4, 40)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Tim McVey', 'physic', 'I have had a career in astronomy which included Hubble Space Telescope operations, where I became an expert in Excel and SQL, and teaching college-level astronomy and physics.', 'tim@gmail.com', '123456', '4694628475', 2.5, 50)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Jacob', 'chemistry', 'I have used many aspects of chemistry in my career and understand molecular formulae, moles and such. Since 2010 I have successfully tutored many students in general or PAP high school courses.', 'Jacob@gmail.com', '123456', '7163627598', 3.5, 60)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Sophia', 'science', 'I graduated last May from Arizona State University with a Bachelor degree in Mechanical Engineering. During my time there I was a Teaching Assistant for an introductory engineering class that included teaching basic engineering principles and hands on design.', 'Sophia@gmail.com', '123456', '2175627495', 4.5, 35)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Evelynn', 'science', 'I have 30 college hours in Computer Science including some Computer Science engineering. For 5 years I did highest level technical support, computer repairing & building. For the past 20 years, I have been using computers between 20 & 40 hours per week for work & home.', 'Evelynn@gmail.com', '123456', '9182637465', 3, 45)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Mica Johnson', 'math', 'Hello! My name is Mica and I am an Advanced Statistics Tutor and Online Academic Coach specializing in helping students taking statistics courses or working on statistical analysis projects.', 'j.alex@gmail.com', '123456', '7163648279', null, 45)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Williams Smith', 'chemistry', 'For college students, I can assist with Chemistry to Science and non-Science majors. For high school students, I can help with AP Chemistry and regular Chemistry.', 's.williams@gmail.com', '123456', '8270184609', null, 40)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Alex Donn', 'physic', 'My goal is to offer high quality, affordable tutoring to help students master the subjects of Math and Physics.', 'ddonn@gmail.com', '123456', '8172635490', null, 35)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'White Harris', 'music', '11 years ago, I picked up a guitar and began my life long pursuit of music. This pursuit has led me in a wide variety of directions.', 'harriswhite@gmail.com', '123456', '6824802984', null, 50)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Jackson Martin', 'biology', 'I serve as Adjunct Professor of Biology at a community college. I earned a Master in Medical Science from UNT Health Science Center and graduated with a double major in Philosophy and Biology from Texas Christian University.', 'jm1234@gmail.com', '123456', '6827481098', null, 45)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Clark Rodiguez', 'mechanical', 'I love learning, and firmly believe that while few things are more frustrating than failing to get it, the fastest way to gain knowledge and understanding of a concept is to talk to a person who knows what you want to know and understands what you want to understand.', 'RClark@gmail.com', '7163892098', '123456', null, 55)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Kiarash Lopez', 'mechanical', 'Hello, my name is Kiarash. I recently graduated with a bachelor degree in Aerospace Engineering from the Texas Christian University. I am currently pursuing a master degree', 'kiarash@gmail.com', '123456', '8291736253', null, 35)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Scott Green', 'physic', 'With over 40 years in the classroom, I have worked with students at many levels. I still attend professional meetings to make sure that I have sharp skills to handle today curriculum demands.', 'scott142@gmail.com', '123456', '7829104567', null, 40)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Baker Carter', 'chemistry', 'I taught Chemistry at the Academic Institute, Inc. in Bellevue, WA during the 11 - 12 school year. He has also been an active chemistry tutor since summer of 2009.', 'baker@gmail.com', '123456', '7189462553', null, 25)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Robert Turner', 'math', 'I am a college mathematics professor with over twenty years experience in healthcare consulting. I have tutored students from 5th grade math to college level courses.', 's.alex@gmail.com', '123456', '8881716253', null, 50)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Edward Nguyen', 'math', 'Math is like a playground for your mind. Developing sharp math skills helps students improve logical reasoning and critical thinking skills. From a young age, I have always enjoyed learning and doing math.', 's.alex@gmail.com', '123456', '1672645559', null, 45)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Edward Rogers', 'physic', 'I enjoy seeing students achieve competency in applying conceptual understanding and logical analysis to problem-solving, so it will be the focus of the tutoring sessions.', 's.alex@gmail.com', '123456', '9098776655', null, 40)");
        database.QueryData("INSERT INTO tutors VALUES(null, 'Bailey White', 'science', 'After practicing Computer Science in industry and product development for 8 years I returned to university for an MS in Computer Science at The University of Texas at Dallas. BSCS in 1988 summa cum laude, MSCS in 1990, plus additional graduate work.', 's.alex@gmail.com', '2345678765', '123456', null, 35)");

        database.QueryData("INSERT INTO students VALUES(null, 'Iqbal', 'iqbal@yahoo.com', '123456', '8176453678')");
        database.QueryData("INSERT INTO students VALUES(null, 'Minh', 'minh@email.com', '123456', '6825594341')");
        database.QueryData("INSERT INTO students VALUES(null, 'AWet', 'awet@gmail.com', '123456', '2146477678')");
        database.QueryData("INSERT INTO students VALUES(null, 'Seth', 'seth@yahoo.com', '123456', '8176678278')");
        database.QueryData("INSERT INTO students VALUES(null, 'Ann', 'ann@yahoo.com', '123456', '8776153678')");*/


        //toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosearch = new Intent(MainActivity.this, Search.class);
                session.setLoggedin(false);
                startActivity(gotosearch);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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

    public void login(){
        String email = edtemail.getText().toString();
        String pass = edtpass.getText().toString();
        Cursor stloginID = database.GetData("select stid from students where stemail = '"+email+"' and stpass = '"+pass+"'");
        Cursor tuloginID = database.GetData("select tuid from tutors where tuemail = '"+email+"' and tupass = '"+pass+"'");
        StringBuffer sb = new StringBuffer();

        if(stloginID.getCount() == 0 && tuloginID.getCount() == 0){
            Toast.makeText(MainActivity.this, "Wrong email/password", Toast.LENGTH_SHORT).show();
            edtemail.setText("");
            edtpass.setText("");
        }
        if(stloginID.getCount() > 0 && tuloginID.getCount() == 0){
            session.setLoggedin(true);
            if(cbremember.isChecked()) {
                session.editor.putString("username", email);
                session.editor.putString("pass", pass);
                session.editor.putBoolean("checked", true);
                session.editor.commit();
            }
            else {
                session.editor.clear();
                session.editor.commit();
            }
            while (stloginID.moveToNext()){
                sb.append(stloginID.getString(0));
            }
            String stid = sb.toString();
            Intent gotoprofile = new Intent(MainActivity.this,profile.class);
            gotoprofile.putExtra("studentid",stid);
            startActivity(gotoprofile);
        }
        if(tuloginID.getCount() > 0 && stloginID.getCount() == 0){
            session.setLoggedin(true);
            if(cbremember.isChecked()) {
                session.editor.putString("username", email);
                session.editor.putString("pass", pass);
                session.editor.putBoolean("checked", true);
                session.editor.commit();
            }
            else {
                session.editor.clear();
                session.editor.commit();
            }
            while (tuloginID.moveToNext()){
                sb.append(tuloginID.getString(0));
            }
            String tuid = sb.toString();
            Intent gotoprofiletutor = new Intent(MainActivity.this,profile_tutor.class);
            gotoprofiletutor.putExtra("tutorid",tuid);
            startActivity(gotoprofiletutor);
        }
    }

    private void init(){
        imSearch = (ImageView) findViewById(R.id.imageSearch);
        edtemail = (EditText)findViewById(R.id.edtemail);
        edtpass = (EditText)findViewById(R.id.edtpass);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        tvforgot = (TextView)findViewById(R.id.tvforgot);
        tvsignup = (TextView)findViewById(R.id.tvsignup);
        cbremember = (CheckBox)findViewById(R.id.cbremember);
    }
}
