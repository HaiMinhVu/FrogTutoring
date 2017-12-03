package com.haivu.frogtutoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DBManager database;

    EditText name, email, pass, repass, phone;
    Button signup, cancel;
    RadioGroup radiosignup;
    RadioButton radiostudent, radiotutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = new DBManager(this, "frogtutors.db", null, 1);

        name = (EditText)findViewById(R.id.edtname);
        email = (EditText)findViewById(R.id.edtemail);
        pass = (EditText)findViewById(R.id.edtpass);
        repass = (EditText)findViewById(R.id.edtrepass);
        phone = (EditText)findViewById(R.id.edtphone);
        signup = (Button) findViewById(R.id.btnsignup);
        cancel = (Button)findViewById(R.id.btncancel);
        radiosignup = (RadioGroup)findViewById(R.id.radiosignup);
        radiostudent = (RadioButton)findViewById(R.id.radiostudent);
        radiotutor = (RadioButton)findViewById(R.id.radiotutor);

        // sign up student and insert to database and go back to main page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pass.getText().toString().equals(repass.getText().toString())){
                    Toast.makeText(SignUp.this, "Password not match !!!", Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString().equals("")){
                    Toast.makeText(SignUp.this, "Enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().equals("")){
                    Toast.makeText(SignUp.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(pass.getText().toString().equals("")){
                    Toast.makeText(SignUp.this, "Enter your password", Toast.LENGTH_SHORT).show();
                }
                else if(phone.getText().toString().equals("")){
                    Toast.makeText(SignUp.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(radiostudent.isChecked()){
                        boolean isinserted = database.insertStudent(
                                name.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString(),
                                phone.getText().toString());
                        if(isinserted = true){
                            successSignup();
                        }
                        else {
                            Toast.makeText(SignUp.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(radiotutor.isChecked()){
                        boolean isinserted = database.insertTutor(
                                name.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString(),
                                phone.getText().toString());
                        if(isinserted = true){
                            successSignup();
                        }
                        else {
                            Toast.makeText(SignUp.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

        // cancel button will go back to main page
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(SignUp.this, MainActivity.class);
                startActivity(gotomain);
            }
        });

    }

    private void successSignup(){
        AlertDialog.Builder alterdialog = new AlertDialog.Builder(this);
        alterdialog.setTitle("Congratulation");
        alterdialog.setMessage("You have successfully registered !!!");
        alterdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent gotomain = new Intent(SignUp.this,MainActivity.class);
                startActivity(gotomain);
            }
        });
        alterdialog.show();
    }

}
