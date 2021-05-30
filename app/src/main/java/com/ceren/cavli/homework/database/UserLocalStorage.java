package com.ceren.cavli.homework.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ceren.cavli.homework.model.UserModel;

import java.util.ArrayList;


public class UserLocalStorage extends SQLiteOpenHelper {

    public UserLocalStorage(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRSTNAME TEXT," +
                "LASTNAME TEXT," +
                "PHONE TEXT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("FIRSTNAME", userModel.getFirstName());
        cv.put("LASTNAME", userModel.getLastName());
        cv.put("PHONE", userModel.getPhoneNumber());
        cv.put("EMAIL", userModel.geteMail());
        cv.put("PASSWORD", userModel.getPassword());

        long insert = db.insert("USERS", null, cv);
        db.close();

        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<UserModel> getAllUsers(){
        ArrayList<UserModel> userModels = new ArrayList<>();
        String query = "SELECT * FROM USERS;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String firstname = cursor.getString(1);
                String lastname = cursor.getString(2);
                String phone = cursor.getString(3);
                String email = cursor.getString(4);
                String password = cursor.getString(5);
                UserModel userModel = new UserModel(firstname,lastname,phone,email,password, id);
                userModels.add(userModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userModels;
    }

    public UserModel getAUserWEMail(String email){
        UserModel userModel;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE EMAIL = "  + "'"  + email+ "'" + " ;";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor != null)
            cursor.moveToFirst();
        int id = cursor.getInt(0);
        String firstname = cursor.getString(1);
        String lastname = cursor.getString(2);
        String phone = cursor.getString(3);
        String mail = cursor.getString(4);
        String password = cursor.getString(5);
        userModel = new UserModel(firstname,lastname,phone,mail,password,id);
        return userModel;
    }


}
