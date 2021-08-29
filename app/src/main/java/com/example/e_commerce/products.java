package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class products extends AppCompatActivity {

    ListView productslst;
    String[] product_name;
    String[] product_price;
    String[] product_quantity;
    ecommerceDB edb;
    String useremail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String categoryname;
    String searchtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        sharedPreferences=getSharedPreferences("rememberme",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        useremail=getIntent().getExtras().getString("useremail");
        TextView protextview=(TextView)findViewById(R.id.productstextview);
        Button backtocat=(Button)findViewById(R.id.backtocategories);
        edb=new ecommerceDB(this);
        if(getIntent().getExtras().getString("categoryname")!=null) {
            categoryname=getIntent().getExtras().getString("categoryname");
            int catid = edb.getcateory_ID(categoryname);
            Cursor cursor = edb.show_categoryproducts(String.valueOf(catid));
            product_name = new String[cursor.getCount()];
            product_price = new String[cursor.getCount()];
            product_quantity = new String[cursor.getCount()];
            int count = 0;
            while (!cursor.isAfterLast()) {
                product_name[count] = cursor.getString(0);
                product_price[count] = String.valueOf(cursor.getInt(1));
                product_quantity[count] = String.valueOf(cursor.getInt(2));
                count++;
                cursor.moveToNext();
            }
            productslst = (ListView) findViewById(R.id.productslst);
            Myadapter adapter = new Myadapter(this, product_name, product_price, product_quantity);
            productslst.setAdapter(adapter);
        }
        else if(getIntent().getExtras().getString("searchtext")!=null)
        {
            searchtext=getIntent().getExtras().getString("searchtext");
            Cursor cursor=edb.searchforproducts(searchtext);
            if(cursor.getCount()<1)
            {
                protextview.setText("No Item Found");
            }
            else
            {
                product_name = new String[cursor.getCount()];
                product_price = new String[cursor.getCount()];
                product_quantity = new String[cursor.getCount()];
                int count = 0;
                while (!cursor.isAfterLast()) {
                    product_name[count] = cursor.getString(0);
                    product_price[count] = String.valueOf(cursor.getInt(1));
                    product_quantity[count] = String.valueOf(cursor.getInt(2));
                    count++;
                    cursor.moveToNext();
                }
                productslst = (ListView) findViewById(R.id.productslst);
                Myadapter adapter = new Myadapter(this, product_name, product_price, product_quantity);
                productslst.setAdapter(adapter);
            }
        }
        backtocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(products.this,categories.class);
                i.putExtra("useremail",useremail);
                startActivity(i);
            }
        });


    }

    class Myadapter extends ArrayAdapter<String>
    {
        Context context;
        String[] aproduct_name;
        String[] aproduct_price;
        String[] aproduct_quantity;

        Myadapter(Context c,String[] n,String[] p,String[] q)
        {
            super(c,R.layout.row,n);
            this.context=c;
            this.aproduct_name=n;
            this.aproduct_price=p;
            this.aproduct_quantity=q;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row,parent,false);
            final TextView proname=row.findViewById(R.id.productname);
            TextView proprice=row.findViewById(R.id.productprice);
            final TextView proquant=row.findViewById(R.id.productquantity);
            final EditText quantitiy=row.findViewById(R.id.quantitytocart);
            Button addtocart=row.findViewById(R.id.addtocart_btn);
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!quantitiy.getText().toString().equals(""))
                    {
                        try {

                            int q = Integer.parseInt(quantitiy.getText().toString());
                            if (q < 1 || q > (Integer.parseInt(aproduct_quantity[position]))) {
                                Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_LONG).show();
                            } else {
                                int custid = edb.getcustomerID(useremail);
                                int proid = edb.getProductID(proname.getText().toString());
                                edb.addtoShoppingcart(String.valueOf(custid), String.valueOf(proid), String.valueOf(q));
                                Toast.makeText(getApplicationContext(), "Added to Shopping cart", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(products.this,products.class);
                                if(categoryname!=null)
                                {
                                    i.putExtra("categoryname",categoryname);
                                    searchtext=null;
                                }
                                if(searchtext!=null)
                                {
                                    i.putExtra("searchtext",searchtext);
                                    categoryname=null;
                                }
                                i.putExtra("useremail",useremail);
                                startActivity(i);
                            }
                        }
                        catch(Exception e)
                        {
                            quantitiy.getText().clear();
                            Toast.makeText(getApplicationContext(),"You should enter a number",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Enter quantity of product you want to buy",Toast.LENGTH_LONG).show();
                    }
                }
            });


            proname.setText(aproduct_name[position]);
            proprice.setText("Price: "+aproduct_price[position]+" EGP");
            proquant.setText("Quantity: "+aproduct_quantity[position]);
            return row;
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
                Intent i=new Intent(products.this,login.class);
                startActivity(i);
                return true;
            case R.id.SC:
                Intent i2=new Intent(products.this,shoppingCart.class);
                i2.putExtra("useremail",useremail);
                startActivity(i2);
                return true;
        }
        return false;
    }
}
