package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        database.QueryData("CREATE TABLE IF NOT EXISTS tutors(tuid INTEGER PRIMARY KEY AUTOINCREMENT, tuname TEXT, tusubject TEXT, tubiography TEXT, tuemail TEXT, tupass TEXT, turate REAL, tuprice REAL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS students(stid INTEGER PRIMARY KEY AUTOINCREMENT, stname TEXT NOT NULL, stemail TEXT NOT NULL, stpass TEXT NOT NULL, stphone TEXT)");
        database.QueryData("CREATE TABLE IF NOT EXISTS review(tuid INTEGER, stid INTEGER, comment TEXT, rate REAL, count INTEGER, PRIMARY KEY(tuid, stid))");
        database.QueryData("CREATE TABLE IF NOT EXISTS available(tuid INTEGER PRIMAEY KEY, day TEXT, time TEXT, start INTEGER, end INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS apipointment(tuid INTEGER, stid INTEGER, day TEXT, time TEXT, price REAL, PRIMARY KEY(tuid, stid))");
        

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
        Cursor loginID = database.GetData("select stid from students where stemail = '"+email+"' and stpass = '"+pass+"'");
        StringBuffer sb = new StringBuffer();
        if(loginID.getCount() > 0){
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
            while (loginID.moveToNext()){
                sb.append(loginID.getString(0));
            }
            String stid = sb.toString();
            Intent gotoprofile = new Intent(MainActivity.this,profile.class);
            gotoprofile.putExtra("studentid",stid);
            startActivity(gotoprofile);
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong email/password", Toast.LENGTH_SHORT).show();
            edtemail.setText("");
            edtpass.setText("");
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



/***************  insert data

 database.QueryData("INSERT INTO tutors VALUES(null, 'Alex Smith', 'Computer Science', 'I have a master degree in computer science (Information Security). My associate and bachelor degrees are in computer science as well (Software engineering). With more than 14 years of experience in computer science.', 's.alex@gmail.com', '123456', 4, 40)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Tim McVey', 'Physic', 'I have had a career in astronomy which included Hubble Space Telescope operations, where I became an expert in Excel and SQL, and teaching college-level astronomy and physics.', 'tim@gmail.com', '123456', 2.5, 50)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Jacob', 'Chemistry', 'I have used many aspects of chemistry in my career and understand molecular formulae, moles and such. Since 2010 I have successfully tutored many students in general or PAP high school courses.', 'Jacob@gmail.com', '123456', 3.5, 60)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Sophia', 'Mechanical/Aerospace', 'I graduated last May from Arizona State University with a Bachelor degree in Mechanical Engineering. During my time there I was a Teaching Assistant for an introductory engineering class that included teaching basic engineering principles and hands on design.', 'Sophia@gmail.com', '123456', 4.5, 35)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Evelynn', 'Computer Science', 'I have 30 college hours in Computer Science including some Computer Science engineering. For 5 years I did highest level technical support, computer repairing & building. For the past 20 years, I have been using computers between 20 & 40 hours per week for work & home.', 'Evelynn@gmail.com', '123456', 3, 45)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Mica Johnson', 'Math', 'Hello! My name is Mica and I am an Advanced Statistics Tutor and Online Academic Coach specializing in helping students taking statistics courses or working on statistical analysis projects.', 'j.alex@gmail.com', '123456', null, 45)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Williams Smith', 'Chemistry', 'For college students, I can assist with Chemistry to Science and non-Science majors. For high school students, I can help with AP Chemistry and regular Chemistry.', 's.williams@gmail.com', '123456', null, 40)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Alex Donn', 'Physic', 'My goal is to offer high quality, affordable tutoring to help students master the subjects of Math and Physics.', 'ddonn@gmail.com', '123456', null, 35)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'White Harris', 'Music', '11 years ago, I picked up a guitar and began my life long pursuit of music. This pursuit has led me in a wide variety of directions.', 'harriswhite@gmail.com', '123456', null, 50)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Jackson Martin', 'Biology', 'I serve as Adjunct Professor of Biology at a community college. I earned a Master in Medical Science from UNT Health Science Center and graduated with a double major in Philosophy and Biology from Texas Christian University.', 'jm1234@gmail.com', '123456', null, 45)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Clark Rodiguez', 'Mechanical/Aerospace', 'I love learning, and firmly believe that while few things are more frustrating than failing to get it, the fastest way to gain knowledge and understanding of a concept is to talk to a person who knows what you want to know and understands what you want to understand.', 'RClark@gmail.com', '123456', null, 55)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Kiarash Lopez', 'Mechanical/Aerospace', 'Hello, my name is Kiarash. I recently graduated with a bachelor degree in Aerospace Engineering from the Texas Christian University. I am currently pursuing a master degree', 'kiarash@gmail.com', '123456', null, 35)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Scott Green', 'Physic', 'With over 40 years in the classroom, I have worked with students at many levels. I still attend professional meetings to make sure that I have sharp skills to handle today curriculum demands.', 'scott142@gmail.com', '123456', null, 40)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Baker Carter', 'Chemistry', 'I taught Chemistry at the Academic Institute, Inc. in Bellevue, WA during the 11 - 12 school year. He has also been an active chemistry tutor since summer of 2009.', 'baker@gmail.com', '123456', null, 25)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Robert Turner', 'Math', 'I am a college mathematics professor with over twenty years experience in healthcare consulting. I have tutored students from 5th grade math to college level courses.', 's.alex@gmail.com', '123456', null, 50)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Edward Nguyen', 'Math', 'Math is like a playground for your mind. Developing sharp math skills helps students improve logical reasoning and critical thinking skills. From a young age, I have always enjoyed learning and doing math.', 's.alex@gmail.com', '123456', null, 45)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Edward Rogers', 'Physic', 'I enjoy seeing students achieve competency in applying conceptual understanding and logical analysis to problem-solving, so it will be the focus of the tutoring sessions.', 's.alex@gmail.com', '123456', null, 40)");
 database.QueryData("INSERT INTO tutors VALUES(null, 'Bailey White', 'Computer Science', 'After practicing Computer Science in industry and product development for 8 years I returned to university for an MS in Computer Science at The University of Texas at Dallas. BSCS in 1988 summa cum laude, MSCS in 1990, plus additional graduate work.', 's.alex@gmail.com', '123456', null, 35)");

 database.QueryData("INSERT INTO students VALUES(null, 'Iqbal', 'iqbal@yahoo.com', '123456', '8176453678')");
 database.QueryData("INSERT INTO students VALUES(null, 'John', 'john@yahoo.com', '123456', '4696423678')");
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

 database.QueryData("INSERT INTO review VALUES(1, 1, 'fair tutor, would recommend this', 3, 0)");
 database.QueryData("INSERT INTO review VALUES(1, 2, 'Great tutor, knows everything you would ask', 4, 0)");
 database.QueryData("INSERT INTO review VALUES(1, 5, 'Very detail explaination and prepared for before tutoring',5, 0)");
 database.QueryData("INSERT INTO review VALUES(2, 3, 'Bad tutor, would not recommend this guy',1, 0)");
 database.QueryData("INSERT INTO review VALUES(2, 4, 'so far so good with this tutor',4, 0)");
 database.QueryData("INSERT INTO review VALUES(3, 1, 'Make an A for the test after taking this tutor',4, 0)");
 database.QueryData("INSERT INTO review VALUES(3, 2, 'not really good, but ok',3, 0)");
 database.QueryData("INSERT INTO review VALUES(4, 2, 'good and fair price tutor',4, 0)");
 database.QueryData("INSERT INTO review VALUES(4, 3, 'great tutor ever',5, 0)");
 database.QueryData("INSERT INTO review VALUES(5, 5, 'this guy is good, but not really an expert',3, 0)");

 *****************/