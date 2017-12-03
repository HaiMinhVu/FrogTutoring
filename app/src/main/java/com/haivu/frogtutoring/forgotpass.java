package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class forgotpass extends AppCompatActivity {

    DBManager database;

    EditText fgemail, fgphone;
    Button fgcancel, fgok;
    RadioButton rdfgtutor, rdfgstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        database = new DBManager(this, "frogtutors.db", null, 1);

        fgemail = (EditText)findViewById(R.id.edtfgemail);
        fgphone = (EditText)findViewById(R.id.edtfgphone);
        fgcancel = (Button)findViewById(R.id.fgcancel);
        fgok = (Button)findViewById(R.id.fgok);
        rdfgstudent = (RadioButton)findViewById(R.id.rdstudent);
        rdfgtutor = (RadioButton)findViewById(R.id.rdtutor);

        fgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2main = new Intent(forgotpass.this, MainActivity.class);
                startActivity(back2main);
            }
        });

        fgok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpass();
            }
        });

    }

    public void getpass(){
        if(rdfgstudent.isChecked()) {
            String lookupemail = fgemail.getText().toString();
            String lookupphone = fgphone.getText().toString();
            Cursor res = database.GetData("select stpass from students where stemail = '" + lookupemail + "' and stphone = '" + lookupphone + "'");

            if (res.getCount() == 0) {
                dialog_pass_found("Your password is not found");
            } else {
                StringBuffer sb = new StringBuffer();
                while (res.moveToNext()) {
                    sb.append("Your password is " + res.getString(0));
                }
                // show password
                dialog_pass_found(sb.toString());
            }
        }
        if (rdfgtutor.isChecked()){
            String lookupemail = fgemail.getText().toString();
            String lookupphone = fgphone.getText().toString();
            Cursor res = database.GetData("select tupass from tutors where tuemail = '" + lookupemail + "' and tuphone = '" + lookupphone + "'");

            if (res.getCount() == 0) {
                dialog_pass_found("Your password is not found");
            } else {
                StringBuffer sb = new StringBuffer();
                while (res.moveToNext()) {
                    sb.append("Your password is " + res.getString(0));
                }
                // show password
                dialog_pass_found(sb.toString());
            }
        }
    }

    public void dialog_pass_found(String mess){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle("Retrieve Password");
        b.setMessage(mess);
        b.show();
    }


}
