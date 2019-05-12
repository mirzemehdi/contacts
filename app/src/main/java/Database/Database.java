package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Contact;
import Util.Constants;

public class Database extends SQLiteOpenHelper {


    public Database(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE "+ Constants.TABLE_NAME+"("+ Constants.KEY_ID+" INTEGER PRIMARY KEY,"+
                Constants.KEY_USERNAME+" TEXT,"+ Constants.KEY_PASSWORD+" TEXT,"+ Constants.KEY_ADDED_DATE+" LONG"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String DROP_TABLE="DROP TABLE IF EXISTS "+ Constants.TABLE_NAME;
    sqLiteDatabase.execSQL(DROP_TABLE);
    onCreate(sqLiteDatabase);
    }


    public void addContact(Contact contact){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(Constants.KEY_USERNAME,contact.getUsername());
        values.put(Constants.KEY_PASSWORD,contact.getPassword());
        values.put(Constants.KEY_ADDED_DATE,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
        db.close();
    }

    public void deleteContact(int contactID){

        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?",new String[]{String.valueOf(contactID)});

        db.close();
    }


    public void updateContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_USERNAME,contact.getUsername());
        values.put(Constants.KEY_PASSWORD,contact.getPassword());
        values.put(Constants.KEY_ADDED_DATE,java.lang.System.currentTimeMillis());
        db.update(Constants.TABLE_NAME,values, Constants.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();

    }

    public Contact getContact(int contactId){

        SQLiteDatabase db=this.getReadableDatabase();
        Contact contact=new Contact();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID, Constants.KEY_USERNAME,
        Constants.KEY_PASSWORD, Constants.KEY_ADDED_DATE}, Constants.KEY_ID+"=?",new String[]{String.valueOf(contactId)},null
        ,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        contact.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        contact.setUsername(cursor.getString(cursor.getColumnIndex(Constants.KEY_USERNAME)));
        contact.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
        java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
       String formatedDate= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ADDED_DATE))).getTime());
       contact.setAddedDate(formatedDate);
        return contact;
    }

    public List<Contact> getAllContact(){
        List<Contact> contacts=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID, Constants.KEY_USERNAME,
                       Constants.KEY_PASSWORD, Constants.KEY_ADDED_DATE},null,null,null
               ,null, Constants.KEY_ADDED_DATE+" DESC");

        if(cursor.moveToFirst()){

           do{


               Contact contact=new Contact();
               contact.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
               contact.setUsername(cursor.getString(cursor.getColumnIndex(Constants.KEY_USERNAME)));
               contact.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
               java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
               String formatedDate= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ADDED_DATE))).getTime());
               contact.setAddedDate(formatedDate);
               contacts.add(contact);

           }while(cursor.moveToNext());


        }
        Log.d("AllContacts","Get All Contact");
        return contacts;
    }

    public void deleteAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        if (cursor.moveToFirst()) {

            do {
                deleteContact(Integer.parseInt(cursor.getString(0)));

            } while (cursor.moveToNext());
        }
    }

    public int getCountContact(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Constants.TABLE_NAME,null);

        return cursor.getCount();
    }



}
