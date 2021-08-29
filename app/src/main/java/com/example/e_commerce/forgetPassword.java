package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class forgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final TextView email=(TextView)findViewById(R.id.emailforgotpass);
        email.setText(getIntent().getExtras().getString("Email"));
        final EditText answer=(EditText)findViewById(R.id.answerforgotpass);
        final EditText newpass=(EditText)findViewById(R.id.newpass);
        final EditText confnewpass=(EditText)findViewById(R.id.confnewpass);
        final ecommerceDB edb=new ecommerceDB(this);
        Button reset=(Button)findViewById(R.id.resetpassbtn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(answer.getText().toString().equals("")) && !(newpass.getText().toString().equals("")) && !(confnewpass.getText().toString().equals("")))
                {
                    String questionanswer=edb.getsecquesanswer(email.getText().toString());
                    if(questionanswer.equals(answer.getText().toString()))
                    {
                        if(newpass.getText().toString().equals(confnewpass.getText().toString()))
                        {
                            edb.updatepassword(email.getText().toString(),newpass.getText().toString());
                            Toast.makeText(getApplicationContext(),"UPDATED",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(forgetPassword.this,login.class);
                            startActivity(i);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"New Password and Confirm Password aren't the same",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Your answer is wrong",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Fill All The Fields",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
