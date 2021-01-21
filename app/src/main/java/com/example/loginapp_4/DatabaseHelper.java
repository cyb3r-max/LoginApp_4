package com.example.loginapp_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="User.db";
    private static final String TABLE_NAME = "userAuth";
    private static final String USER_ID = "_id";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final int DATABASE_VERSION = 8;


    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;



    private Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
           String CREATE_TABLE="CREATE TABLE userAuth (_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(255) NOT NULL, email TEXT NOT NULL, password TEXT);";
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context,"DATABASE CREATED", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(context,"DATABASE NOT CREATED", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context,"DATABASE UPGRADE", Toast.LENGTH_SHORT).show();
        }catch (Exception exception){
            Toast.makeText(context,"DATABASE UPGRADE FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    public long insertUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(USER_EMAIL, user.getEmail());
        contentValues.put(USERNAME, user.getUsername());



        long rowID=sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowID;
    }

    public Boolean findUser(String userName, String userPassword){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM userAuth;",null);

        Boolean result = false;
        if (cursor.getCount()==0){
            Toast.makeText(context,"User not found", Toast.LENGTH_SHORT).show();

        }else {
            while (cursor.moveToNext()){
                String userLogName=cursor.getString(1);
                String userLogpassword=cursor.getString(3);

                if (userLogName.equals(userName) && userLogpassword.equals(userPassword)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}
