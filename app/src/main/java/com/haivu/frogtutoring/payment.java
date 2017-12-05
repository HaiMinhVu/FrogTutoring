package com.haivu.frogtutoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.haivu.frogtutoring.R.id.back;

public class payment extends BaseActivity {

    DBManager database;
    TextView tvtotal;
    Button pay, paycancel;
    int tuid, totalpayment, scheid;
    String stid, apptdate, apptstart, apptend;
    EditText editname;
    EditText editcard;
    EditText editcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        database = new DBManager(this, "frogtutors.db", null, 1);
        tvtotal = (TextView)findViewById(R.id.total);
        pay = (Button)findViewById(R.id.btnpay);
        paycancel = (Button)findViewById(R.id.btnpaycancel);
        editname = (EditText) findViewById(R.id.name);
        editcard = (EditText) findViewById(R.id.editcard);
        editcode = (EditText) findViewById(R.id.editcode);

        Intent getintent = getIntent();
        tuid = getintent.getIntExtra("tutorid",0);
        stid = getintent.getStringExtra("studentid");
        totalpayment = getintent.getIntExtra("total",0);
        apptdate = getintent.getStringExtra("apptdate");
        apptstart = getintent.getStringExtra("apptstart");
        apptend = getintent.getStringExtra("apptend");
        scheid = getintent.getIntExtra("scheid",0);

        tvtotal.setText("$"+totalpayment);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editname.length() == 0) {
                    Toast.makeText(payment.this, "Please enter name on the card", Toast.LENGTH_SHORT).show();
                }
                if (editcard.length() != 16) {
                    Toast.makeText(payment.this, "Please enter a 16-digit card number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editcode.length() != 3) {
                    Toast.makeText(payment.this, "Please enter a 3-digit security code", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    database.QueryData("insert into studentappointment values(null, '" + tuid + "', '" + stid + "', '" + apptdate + "', '" + apptstart + "', '" + apptend + "', 1)");
                    database.QueryData("update tutorschedule set status = 0 where scheID = '" + scheid + "'");
                    paymentdialog();
                }
            }
        });
        paycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoappt = new Intent(payment.this,appointment.class);
                gotoappt.putExtra("tutorid", tuid);
                gotoappt.putExtra("studentid", stid);
                startActivity(gotoappt);
            }
        });

    }

    public void paymentdialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle("Payment Status");
        b.setMessage("Pay Sucessfully.");
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotoappt = new Intent(payment.this,appointment.class);
                gotoappt.putExtra("tutorid", tuid);
                gotoappt.putExtra("studentid", stid);
                startActivity(gotoappt);
            }
        });
        b.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == back){
            Intent back2profile = new Intent(payment.this,profile.class);
            back2profile.putExtra("studentid", stid);
            startActivity(back2profile);
        }
        return super.onOptionsItemSelected(item);
    }


}
