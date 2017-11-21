package com.haivu.frogtutoring;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class forgotpass extends AppCompatActivity {

    DBManager database;

    EditText fgemail, fgphone;
    Button fgcancel, fgok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        database = new DBManager(this, "frogtutors.db", null, 1);

        fgemail = (EditText)findViewById(R.id.edtfgemail);
        fgphone = (EditText)findViewById(R.id.edtfgphone);
        fgcancel = (Button)findViewById(R.id.fgcancel);
        fgok = (Button)findViewById(R.id.fgok);

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
        String loopupemail = fgemail.getText().toString();
        String loopupphone = fgphone.getText().toString();
        Cursor res = database.GetData("select stpass from students where stemail = '"+loopupemail+"' and stphone = '"+loopupphone+"'");

        if(res.getCount() == 0){
            AlertDialog.Builder alterdialog = new AlertDialog.Builder(forgotpass.this);
            alterdialog.setCancelable(true);
            alterdialog.setTitle("Retrieve Password");
            alterdialog.setMessage("You password is not found");
            alterdialog.show();
        }
        else {
            StringBuffer sb = new StringBuffer();
            while (res.moveToNext()){
                sb.append("Your password is "+res.getString(0));
            }
            // show password
            dialog_pass_found(sb.toString());
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
