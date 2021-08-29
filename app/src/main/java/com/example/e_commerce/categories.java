package com.example.e_commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;

public class categories extends AppCompatActivity{
    private static final int REQUEST_CODE_SPEECH = 1000;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageButton searchvoice;
    ImageButton searchbytext;
    EditText searchedit_text;
    ImageButton barcode_search;
    ecommerceDB edb;
    ArrayAdapter<String> adapter;
    String useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        useremail=getIntent().getExtras().getString("useremail");
        edb=new ecommerceDB(this);
        sharedPreferences=getSharedPreferences("rememberme",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        searchvoice=(ImageButton)findViewById(R.id.voicesearch);
        searchbytext=(ImageButton)findViewById(R.id.searchbtn);
        searchedit_text=(EditText)findViewById(R.id.editsearch);
        barcode_search=(ImageButton)findViewById(R.id.barcode);
        ListView categorieslst=(ListView)findViewById(R.id.categorieslst);
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        categorieslst.setAdapter(adapter);
        getallcategories();
        categorieslst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent i=new Intent(categories.this,products.class);
                 i.putExtra("categoryname",((TextView)view).getText().toString());
                 i.putExtra("useremail",useremail);
                 startActivity(i);
            }
        });
        searchbytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(categories.this,products.class);
                i.putExtra("searchtext",searchedit_text.getText().toString());
                i.putExtra("useremail",useremail);
                startActivity(i);
            }
        });
        barcode_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
            }
        });
        searchvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceSearch();

            }
        });

    }

    private void getallcategories() {
        Cursor cursor=edb.showallcategories();
        while (!cursor.isAfterLast())
        {
            adapter.add(cursor.getString(0));
            cursor.moveToNext();
        }
    }

    private void scancode() {
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(capture.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    public void voiceSearch() {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"say something");

        try {
            startActivityForResult(i,REQUEST_CODE_SPEECH);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult resultintegrator=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(resultintegrator!=null)
        {
            if(resultintegrator.getContents()!=null)
            {

                searchedit_text.setText(resultintegrator.getContents());
            }
            else
            {
                Toast.makeText(this,"No Results",Toast.LENGTH_LONG).show();
            }
        }else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH:
                if(resultCode==RESULT_OK && data!=null)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchedit_text.setText(result.get(0));
                }
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                editor.clear();
                editor.apply();
                Intent i=new Intent(categories.this,login.class);
                startActivity(i);
                return true;
            case R.id.SC:
                Intent i2=new Intent(categories.this,shoppingCart.class);
                i2.putExtra("useremail",useremail);
                startActivity(i2);
                return true;
        }
        return false;
    }


}
