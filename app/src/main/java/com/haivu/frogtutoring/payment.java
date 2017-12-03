package com.haivu.frogtutoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class payment extends AppCompatActivity {

    DBManager database;
    TextView tvtotal;
    Button pay;
    int tuid, totalpayment, scheid;
    String stid, apptdate, apptstart, apptend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        database = new DBManager(this, "frogtutors.db", null, 1);
        tvtotal = (TextView)findViewById(R.id.total);
        pay = (Button)findViewById(R.id.btnpay);

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
                //Toast.makeText(payment.this, "Thank you for your payment.", Toast.LENGTH_SHORT).show();
                database.QueryData("insert into studentappointment values(null, '"+tuid+"', '"+stid+"', '"+apptdate+"', '"+apptstart+"', '"+apptend+"', 1)");
                database.QueryData("update tutorschedule set status = 0 where scheID = '"+scheid+"'");
                paymentdialog();
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

}
