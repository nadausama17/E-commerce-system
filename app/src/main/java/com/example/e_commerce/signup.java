package com.example.e_commerce;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class signup extends AppCompatActivity  {
    TextView birthdateview;
    Button birthbtn;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        birthbtn=(Button)findViewById(R.id.birthdate_btn);
        birthdateview=(TextView)findViewById(R.id.birthdate_view);
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText email=(EditText)findViewById(R.id.email);
        final EditText pass=(EditText)findViewById(R.id.userpass);
        final EditText confpass=(EditText)findViewById(R.id.confpass);
        final EditText job=(EditText)findViewById(R.id.userjob);
        final EditText answer=(EditText)findViewById(R.id.secques);
        final RadioButton male=(RadioButton) findViewById(R.id.male);
        final RadioButton female=(RadioButton)findViewById(R.id.female);
        Button signup=(Button)findViewById(R.id.signupbtn);
        TextView gologin=(TextView)findViewById(R.id.gotologin);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final ecommerceDB edb=new ecommerceDB(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean duplicated=edb.checkduplicate_username(email.getText().toString());
                if(duplicated==true)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String gender="";
                    if(male.isChecked())
                    {
                        gender="Male";
                    }
                    else if(female.isChecked())
                    {
                        gender="Female";
                    }
                    String date="";
                    if(!birthdateview.getText().toString().equals("....-..-.."))
                    {
                        date=birthdateview.getText().toString();
                    }
                    if(pass.getText().toString().equals(confpass.getText().toString())) {
                        boolean sign_success = edb.signup(name.getText().toString(), email.getText().toString(), pass.getText().toString(), gender,date,job.getText().toString(),answer.getText().toString());
                        if(sign_success==true)
                        {
                            Toast.makeText(getApplicationContext(),"Sign up Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"You Should Fill all the Fields",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password and Confirm Password aren't the same",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signup.this,login.class);
                startActivity(i);
            }
        });
        birthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        signup.this, android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=year+"-"+month+"-"+dayOfMonth;
                if(month<10)
                {
                     date=year+"-"+"0"+month+"-"+dayOfMonth;
                }
                if(dayOfMonth<10)
                {
                    date=year+"-"+month+"-"+"0"+dayOfMonth;
                }
                if(month<10 && dayOfMonth<10)
                {
                    date=year+"-"+"0"+month+"-"+"0"+dayOfMonth;
                }

                birthdateview.setText(date);
            }
        };

    }




}
