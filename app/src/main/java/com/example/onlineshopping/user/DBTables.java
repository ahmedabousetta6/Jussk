package com.example.onlineshopping.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTables extends SQLiteOpenHelper {
    private  static String databasename="shop1";

    SQLiteDatabase admin;
    public DBTables(Context context){
        super(context,databasename,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer(id integer primary key autoincrement , fname text not null,lname text not null ," +
                "email text not null , password text not null , phone text not null , birth date ,sequrity text)");
        db.execSQL("create table product(id integer primary key autoincrement ,name text not null , des text not null ," +
                " color text not null ,image BLOG ,cat text not null,price text not null,barcode text)");
        db.execSQL("create table userbuy(id integer primary key autoincrement , email text not null, name text not null,amount text not null,cost text not null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists buy");
        onCreate(db);
    }
    public boolean addnewuser(String fname ,String lname , String mail,String pw , String phone,String color,String date)
    {
        ContentValues row =new ContentValues();
        row.put("fname",fname);
        row.put("lname",lname);
        row.put("email",mail);
        row.put("password",pw);
        row.put("phone",phone);
        row.put("sequrity",color);
        row.put("birth",date);
        admin=getWritableDatabase();
        long ins=admin.insert("customer",null,row);
        if(ins==-1){
            return false;
        }else
            return true;
    }
    public boolean checkmail(String mail){
        admin=getReadableDatabase();
        Cursor cursor=admin.rawQuery("Select * from customer where email=?",new String[]{mail});
        if(cursor.getCount()>0){
            return false;
        } else {
            return true;
        }
    }
   public boolean loginuser(String mail,String pw){

        admin=getReadableDatabase();
        String[] arg={mail,pw};
        Cursor cursor=admin.rawQuery("Select * from customer where email=? and password=? ",arg);
        if(cursor.getCount()>0){
            return  true;
        } else
            return false;
    }
    public  boolean addproduct(String name , String des , String color , byte[] image , String price, String cat,String barcode)
    {
        ContentValues row=new ContentValues();
        row.put("name",name);
        row.put("des",des);
        row.put("color",color);
        row.put("image",image);
        row.put("cat",cat);
        row.put("price",price);
        row.put("barcode",barcode);
        admin=getWritableDatabase();
        long ins=admin.insert("product",null,row);
        if(ins==-1){
            return false;
        }else
            return true;
    }
  /*  public Cursor getproductt()
    {
         admin=getReadableDatabase();
         String [] row={"name" ,"color","price","image"};
         Cursor cursor=admin.query("product",row,null,null,null,null,null);
         if(cursor!=null){

             cursor.moveToFirst();
         }
         admin.close();

     return cursor;
    }
    */

    public Cursor getproduct(String sql){
        admin= getReadableDatabase();
        return admin.rawQuery(sql, null);
    }

    public Cursor searchbytext(String name)
    {
        admin= getReadableDatabase();
        String [] arg={name};
        return admin.rawQuery("Select * from product where name like?",arg);
    }
    public Cursor searchbycat(String barcode)
    {
        admin= getReadableDatabase();
        String [] arg={barcode};
        return admin.rawQuery("Select * from product where barcode like?",arg);
    }
    public Cursor searchbycategory(String category)
    {
        admin= getReadableDatabase();
        String [] arg={category};
        return admin.rawQuery("Select * from product where cat like?",arg);
    }
    public boolean addbuy(String email,String name,String amount ,String cost)
    {
        ContentValues row=new ContentValues();
        row.put("email",email);
        row.put("name",name);
        row.put("amount",amount);
        row.put("cost",cost);

        admin=getWritableDatabase();
        long ins=admin.insert("userbuy",null,row);
        if(ins==-1){
            return false;
        }else
            return true;
    }
    public Cursor getbuy(String email)
    {
        admin= getReadableDatabase();
        String [] arg={email};
        return admin.rawQuery("Select * from userbuy where email like?",arg);

    }
    public void deletebuy(String name)
    {
     admin=getWritableDatabase();
     admin.delete("userbuy","name='"+name+"'",null);

    }
    public String getamount(String name)
    {
        admin=getReadableDatabase();
        String [] arg={name};
        Cursor cursor=admin.rawQuery("Select amount from userbuy where name like?",arg);
        cursor.moveToFirst();
        return cursor.getString(0);


    }

    public  String getcost(String name)
    {

        admin=getReadableDatabase();
        String [] arg={name};
        Cursor cursor=admin.rawQuery("Select cost from userbuy where name like?",arg);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public void updatebuy(String name,String amount,String cost)
    {
        admin=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("amount",amount);
        row.put("cost",cost);
        admin.update("userbuy",row,"name like ?",new String[]{name});
    }

    public String getpassword(String email,String color)
    {

        admin=getReadableDatabase();
        String [] arg={email,color};
        Cursor cursor=admin.rawQuery("Select password from customer where email like? and sequrity like? ",arg);
        cursor.moveToFirst();
        return cursor.getString(0);

    }

    public void confirmbuy(String email)
    {
        admin=getWritableDatabase();
        admin.delete("userbuy","email='"+email+"'",null);
    }

}
