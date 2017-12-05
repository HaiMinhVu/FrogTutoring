package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.haivu.frogtutoring.R.id.back;

public class edit_tutor_profile extends BaseActivity {

    DBManager database;
    EditText edemail, edphone, edsubject, edbio, edprice;
    Button btupdate;
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(edit_tutor_profile.this,profile_tutor.class);
            back2profile.putExtra("tutorid", tuid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }

}
