package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class edit_tutor_profile extends AppCompatActivity {

    DBManager database;
    EditText edemail, edphone, edsubject, edbio, edprice;
    Button btupdate;
    TextView edtsignout;
    String tuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_profile);

        database = new DBManager(this, "frogtutors.db", null, 1);

        edemail = (EditText)findViewById(R.id.edemail);
        edphone = (EditText)findViewById(R.id.edphone);
        edsubject = (EditText)findViewById(R.id.edsubject);
        edbio = (EditText)findViewById(R.id.edbio);
        edprice = (EditText)findViewById(R.id.edprice);
        btupdate = (Button)findViewById(R.id.btupdate);
        edtsignout = (TextView)findViewById(R.id.edt_pro_signout);

        Intent getintent = getIntent();
        tuid = getintent.getStringExtra("tutorid");
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
        edemail.setText(sbtuemail);
        edphone.setText(sbtuphone);
        edsubject.setText(sbtusubject);
        edbio.setText(sbtubiography);
        edprice.setText(sbtuprice);

        btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edemail.getText().toString();
                String phone = edphone.getText().toString();
                String subject = edsubject.getText().toString();
                String bio = edbio.getText().toString();
                String price = edprice.getText().toString();

                database.QueryData("update tutors set tuemail = '"+email+"', tuphone = '"+phone+"', tusubject = '"+subject+"', tubiography = '"+bio+"', tuprice = '"+price+"' where tuid = '"+tuid+"'");
                Intent back2tutorprofile = new Intent(edit_tutor_profile.this, profile_tutor.class);
                back2tutorprofile.putExtra("tutorid",tuid);
                startActivity(back2tutorprofile);

            }
        });

        edtsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_tutor_profile.this, MainActivity.class));
            }
        });
    }
}
