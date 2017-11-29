package com.haivu.frogtutoring;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class appointment extends AppCompatActivity {

    DBManager database;
    ScrollView sv;
    LinearLayout ll;
    CheckBox cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // create database
        database = new DBManager(this, "frogtutors.db", null, 1);

        sv = new ScrollView(this);
        ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button button = new Button(this);

        ll.addView(button, lp);
        ll.setOrientation(LinearLayout.VERTICAL);
        button.setText("Confirm");
        sv.addView(ll);

        final Cursor cursor = database.GetData("select day, time, start, end from available where tuid = 1");

        int i = 0;
        while (cursor.moveToNext()) {
            String day = cursor.getString(0);
            String time = cursor.getString(1);
            cb = new CheckBox(this);
            cb.setText(day + " from " + time);
            cb.setId(i);
            ll.addView(cb);
            i++;
        }

        this.setContentView(sv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                cursor.moveToFirst();
                do
                {
                    cb = (CheckBox) ll.findViewById(i);
                    if(cb.isChecked())
                    {
                        int start = cursor.getInt(2);
                        int end = cursor.getInt(3);
                        MakeToast("From " + Integer.toString(start) + " to " + Integer.toString(end));
                    }
                    i++;
                }while(cursor.moveToNext());
            }
        });
    }

    public void MakeToast(String output)
    {
        Toast.makeText(appointment.this, output, Toast.LENGTH_SHORT).show();
    }
}