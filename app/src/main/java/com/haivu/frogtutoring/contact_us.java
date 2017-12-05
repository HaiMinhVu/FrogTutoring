package com.haivu.frogtutoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.haivu.frogtutoring.R.id.back;


public class contact_us extends AppCompatActivity {
    DBManager database;
    RadioGroup radiocontact;
    RadioButton radioemail, radiophone;
    TextView tvphoneoremail;
    EditText phoneemail, comment;
    Button contactcancal, contactsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        radiocontact = (RadioGroup)findViewById(R.id.radiocontact);
        radioemail = (RadioButton)findViewById(R.id.radioemail);
        radiophone = (RadioButton)findViewById(R.id.radiophone);
        tvphoneoremail = (TextView)findViewById(R.id.tvphoneoremail);
        phoneemail = (EditText)findViewById(R.id.enterphoneoremail);
        comment = (EditText)findViewById(R.id.entercomment);
        contactcancal = (Button)findViewById(R.id.contactcancael);
        contactsubmit = (Button)findViewById(R.id.contactsubmit);

        database = new DBManager(this, "frogtutors.db", null, 1);

        if (radiophone.isChecked()){
            tvphoneoremail.setText("Your Phone");
        }
        if (radioemail.isChecked()){
            tvphoneoremail.setText("Your Email");
        }

        contactsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneemail.getText().toString().equals("")){
                    Toast.makeText(contact_us.this, "Enter Your Phone or Email", Toast.LENGTH_SHORT).show();
                }
                else if (comment.getText().toString().equals("")){
                    Toast.makeText(contact_us.this, "Enter Your Comments", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.QueryData("insert into contactus values(null, '"+phoneemail.getText().toString()+"', '"+comment.getText().toString()+"')");
                    Toast.makeText(contact_us.this, "Thank You for Contacting Us, We Will Get Back to You Soon", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactcancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(contact_us.this,MainActivity.class));
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(contact_us.this,MainActivity.class);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }

}
