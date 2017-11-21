package com.haivu.frogtutoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class profile extends AppCompatActivity {

    Button btnsearch;
    Button btnappointments;
    Button btnpayments;
    Button btnreviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnsearch = (Button)findViewById(R.id.btnSearch);
        btnappointments = (Button)findViewById(R.id.btnAppointments);
        btnpayments = (Button)findViewById(R.id.btnPayments);
        btnreviews = (Button)findViewById(R.id.btnReviews);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosearch = new Intent(profile.this,Search.class);
                startActivity(gotosearch);
            }
        });



    }
}
