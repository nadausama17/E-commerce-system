package com.example.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ecommerceDB extends SQLiteOpenHelper {
    private static String databasename="ecommerce_db";
    SQLiteDatabase ecommercedb;
    public ecommerceDB(Context context)
    {
        super(context,databasename,null,5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customers(CustID integer primary key autoincrement,CutName text not null,Username text not null," +
                "Password text not null,Gender text not null,Birthdate date not null,Job text not null,Securityques text not null)");
        db.execSQL("create table Categories(CatID integer primary key autoincrement,CatName text not null)");

        db.execSQL("create table Orders(OrdID integer primary key autoincrement,OrdDate date,Address text,Custid integer," +
                "foreign key(Custid)REFERENCES Customers(CustID))");
        db.execSQL("create table Products(ProID integer primary key autoincrement,ProName text not null,Price integer not null," +
                "Quantity integer not null,Catid integer,foreign key(Catid)REFERENCES Categories(CatID))");

        db.execSQL("create table OrderDetails(Ordid integer not null,Proid integer not null,Quantity integer not null," +
                "primary key(Ordid,Proid),foreign key(Ordid)REFERENCES Orders(OrdID),foreign key(Proid)REFERENCES Products(ProID))");

        ContentValues row=new ContentValues();
        row.put("CutName","Nada Usama");
        row.put("Username","nadaua2@gmail.com");
        row.put("Password","1234");
        row.put("Gender","Female");
        row.put("Birthdate","2000-02-17");
        row.put("Job","student");
        row.put("Securityques","Hager Saber");
        db.insert("Customers",null,row);

        ContentValues row2=new ContentValues();
        row2.put("CatName","Fashion");
        db.insert("Categories",null,row2);

        row2.put("CatName","Food");
        db.insert("Categories",null,row2);

        row2.put("CatName","Laptops");
        db.insert("Categories",null,row2);

        row2.put("CatName","Home devices");
        db.insert("Categories",null,row2);

        row2.put("CatName","Accessories");
        db.insert("Categories",null,row2);

        ContentValues row3=new ContentValues();
        row3.put("ProName","Black pants");
        row3.put("Price",150);
        row3.put("Quantity",30);
        row3.put("Catid",1);
        db.insert("Products",null,row3);

        row3.put("ProName","Long grey coat");
        row3.put("Price",500);
        row3.put("Quantity",20);
        row3.put("Catid",1);
        db.insert("Products",null,row3);

        row3.put("ProName","Dark red skirt");
        row3.put("Price",115);
        row3.put("Quantity",40);
        row3.put("Catid",1);
        db.insert("Products",null,row3);

        row3.put("ProName","Blue dress");
        row3.put("Price",300);
        row3.put("Quantity",10);
        row3.put("Catid",1);
        db.insert("Products",null,row3);

        row3.put("ProName","High collar pullover");
        row3.put("Price",150);
        row3.put("Quantity",30);
        row3.put("Catid",1);
        db.insert("Products",null,row3);

        row3.put("ProName","Italiano pasta");
        row3.put("Price",8);
        row3.put("Quantity",40);
        row3.put("Catid",2);
        db.insert("Products",null,row3);

        row3.put("ProName","Crystal sunflower oil- 1 Liter");
        row3.put("Price",25);
        row3.put("Quantity",60);
        row3.put("Catid",2);
        db.insert("Products",null,row3);

        row3.put("ProName","Rice - 1 kg");
        row3.put("Price",20);
        row3.put("Quantity",50);
        row3.put("Catid",2);
        db.insert("Products",null,row3);

        row3.put("ProName","Brown flour");
        row3.put("Price",50);
        row3.put("Quantity",30);
        row3.put("Catid",2);
        db.insert("Products",null,row3);

        row3.put("ProName","Sugar");
        row3.put("Price",21);
        row3.put("Quantity",70);
        row3.put("Catid",2);
        db.insert("Products",null,row3);

        row3.put("ProName","Lenovo 155");
        row3.put("Price",8000);
        row3.put("Quantity",10);
        row3.put("Catid",3);
        db.insert("Products",null,row3);

        row3.put("ProName","DELL 3580");
        row3.put("Price",7000);
        row3.put("Quantity",15);
        row3.put("Catid",3);
        db.insert("Products",null,row3);

        row3.put("ProName","HP probook 450 G7");
        row3.put("Price",15500);
        row3.put("Quantity",8);
        row3.put("Catid",3);
        db.insert("Products",null,row3);

        row3.put("ProName","DELL latitude 3400");
        row3.put("Price",16000);
        row3.put("Quantity",12);
        row3.put("Catid",3);
        db.insert("Products",null,row3);

        row3.put("ProName","Lenovo IdeaPad L3");
        row3.put("Price",9400);
        row3.put("Quantity",17);
        row3.put("Catid",3);
        db.insert("Products",null,row3);

        row3.put("ProName","Zanussi no frost refrigerator");
        row3.put("Price",5850);
        row3.put("Quantity",5);
        row3.put("Catid",4);
        db.insert("Products",null,row3);

        row3.put("ProName","White point dishwasher");
        row3.put("Price",4600);
        row3.put("Quantity",7);
        row3.put("Catid",4);
        db.insert("Products",null,row3);

        row3.put("ProName","Toshiba washing machine");
        row3.put("Price",4400);
        row3.put("Quantity",6);
        row3.put("Catid",4);
        db.insert("Products",null,row3);

        row3.put("ProName","Electrostar deep freezer");
        row3.put("Price",4000);
        row3.put("Quantity",7);
        row3.put("Catid",4);
        db.insert("Products",null,row3);

        row3.put("ProName","Scarf");
        row3.put("Price",50);
        row3.put("Quantity",20);
        row3.put("Catid",5);
        db.insert("Products",null,row3);

        row3.put("ProName","Leather Belt-black");
        row3.put("Price",100);
        row3.put("Quantity",15);
        row3.put("Catid",5);
        db.insert("Products",null,row3);

        row3.put("ProName","Ice cap");
        row3.put("Price",80);
        row3.put("Quantity",25);
        row3.put("Catid",5);
        db.insert("Products",null,row3);

        row3.put("ProName","Cat eye sunglasses");
        row3.put("Price",45);
        row3.put("Quantity",10);
        row3.put("Catid",5);
        db.insert("Products",null,row3);

        row3.put("ProName","Gloves");
        row3.put("Price",70);
        row3.put("Quantity",13);
        row3.put("Catid",5);
        db.insert("Products",null,row3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        onCreate(db);
    }

    /////////login and forgot password///////////////////////
    public void updatepassword(String username,String newpassword)
    {
        ecommercedb=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Password",newpassword);
        ecommercedb.update("Customers",row,"Username like ?",new String[]{username});
        ecommercedb.close();
    }
    public String getsecquesanswer(String username)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={username};
        Cursor cursor=ecommercedb.rawQuery("select Securityques from Customers where Username like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor.getString(0);
    }
    public boolean login(String username,String password)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={username,password};
        Cursor cursor=ecommercedb.rawQuery("select * from Customers where Username like ? and Password like ?",arg);
        if(cursor.getCount()>0)
        {
            ecommercedb.close();
            return true;
        }
        ecommercedb.close();
        return false;
    }
    //////sign up functions/////////////////////////
    public boolean signup(String customername,String username,String password,String gender,String birthdate,String job,String answer)
    {
         if(customername.equals("") || username.equals("") || password.equals("") || gender.equals("") || birthdate.equals("") || job.equals("") || answer.equals(""))
         {
             return false;
         }
         ContentValues row=new ContentValues();
         row.put("CutName",customername);
         row.put("Username",username);
         row.put("Password",password);
         row.put("Gender",gender);
         row.put("Birthdate",birthdate);
         row.put("Job",job);
         row.put("Securityques",answer);
         ecommercedb=getWritableDatabase();
         ecommercedb.insert("Customers",null,row);
         ecommercedb.close();
         return true;

    }
    public boolean checkduplicate_username(String username)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={username};
        Cursor cursor=ecommercedb.rawQuery("select * from Customers where Username like ?",arg);
        if(cursor.getCount()==1)
        {
            ecommercedb.close();
            return true;
        }
        ecommercedb.close();
        return false;
    }
    ///////////////////////////Categories////////////////////////////////
    public Cursor showallcategories()
    {
        ecommercedb=getReadableDatabase();
        Cursor cursor=ecommercedb.rawQuery("select CatName from Categories",null);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor;
    }
    public Cursor searchforproducts(String text)
    {
       ecommercedb=getReadableDatabase();
       String[] arg={text};
       Cursor cursor=ecommercedb.rawQuery("select ProName,Price,Quantity from Products where ProName like '%'||?||'%'",arg);
       cursor.moveToFirst();
       ecommercedb.close();
       return cursor;
    }
    ///////////////////products////////////////////////////
    public int getcateory_ID(String categoryname)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={categoryname};
        Cursor cursor=ecommercedb.rawQuery("select CatID from Categories where CatName like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor.getInt(0);
    }
    public Cursor show_categoryproducts(String cateogry_id)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={cateogry_id};
        Cursor cursor=ecommercedb.rawQuery("select ProName,Price,Quantity from Products where Catid like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor;
    }
    public int getcustomerID(String email)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={email};
        Cursor cursor=ecommercedb.rawQuery("select CustID from Customers where Username like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor.getInt(0);
    }
    public int getProductID(String name)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={name};
        Cursor cursor=ecommercedb.rawQuery("select ProID from Products where ProName like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor.getInt(0);
    }
    public void addtoShoppingcart(String custid,String prodid,String quantity)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={custid};
        Cursor cursor=ecommercedb.rawQuery("select OrdID from Orders where Custid like ? and Address IS NULL and OrdDate IS NULL",arg);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            insertinto_existSC(String.valueOf(cursor.getInt(0)),prodid,quantity);
        }
        else
        {
            insert_SC(custid,prodid,quantity);
        }
        ecommercedb.close();
    }
    public void insertinto_existSC(String ordid,String prodid,String quantity)
    {
       /* create table OrderDetails(Ordid integer not null,Proid integer not null,Quantity integer not null," +
        "primary key(Ordid,Proid),foreign key(Ordid)REFERENCES Orders(OrdID),foreign key(Proid)REFERENCES Products(ProID))*/
        ecommercedb=getReadableDatabase();
        String[] arg={ordid,prodid};
        Cursor cursor=ecommercedb.rawQuery("select Quantity from OrderDetails where Ordid like ? and Proid like ?",arg);
        cursor.moveToFirst();
        String[] arg1={prodid};
        Cursor cursor1=ecommercedb.rawQuery("select Quantity from Products where  ProID like ?",arg1);
        cursor1.moveToFirst();
        if(cursor.getCount()>0)
        {
            int q=cursor.getInt(0)+Integer.parseInt(quantity);
            update_quantity(ordid,prodid,String.valueOf(q));

            int q1=cursor1.getInt(0)-Integer.parseInt(quantity);
            reduce_product_quantity(prodid,String.valueOf(q1));
        }
        else {
            ContentValues row = new ContentValues();
            row.put("Ordid", ordid);
            row.put("Proid", prodid);
            row.put("Quantity", quantity);
            ecommercedb = getWritableDatabase();
            ecommercedb.insert("OrderDetails", null, row);
            int q1=cursor1.getInt(0)-Integer.parseInt(quantity);
            reduce_product_quantity(prodid,String.valueOf(q1));
            ecommercedb.close();
        }
    }
    public void insert_SC(String custid,String prodid,String quantity)
    {
        ContentValues row=new ContentValues();
        row.put("Custid",custid);
        ecommercedb=getWritableDatabase();
        ecommercedb.insert("Orders",null,row);
        ecommercedb.close();
        /////insert into OrderDetails/////////
        ecommercedb=getReadableDatabase();
        String[] arg={custid};
        Cursor cursor=ecommercedb.rawQuery("select OrdID from Orders where Custid like ? and Address IS NULL and OrdDate IS NULL",arg);
        cursor.moveToFirst();
        insertinto_existSC(String.valueOf(cursor.getInt(0)),prodid,quantity);
        ecommercedb.close();
    }
   /* db.execSQL("create table Products(ProID integer primary key autoincrement,ProName text not null,Price integer not null," +
            "Quantity integer not null,Catid integer,foreign key(Catid)REFERENCES Categories(CatID))");*/
    public void reduce_product_quantity(String proid,String quantity)
    {
        ecommercedb=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Quantity",quantity);
        ecommercedb.update("Products",row,"ProID like ?",new String[]{proid});
        ecommercedb.close();
    }

    /////////////////////////Shopping cart/////////////////////////////////
    public int getcustShoppingcart_ID(String custid)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={custid};
        Cursor cursor=ecommercedb.rawQuery("select OrdID from Orders where Custid like ? and Address IS NULL and OrdDate IS NULL",arg);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
        }
        else
        {
            return 0;
        }
        ecommercedb.close();
        return cursor.getInt(0);
    }
    public Cursor getShoppingcart_products(String sc_id)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={sc_id};
        Cursor cursor=ecommercedb.rawQuery("select Proid,Quantity from OrderDetails where Ordid like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor;
    }
    public Cursor getproducts_name_price_quantSC(String proid)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={proid};
        Cursor cursor=ecommercedb.rawQuery("select ProName,Price,Quantity from Products where ProID like ?",arg);
        cursor.moveToFirst();
        ecommercedb.close();
        return cursor;
    }
    public void update_quantity(String Sc_id,String proid,String quantity)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={Sc_id,proid};
        Cursor cursor=ecommercedb.rawQuery("select Quantity from OrderDetails where Ordid like ? and Proid like ?",arg);
        cursor.moveToFirst();
        if(Integer.parseInt(quantity)>cursor.getInt(0))
        {
            int q=Integer.parseInt(quantity)-cursor.getInt(0);
            String[] arg2={proid};
            Cursor cursor2=ecommercedb.rawQuery("select Quantity from Products where ProID like ?",arg2);
            cursor2.moveToFirst();
            int newquantity=cursor2.getInt(0)-q;
            reduce_product_quantity(proid,String.valueOf(newquantity));
        }
        else if(Integer.parseInt(quantity)<cursor.getInt(0))
        {
            int q=cursor.getInt(0)-Integer.parseInt(quantity);
            String[] arg2={proid};
            Cursor cursor2=ecommercedb.rawQuery("select Quantity from Products where ProID like ?",arg2);
            cursor2.moveToFirst();
            int newquantity=cursor2.getInt(0)+q;
            reduce_product_quantity(proid,String.valueOf(newquantity));
        }

        ecommercedb=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Quantity",quantity);
        ecommercedb.update("OrderDetails",row,"Ordid like ? and Proid like ?",new String[]{Sc_id,proid});
        ecommercedb.close();
    }
    //Ordid integer not null,Proid
    public void delete_product(String sc_id,String proid)
    {
        ecommercedb=getReadableDatabase();
        String[] arg={sc_id,proid};
        Cursor cursor=ecommercedb.rawQuery("select Quantity from OrderDetails where Ordid like ? and Proid like ?",arg);
        cursor.moveToFirst();

        String[] arg2={proid};
        Cursor cursor2=ecommercedb.rawQuery("select Quantity from Products where ProID like ?",arg2);
        cursor2.moveToFirst();

        int q=cursor.getInt(0)+cursor2.getInt(0);
        reduce_product_quantity(proid,String.valueOf(q));
        ecommercedb=getWritableDatabase();
        ecommercedb.delete("OrderDetails","Ordid='"+sc_id+"' and Proid='"+proid+"' ",null);
        ecommercedb.close();
    }
    public float calculate_price(String[] p,String[] q)
    {
        float sum=0;
        for(int i=0;i<p.length;i++)
        {
            sum=sum+(Float.parseFloat(p[i])*Float.parseFloat(q[i]));
        }
        return sum;
    }

    public void make_order(String ordid,String custid,String ordDate,String address)
    {
        ecommercedb=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdDate",ordDate);
        row.put("Address",address);
        ecommercedb.update("Orders",row,"OrdID like ? and Custid like ?",new String[]{ordid,custid});
        ecommercedb.close();
    }
}
