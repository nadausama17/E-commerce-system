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

import java.util.Calendar;


public class shoppingCart extends AppCompatActivity {

    String useremail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ecommerceDB edb;
    String[] pro_name;
    String[] pro_price;
    String[] pro_quantity;
    ListView shoppingcartlst;
    Button buy_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        sharedPreferences=getSharedPreferences("rememberme",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        useremail=getIntent().getExtras().getString("useremail");
        Button backtocat=(Button)findViewById(R.id.backfrom_shoppingcart);
        buy_btn=(Button)findViewById(R.id.buybtn);
        Button getlocation=(Button)findViewById(R.id.getloc_btn);
        edb=new ecommerceDB(this);
        int custid=edb.getcustomerID(useremail);
        int sc_id=edb.getcustShoppingcart_ID(String.valueOf(custid));
        if(sc_id>0) {
            Cursor cursor_id_quantity = edb.getShoppingcart_products(String.valueOf(sc_id));
            pro_name = new String[cursor_id_quantity.getCount()];
            pro_price = new String[cursor_id_quantity.getCount()];
            pro_quantity = new String[cursor_id_quantity.getCount()];

            int i = 0;
            while (!cursor_id_quantity.isAfterLast()) {
                Cursor cursor_name_price = edb.getproducts_name_price_quantSC(cursor_id_quantity.getString(0));
                pro_name[i] = cursor_name_price.getString(0);
                pro_price[i] = cursor_name_price.getString(1);
                pro_quantity[i] = cursor_id_quantity.getString(1);
                cursor_id_quantity.moveToNext();
                i++;
            }
            shoppingcartlst = (ListView) findViewById(R.id.shoppingcartlst);
            Myadapter adapter = new Myadapter(this, pro_name, pro_price, pro_quantity);
            shoppingcartlst.setAdapter(adapter);
        }

        backtocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(shoppingCart.this,categories.class);
                i.putExtra("useremail",useremail);
                startActivity(i);
            }
        });

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog(pro_price,pro_quantity);

            }
        });


    }

    private void opendialog(String[] price,String[] quantity) {
        float result=edb.calculate_price(price,quantity);
        String date=getOrder_date();
        Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        Intent i=new Intent(shoppingCart.this,MapsActivity.class);
        i.putExtra("useremail",useremail);
        i.putExtra("date",date);
        orderconfirm o=new orderconfirm(result,i);
        o.show(getSupportFragmentManager(),"confirm");
    }

    private String getOrder_date() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        month=month+1;
        String date=year+"-"+month+"-"+day;
        if(month<10)
        {
            date=year+"-"+"0"+month+"-"+day;
        }
        if(day<10)
        {
            date=year+"-"+month+"-"+"0"+day;
        }
        if(month<10 && day<10)
        {
            date=year+"-"+"0"+month+"-"+"0"+day;
        }
        return date;
    }

    class Myadapter extends ArrayAdapter<String>
    {
        Context context;
        String[] aproduct_name;
        String[] aproduct_price;
        String[] aproduct_quantity;

        Myadapter(Context c,String[] n,String[] p,String[] q)
        {
            super(c,R.layout.rowsc,n);
            this.context=c;
            this.aproduct_name=n;
            this.aproduct_price=p;
            this.aproduct_quantity=q;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.rowsc,parent,false);
            final TextView proname=row.findViewById(R.id.productname2);
            TextView proprice=row.findViewById(R.id.productprice2);
            final TextView proquant=row.findViewById(R.id.productquantity2);
            final EditText setquantity=row.findViewById(R.id.changequant);
            Button update_quant=row.findViewById(R.id.updatequant_btn);
            Button delete_product=row.findViewById(R.id.deleteproduct_btn);

            update_quant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int custid=edb.getcustomerID(useremail);
                    int sc_id=edb.getcustShoppingcart_ID(String.valueOf(custid));
                    int proid=edb.getProductID(aproduct_name[position]);
                    Cursor cursor=edb.getproducts_name_price_quantSC(String.valueOf(proid));
                    if(!setquantity.getText().toString().equals(""))
                    {
                        try{
                            int q = Integer.parseInt(setquantity.getText().toString());
                            if (q < 1 || q > (Integer.parseInt(cursor.getString(2)))) {
                                Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_LONG).show();
                            } else {
                                edb.update_quantity(String.valueOf(sc_id),String.valueOf(proid), String.valueOf(q));
                                proquant.setText("Quantity: "+String.valueOf(q));
                                Toast.makeText(getApplicationContext(), "Quantity updated", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch(Exception e)
                        {
                            setquantity.getText().clear();
                            Toast.makeText(getApplicationContext(),"You should enter a number",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Enter quantity of product you want to change to",Toast.LENGTH_LONG).show();
                    }
                }
            });

            delete_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int custid=edb.getcustomerID(useremail);
                    int sc_id=edb.getcustShoppingcart_ID(String.valueOf(custid));
                    int proid=edb.getProductID(aproduct_name[position]);

                    Intent i=new Intent(shoppingCart.this,shoppingCart.class);
                    i.putExtra("useremail",useremail);
                    Deleteproduct d=new Deleteproduct(sc_id,proid,edb,i);
                    d.show(getSupportFragmentManager(),"delete");

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
                Intent i=new Intent(shoppingCart.this,login.class);
                startActivity(i);
                return true;
            case R.id.SC:
                Intent i2=new Intent(shoppingCart.this,shoppingCart.class);
                i2.putExtra("useremail",useremail);
                startActivity(i2);
                return true;
        }
        return false;
    }
}
