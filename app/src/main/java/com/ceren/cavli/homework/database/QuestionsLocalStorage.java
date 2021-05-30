package com.ceren.cavli.homework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ceren.cavli.homework.activities.MainActivity;
import com.ceren.cavli.homework.model.QuestionModel;

import java.util.ArrayList;

public class QuestionsLocalStorage extends SQLiteOpenHelper {
    public QuestionsLocalStorage(@Nullable Context context) {
        super(context, "questions.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE QUESTIONS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "QUESTION TEXT," +
                "A TEXT," +
                "B TEXT," +
                "C TEXT," +
                "D TEXT," +
                "E TEXT," +
                "ANSWER TEXT," +
                "FILEPATH TEXT," +
                "USERID INTEGER);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewQuestion(QuestionModel question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("QUESTION", question.getQuestion());
        cv.put("A", question.getA());
        cv.put("B", question.getB());
        cv.put("C", question.getC());
        cv.put("D", question.getD());
        cv.put("E", question.getE());
        cv.put("ANSWER", question.getAnswer());
        cv.put("FILEPATH", question.getFilePath());
        cv.put("USERID", question.getUserID());
        long insert = db.insert("QUESTIONS", null, cv);
        db.close();

        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<QuestionModel> getAllQuestions(){
        ArrayList<QuestionModel> questions = new ArrayList<>();
        String query = "SELECT * FROM QUESTIONS WHERE USERID = "+ MainActivity.userID + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String questionText = cursor.getString(1);
                String a = cursor.getString(2);
                String b = cursor.getString(3);
                String c = cursor.getString(4);
                String d = cursor.getString(5);
                String e = cursor.getString(6);
                String answer = cursor.getString(7);
                String filePath = cursor.getString(8);
                int userID = cursor.getInt(9);
                QuestionModel question = new QuestionModel(questionText,a,b,c,d,e,answer,filePath, id, userID);
                questions.add(question);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return questions;
    }

    public QuestionModel getAQuestionWID(int id){
        QuestionModel question;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM QUESTIONS WHERE ID = " + id+";";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor != null)
            cursor.moveToFirst();
        String questionText = cursor.getString(1);
        String a = cursor.getString(2);
        String b = cursor.getString(3);
        String c = cursor.getString(4);
        String d = cursor.getString(5);
        String e = cursor.getString(6);
        String answer = cursor.getString(7);
        String filePath = cursor.getString(8);
        int userID = cursor.getInt(9);
        question = new QuestionModel(questionText,a,b,c,d,e,answer,filePath, id, userID);
        return question;
    }

    public void setAQuestion(int id, QuestionModel question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("QUESTION", question.getQuestion());
        cv.put("A", question.getA());
        cv.put("B", question.getB());
        cv.put("C", question.getC());
        cv.put("D", question.getD());
        cv.put("E", question.getE());
        cv.put("ANSWER", question.getAnswer());
        cv.put("FILEPATH", question.getFilePath());
        cv.put("USERID", question.getUserID());
        db.update("questions",cv,"ID" + "=" + id,null);
        db.close();
    }
    public void deleteAQuestion(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("questions", "id = "+ id, null);
    }
}
