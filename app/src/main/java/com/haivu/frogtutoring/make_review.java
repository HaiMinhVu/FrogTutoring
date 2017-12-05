package com.haivu.frogtutoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import static com.haivu.frogtutoring.R.id.back;

public class make_review extends BaseActivity {

    DBManager database;
    String stid, tuname;
    int apptid, tuid;

    TextView viewname;
    EditText comment;
    RatingBar rating;
    Button submit, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        database = new DBManager(this, "frogtutors.db", null, 1);

        viewname = (TextView)findViewById(R.id.mkreviewname);
        comment = (EditText)findViewById(R.id.review_comment);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        submit = (Button)findViewById(R.id.mkreviewsubmit);
        cancel = (Button)findViewById(R.id.mkreviewcancel);

        Intent getintent = getIntent();
        stid = getintent.getStringExtra("studentid");
        tuname = getintent.getStringExtra("tutorname");
        tuid = getintent.getIntExtra("tutorid",0);
        apptid = getintent.getIntExtra("apptid",0);

        viewname.setText(tuname);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2profile = new Intent(make_review.this,profile.class);
                back2profile.putExtra("studentid",stid);
                startActivity(back2profile);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int getrating = (int) rating.getRating();
                String getcomment = comment.getText().toString();
                database.QueryData("insert into review values('"+tuid+"','"+stid+"','"+getcomment+"','"+getrating+"')");
                database.QueryData("delete from studentappointment where apptID = '"+apptid+"'");
                database.QueryData("update tutors set turate = (select avg(review.rate) from review where review.tuid = '"+tuid+"') where tuid = '"+tuid+"'");
                Intent back2profile = new Intent(make_review.this,profile.class);
                back2profile.putExtra("studentid",stid);
                startActivity(back2profile);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(make_review.this,profile.class);
            back2profile.putExtra("studentid", stid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }

}
