package com.example.e_commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    CheckBox remebme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText username;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ecommerceDB edb=new ecommerceDB(this);
        username=(EditText)findViewById(R.id.loginemail);
        pass=(EditText)findViewById(R.id.loginpass);
        TextView sign=(TextView)findViewById(R.id.signup);
        final TextView forgotpass=(TextView)findViewById(R.id.forgotpass);
        remebme=(CheckBox)findViewById(R.id.rememberme);
        sharedPreferences=getSharedPreferences("rememberme",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        getsharedpreferencesData();

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(username.getText().toString().equals("")))
                {
                    Intent i=new Intent(login.this,forgetPassword.class);
                    i.putExtra("Email",username.getText().toString());
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_LONG).show();
                }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,signup.class);
                startActivity(i);
            }
        });
        final Button login=(Button)findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean l=edb.login(username.getText().toString(),pass.getText().toString());
                if(l==true)
                {
                    if(remebme.isChecked())
                    {
                        Boolean isrememchecked=remebme.isChecked();
                        editor.putString("Username",username.getText().toString());
                        editor.putString("Password",pass.getText().toString());
                        editor.putBoolean("ischecked",isrememchecked);
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"Settings have been saved",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editor.clear();
                        editor.apply();
                    }

                    Intent i=new Intent(login.this,categories.class);
                    i.putExtra("useremail",username.getText().toString());
                    startActivity(i);
                    username.getText().clear();
                    pass.getText().clear();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username or Password is wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getsharedpreferencesData() {
        SharedPreferences sp=getSharedPreferences("rememberme",MODE_PRIVATE);
        if(sp.contains("Username"))
        {
            String u=sp.getString("Username",null);
            username.setText(u);
        }
        if(sp.contains("Password"))
        {
            String p=sp.getString("Password",null);
            pass.setText(p);
        }
        if(sp.contains("ischecked"))
        {
            boolean check=sp.getBoolean("ischecked",false);
            remebme.setChecked(check);
        }
    }
}
